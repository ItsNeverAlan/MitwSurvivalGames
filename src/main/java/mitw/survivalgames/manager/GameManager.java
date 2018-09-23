package mitw.survivalgames.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.scoreboard.BoardSetup;
import mitw.survivalgames.tasks.DmStartTask;
import mitw.survivalgames.tasks.FireworkTask;
import mitw.survivalgames.tasks.GameTask;
import mitw.survivalgames.utils.Utils;

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
		if (GameStatus.isFinished())
			return;
		getWinner();
		getDeathMatch();
	}

	public void getWinner() {
		if (!(PlayerManager.players.size() < 2))
			return;
		BoardSetup.winnerName = SurvivalGames.getPlayerManager().getNameByUUID(PlayerManager.players.get(0));
		GameStatus.setState(GameStatus.FINISH);
		if (!PlayerManager.players.isEmpty()) {
			victoryDance(Bukkit.getPlayer(PlayerManager.players.get(0)));
		}
		/*
		 * send to lobby
		 */
		Bukkit.getScheduler().runTaskLaterAsynchronously(SurvivalGames.getInstance(), () -> SurvivalGames.getGameManager().sendKilltop(), 20 * 2);
		Bukkit.getScheduler().runTaskLater(SurvivalGames.getInstance(), () -> sendAllPlayers(), 20 * 15);
	}

	private void sendAllPlayers() {
		final ArrayList<Player> players = new ArrayList<>();
		for (final Player p : Bukkit.getOnlinePlayers())
			players.add(p);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (players.isEmpty()) {
					this.cancel();
					Bukkit.shutdown();
					return;
				}
				final Player p = players.get(0);
				if (p != null)
					sendToLobbyServer(p);
				players.remove(0);
			}
		}.runTaskTimer(SurvivalGames.getInstance(), 0, 3);
	}

	private void getDeathMatch() {
		if (canDeathMatch)
			return;
		if (PlayerManager.players.size() <= 4) {
			canDeathMatch = true;
			Utils.playSoundAll(Sound.NOTE_PLING);
			Bukkit.broadcastMessage(Lang.needDeathMatch);
			GameTask.timeLeft = 121;
		}
	}

	public void setupGame() {
		checkUsingArena();
		SurvivalGames.getArenaManager().loadArena(ArenaManager.usingArena);
		addPlayers();
		teleportPlayers();
	}

	public void startDeathMatch() {
		GameStatus.setState(GameStatus.DMSTARTING);
		teleportDeathMatch();
		BoardSetup.setTitile(Lang.deathMatchTitle);
		new DmStartTask().runTaskTimer(SurvivalGames.getInstance(), 0, 20);
	}

	private void addPlayers() {
		int max = 0;
		for (final Player p : Bukkit.getOnlinePlayers()) {
			final UUID u = p.getUniqueId();
			PlayerManager.players.add(u);
			PlayerManager.kills.put(u, 0);
			max++;
		}
		PlayerManager.max = max;
	}

	private void checkUsingArena() {
		final HashMap<Arena, Integer> temp = new HashMap<>();
		for (final Arena a : ArenaManager.votes.values()) {
			if (temp.containsKey(a))
				temp.put(a, temp.get(a) + 1);
			else
				temp.put(a, 1);
		}
		final ArrayList<Arena> tempArena = new ArrayList<>(ArenaManager.arenas);
		Arena use = null;
		int highest = 0;
		for (final Arena a : temp.keySet())
			if (temp.get(a) > highest) {
				highest = temp.get(a);
				use = a;
			}
		if (use == null || ArenaManager.voteRandom.size() > highest)
			use = tempArena.get(SurvivalGames.getRandom().nextInt(tempArena.size()));
		ArenaManager.usingArena = use;
		ArenaManager.usingArenaName = ArenaManager.usingArena.getName();
	}

	public void teleportPlayers() {
		final ArrayList<UUID> temp = new ArrayList<>(PlayerManager.players);
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
		}.runTaskTimer(SurvivalGames.getInstance(), 0, 2);
	}

	private void sendInfoMessage(Player p) {
		for (final String a : Lang.infoMessage)
			p.sendMessage(a);
	}

	public void teleportDeathMatch() {
		int run = 0;
		final int addPoint = ArenaManager.usingArena.spawnPoints.size() / PlayerManager.players.size();
		for (final UUID u : PlayerManager.players) {
			if (Bukkit.getPlayer(u) != null) {
				final Player p = Bukkit.getPlayer(u);
				p.teleport(ArenaManager.usingArena.spawnPoints.get(run).add(0.5, 0, 0.5));
				p.closeInventory();
			}
			run += addPoint;
		}
	}

	public void sendToLobbyServer(Player p) {

		final String server = "waiting";

		final ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Connect");
		out.writeUTF(server);

		p.sendPluginMessage(SurvivalGames.getInstance(), "BungeeCord", out.toByteArray());

	}

	public void sendKilltop() {
		final ArrayList<UUID> temp = Utils.getTopArray(PlayerManager.kills);
		final PlayerManager pm = SurvivalGames.getPlayerManager();
		Bukkit.broadcastMessage(Utils.colored("&f&m--------------------------"));
		Bukkit.broadcastMessage(Utils.colored("&61. " + pm.getNameByUUID(temp.get(0)) + " -> " + PlayerManager.kills.get(temp.get(0)) + " ¿ª±˛"));
		Bukkit.broadcastMessage(Utils.colored("&e2. " + pm.getNameByUUID(temp.get(1)) + " -> " + PlayerManager.kills.get(temp.get(1)) + " ¿ª±˛"));
		Bukkit.broadcastMessage(Utils.colored("&a3. " + pm.getNameByUUID(temp.get(2)) + " -> " + PlayerManager.kills.get(temp.get(2)) + " ¿ª±˛"));
		Bukkit.broadcastMessage(Utils.colored("&f&m--------------------------"));
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
		for (final String a : Lang.victoryMsg)
			Bukkit.broadcastMessage(a.replaceAll("<player>", p.getName()));
		Utils.playSoundAll(Sound.WITHER_DEATH);
		/* FireWork */
		new FireworkTask(p).runTaskTimer(SurvivalGames.getInstance(), 0, 10);

	}

}
