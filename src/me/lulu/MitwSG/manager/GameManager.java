package me.lulu.MitwSG.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.options.Options;
import me.lulu.MitwSG.scoreboard.BoardSetup;
import me.lulu.MitwSG.tasks.DmStartTask;
import me.lulu.MitwSG.tasks.FireworkTask;
import me.lulu.MitwSG.tasks.GameTask;
import me.lulu.MitwSG.utils.Arena;

public class GameManager {

	public static GameManager ins;

	public static boolean starting = false;
	public static boolean canDeathMatch = false;
	public static boolean isDeathMatching = false;

	public static int minPlayerToStart;

	public GameManager() {
		ins = this;

	}

	public void checkWin() {
		if (Status.isFinished())
			return;
		getWinner();
		getDeathMatch();
	}

	public void getWinner() {
		if (!(PlayerManager.players.size() < 2))
			return;
		BoardSetup.winnerName = Main.getPm().getNameByUUID(PlayerManager.players.get(0));
		Status.setState(Status.FINISH);
		if (!PlayerManager.players.isEmpty()) {
			victoryDance(Bukkit.getPlayer(PlayerManager.players.get(0)));
		}
		/*
		 * send to lobby
		 */
		Bukkit.getScheduler().runTaskLaterAsynchronously(Main.get(), new Runnable() {

			@Override
			public void run() {
				Main.getGm().sendKilltop();
			}
		}, 20 * 2);
		Bukkit.getScheduler().runTaskLater(Main.get(), new Runnable() {

			@Override
			public void run() {
				sendAllPlayers();
			}
		}, 20 * 15);
	}

	private void sendAllPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers())
			players.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (players.isEmpty()) {
					this.cancel();
					Bukkit.shutdown();
					return;
				}
				Player p = players.get(0);
				if (p != null)
					sendToLobbyServer(p);
				players.remove(0);
			}
		}.runTaskTimer(Main.get(), 0, 3);
	}

	private void getDeathMatch() {
		if (canDeathMatch)
			return;
		if (PlayerManager.players.size() <= 4) {
			canDeathMatch = true;
			Options.playSoundAll(Sound.NOTE_PLING);
			Bukkit.broadcastMessage(Lang.needDeathMatch);
			GameTask.timeLeft = 121;
		}
	}

	public void setupGame() {
		checkUsingArena();
		Main.getAm().loadArena(ArenaManager.usingArena);
		addPlayers();
		teleportPlayers();
	}

	public void startDeathMatch() {
		Status.setState(Status.DMSTARTING);
		teleportDeathMatch();
		BoardSetup.setTitile(Lang.deathMatchTitle);
		new DmStartTask().runTaskTimer(Main.get(), 0, 20);
	}

	private void addPlayers() {
		int max = 0;
		for (Player p : Bukkit.getOnlinePlayers()) {
			UUID u = p.getUniqueId();
			PlayerManager.players.add(u);
			PlayerManager.kills.put(u, 0);
			max++;
		}
		PlayerManager.max = max;
	}

	private void checkUsingArena() {
		HashMap<Arena, Integer> temp = new HashMap<>();
		for (Arena a : ArenaManager.votes.values()) {
			if (temp.containsKey(a))
				temp.put(a, temp.get(a) + 1);
			else
				temp.put(a, 1);
		}
		ArrayList<Arena> tempArena = new ArrayList<>(ArenaManager.arenas);
		Arena use = null;
		int highest = 0;
		for (Arena a : temp.keySet())
			if (temp.get(a) > highest) {
				highest = temp.get(a);
				use = a;
			}
		if (use == null || ArenaManager.voteRandom.size() > highest)
			use = tempArena.get(new Random().nextInt(tempArena.size()));
		ArenaManager.usingArena = use;
		ArenaManager.usingArenaName = ArenaManager.usingArena.getName();
	}

	public void teleportPlayers() {
		ArrayList<UUID> temp = new ArrayList<>(PlayerManager.players);
		new BukkitRunnable() {
			int run = 0;
			Player p;

			@Override
			public void run() {
				if (temp.isEmpty()) {
					this.cancel();
					return;
				}
				if (Bukkit.getPlayer(temp.get(0)) != null) {
					p = Bukkit.getPlayer(temp.get(0));
					p.teleport(ArenaManager.usingArena.spawnPoints.get(run).add(0.5, 0, 0.5));
					p.closeInventory();
					sendInfoMessage(p);
					run++;
				}
				temp.remove(0);
			}
		}.runTaskTimer(Main.get(), 0, 2);
	}

	private void sendInfoMessage(Player p) {
		for (String a : Lang.infoMessage)
			p.sendMessage(a);
	}

	public void teleportDeathMatch() {
		int run = 0;
		int addPoint = ArenaManager.usingArena.spawnPoints.size() / PlayerManager.players.size();
		for (UUID u : PlayerManager.players) {
			if (Bukkit.getPlayer(u) != null) {
				Player p = Bukkit.getPlayer(u);
				p.teleport(ArenaManager.usingArena.spawnPoints.get(run).add(0.5, 0, 0.5));
				p.closeInventory();
			}
			run += addPoint;
		}
	}

	public void sendToLobbyServer(Player p) {

		String server = "waiting";

		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Connect");
		out.writeUTF(server);

		p.sendPluginMessage(Main.get(), "BungeeCord", out.toByteArray());

	}

	public void sendKilltop() {
		ArrayList<UUID> temp = Options.getTopArray(PlayerManager.kills);
		PlayerManager pm = Main.getPm();
		Bukkit.broadcastMessage(Options.colored("&f&m--------------------------"));
		Bukkit.broadcastMessage(Options.colored("&61. " + pm.getNameByUUID(temp.get(0)) + " -> " + PlayerManager.kills.get(temp.get(0)) + " ¿ª±˛"));
		Bukkit.broadcastMessage(Options.colored("&e2. " + pm.getNameByUUID(temp.get(1)) + " -> " + PlayerManager.kills.get(temp.get(1)) + " ¿ª±˛"));
		Bukkit.broadcastMessage(Options.colored("&a3. " + pm.getNameByUUID(temp.get(2)) + " -> " + PlayerManager.kills.get(temp.get(2)) + " ¿ª±˛"));
		Bukkit.broadcastMessage(Options.colored("&f&m--------------------------"));
	}

	public boolean isFull() {
		if (PlayerManager.players.size() >= 24)
			return true;
		return false;
	}

	public boolean canStart() {
		if (Bukkit.getOnlinePlayers().size() >= minPlayerToStart)
			return true;
		return false;
	}

	public boolean isStarting() {
		if (starting)
			return true;
		return false;
	}

	public boolean sameBlock(Location location, Location check) {
		return location.getWorld().getName().equalsIgnoreCase(check.getWorld().getName()) && location.getBlockX() == check.getBlockX()
				&& location.getBlockY() == check.getBlockY() && location.getBlockZ() == check.getBlockZ();

	}

	public boolean isOutBlock(Location location) {
		return location.distance(ArenaManager.usingArena.getCenter()) >= 40;
	}

	private void victoryDance(Player p) {
		/* ºΩ©Ò¡n≠µ + ∞TÆß*/
		for (String a : Lang.victoryMsg)
			Bukkit.broadcastMessage(a.replaceAll("<player>", p.getName()));
		Options.playSoundAll(Sound.WITHER_DEATH);
		/* FireWork */
		new FireworkTask(p).runTaskTimer(Main.get(), 0, 10);

	}

}
