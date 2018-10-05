package mitw.survivalgames.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.scoreboard.BoardSetup;
import mitw.survivalgames.tasks.DmStartTask;
import mitw.survivalgames.tasks.FireworkTask;
import mitw.survivalgames.tasks.GameTask;
import mitw.survivalgames.utils.Utils;
import net.md_5.bungee.api.ChatColor;

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
		BoardSetup.winnerKills = SurvivalGames.getPlayerManager().getKills(PlayerManager.players.get(0)) + "";
		GameStatus.setState(GameStatus.FINISH);
		if (!PlayerManager.players.isEmpty()) {
			final Player winner = Bukkit.getPlayer(PlayerManager.players.get(0));

			victoryDance(winner);

			final int ratingAdded = SurvivalGames.getRandom().nextInt(18, 25);
			final PlayerCache killerCache = SurvivalGames.getPlayerManager().getCache(winner.getUniqueId());
			killerCache.setRating(killerCache.getRating() + ratingAdded);

			winner.sendMessage(SurvivalGames.getLanguage().translate(winner, "ratingAdded") + ratingAdded);
		}

		SurvivalGames.getPlayerManager().saveAllCache();
		/*
		 * send to lobby
		 */
		Bukkit.getScheduler().runTaskLaterAsynchronously(SurvivalGames.getInstance(), () -> SurvivalGames.getGameManager().sendKilltop(), 20 * 2);
		Bukkit.getScheduler().runTaskLater(SurvivalGames.getInstance(), () -> sendAllPlayers(), 20 * 15);
	}

	private void sendAllPlayers() {
		final ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
		new BukkitRunnable() {
			@Override
			public void run() {
				if (players.isEmpty()) {
					this.cancel();
					Bukkit.shutdown();
					return;
				}
				final Player p = players.remove(0);
				if (p != null) {
					sendToLobbyServer(p);
				}
			}
		}.runTaskTimer(SurvivalGames.getInstance(), 0L, 2L);
	}

	private void getDeathMatch() {
		if (canDeathMatch)
			return;
		if (PlayerManager.players.size() <= 4) {
			canDeathMatch = true;
			Utils.playSoundAll(Sound.NOTE_PLING);
			Bukkit.getOnlinePlayers().forEach(p -> SurvivalGames.getLanguage().translate(p, "needDeathMatch"));
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
		BoardSetup.setTitile(Utils.colored(Lang.deathMatchTitle));
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
			if (temp.containsKey(a)) {
				temp.put(a, temp.get(a) + 1);
			} else {
				temp.put(a, 1);
			}
		}
		final List<Arena> tempArena = new ArrayList<>(ArenaManager.arenas);
		Arena use = null;
		int highest = 0;
		for (final Arena a : temp.keySet())
			if (temp.get(a) > highest) {
				highest = temp.get(a);
				use = a;
			}
		if (use == null || ArenaManager.voteRandom.size() > highest) {
			use = tempArena.get(SurvivalGames.getRandom().nextInt(tempArena.size()));
		}
		ArenaManager.usingArena = use;
		ArenaManager.usingArenaName = ArenaManager.usingArena.getName();
	}

	public void teleportPlayers() {

		final Iterator<UUID> iterator = new ArrayList<>(PlayerManager.players).iterator();

		new BukkitRunnable() {

			private int run = 0;

			@Override
			public void run() {
				if (!iterator.hasNext()) {
					this.cancel();
					return;
				}
				final Player p = Bukkit.getPlayer(iterator.next());
				if (p != null) {
					p.teleport(ArenaManager.usingArena.spawnPoints.get(run).add(0.5, 0, 0.5));
					p.closeInventory();
					sendInfoMessage(p);
					run++;
				}
			}
		}.runTaskTimer(SurvivalGames.getInstance(), 0, 2);
	}

	private void sendInfoMessage(final Player p) {
		SurvivalGames.getLanguage().translateArrays(p, "infoMessage").forEach(p::sendMessage);
	}

	public void teleportDeathMatch() {
		int run = 0;
		final int addPoint = ArenaManager.usingArena.spawnPoints.size() / PlayerManager.players.size();
		for (final UUID u : PlayerManager.players) {
			final Player p = Bukkit.getPlayer(u);
			if (p != null) {
				p.teleport(ArenaManager.usingArena.spawnPoints.get(run).add(0.5, 0, 0.5));
				p.closeInventory();
			}
			run += addPoint;
		}
	}

	public void sendToLobbyServer(final Player p) {

		final String server = "waiting";

		final ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("Connect");
		out.writeUTF(server);

		p.sendPluginMessage(SurvivalGames.getInstance(), "BungeeCord", out.toByteArray());

	}

	public void sendKilltop() {
		final List<UUID> temp = Utils.getTopArray(PlayerManager.kills);
		final PlayerManager pm = SurvivalGames.getPlayerManager();
		Bukkit.broadcastMessage(Utils.colored("&f&m--------------------------"));
		for (int i = 0; i < temp.size() && i < 3; i++) {
			final String color = getKillTopColor(i), top = (i + 1) + "", name = pm.getNameByUUID(temp.get(i)), kills = PlayerManager.kills.get(temp.get(i)) + "";
			Bukkit.getOnlinePlayers()
			.forEach(pl -> pl.sendMessage(color + top + ". " + name + " -> " + kills + " "
					+ SurvivalGames.getLanguage().translate(pl, "kills")));
		}
		Bukkit.broadcastMessage(Utils.colored("&f&m--------------------------"));
	}

	public String getKillTopColor(final int i) {
		if (i == 0)
			return ChatColor.GOLD.toString();
		else if (i == 1)
			return ChatColor.YELLOW.toString();
		return ChatColor.GREEN.toString();
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

	public boolean sameBlock(final Location location, final Location check) {
		return location.getWorld().getName().equalsIgnoreCase(check.getWorld().getName()) && location.getBlockX() == check.getBlockX()
				&& location.getBlockY() == check.getBlockY() && location.getBlockZ() == check.getBlockZ();
	}

	public boolean isOutBlock(final Location location) {
		return location.distance(ArenaManager.usingArena.getCenter()) >= 40;
	}

	private void victoryDance(final Player p) {
		/* 播放聲音 + 訊息*/
		Bukkit.getOnlinePlayers()
		.forEach(pl -> SurvivalGames.getLanguage().translateArrays(pl, "victoryMsg")
				.forEach(msg -> pl.sendMessage(msg.replaceAll("<player>", p.getName()).replaceAll("<playerKills>", ""+SurvivalGames.getPlayerManager().getKills(p)))));

		Utils.playSoundAll(Sound.WITHER_DEATH);
		/* FireWork */
		new FireworkTask(p).runTaskTimer(SurvivalGames.getInstance(), 0, 10);

	}

}
