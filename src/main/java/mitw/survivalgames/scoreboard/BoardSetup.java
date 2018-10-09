package mitw.survivalgames.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.tasks.DeathMatchTask;
import mitw.survivalgames.tasks.DmStartTask;
import mitw.survivalgames.tasks.GameTask;
import mitw.survivalgames.tasks.LobbyTask;
import mitw.survivalgames.tasks.StartTask;
import mitw.survivalgames.utils.Utils;

public class BoardSetup {
	public static String winnerName;
	public static String winnerKills;

	public static void setup() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(SurvivalGames.getInstance(), BoardSetup::updateAll, 20L, 2L);
	}

	public static void setTitile(final String title) {
		new ArrayList<>(ScoreHelper.players.values()).forEach(scoreHelper -> scoreHelper.setTitle(title));
	}

	public static void updateAll() {
		try {
			Bukkit.getOnlinePlayers().forEach(BoardSetup::updateScore);
		} catch (final Exception e) {}
	}

	public static void updateScore(final Player p) {
		if (ScoreHelper.hasScore(p)) {
			final ScoreHelper helper = ScoreHelper.getByPlayer(p);
			final List<String> setboardList = new ArrayList<>();
			for (final String s : getList(p)) {
				setboardList.add(var(p, s));
			}
			helper.setSlotsFromList(setboardList);
		} else {
			final ScoreHelper helper = ScoreHelper.createScore(p);
			helper.setTitle(Lang.Title);
		}
	}

	public static List<String> getList(Player p) {
		switch (GameStatus.getState()) {
		case WAITING:
			return SurvivalGames.getLanguage().translateArrays(p, "watingList");
		case STARRTING:
			return SurvivalGames.getLanguage().translateArrays(p, "startingList");
		case GAMING:
			return SurvivalGames.getLanguage().translateArrays(p, "gamingList");
		case FINISH:
			return SurvivalGames.getLanguage().translateArrays(p, "finishList");
		case DEATHMATCH:
			return SurvivalGames.getLanguage().translateArrays(p, "dmList");
		case DMSTARTING:
			return SurvivalGames.getLanguage().translateArrays(p, "dmStartList");
		}
		return null;
	}

	public static String var(final Player p, String s) {
		final PlayerCache playerCache = PlayerManager.getCache(p.getUniqueId());
		s = s.replaceAll("<rating>", playerCache.getRating() + "");
		switch (GameStatus.getState()) {
		case WAITING:
			return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<starting>", "" + LobbyTask.timeLeft).replaceAll("<server>", Lang.serverName);
		case STARRTING:
			return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<starting>", StartTask.timeLeft + "").replaceAll("<server>", Lang.serverName);
		case GAMING:
			return s.replaceAll("<players>", "" + PlayerManager.getPlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<time>", Utils.timeFormat(GameTask.timeLeft))
					.replaceAll("<max>", PlayerManager.getMax() + "").replaceAll("<arena>", ArenaManager.getUsingArena().getName())
					.replaceAll("<kills>", String.valueOf(PlayerManager.getKills(p))).replaceAll("<server>", Lang.serverName);
		case FINISH:
			return s.replaceAll("<player>", winnerName).replaceAll("<playerKills>", winnerKills).replaceAll("<time>", Utils.timeFormat(GameTask.timeLeft));
		case DEATHMATCH:
			return s.replaceAll("<players>", "" + PlayerManager.getPlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<time>", (DeathMatchTask.a <= 0 ? Utils.timeFormat(0) : Utils.timeFormat(DeathMatchTask.a)))
					.replaceAll("<max>", PlayerManager.getMax() + "").replaceAll("<arena>", ArenaManager.getUsingArena().getName())
					.replaceAll("<kills>", String.valueOf(PlayerManager.getKills(p))).replaceAll("<server>", Lang.serverName);
		case DMSTARTING:
			return s.replaceAll("<players>", "" + PlayerManager.getPlayers().size()).replaceAll("&", "¡±").replaceAll("<starting>", DmStartTask.count + "")
					.replaceAll("<server>", Lang.serverName);
		}
		return s;
	}

}
