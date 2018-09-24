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
import mitw.survivalgames.tasks.DeathMatchTask;
import mitw.survivalgames.tasks.DmStartTask;
import mitw.survivalgames.tasks.GameTask;
import mitw.survivalgames.tasks.LobbyTask;
import mitw.survivalgames.tasks.StartTask;
import mitw.survivalgames.utils.Utils;

public class BoardSetup {
	public static String winnerName;

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
			for (final String s : getList()) {
				setboardList.add(var(p, s));
			}
			helper.setSlotsFromList(setboardList);
		} else {
			final ScoreHelper helper = ScoreHelper.createScore(p);
			helper.setTitle(Lang.Title);
		}
	}

	public static List<String> getList() {
		switch (GameStatus.getState()) {
		case WAITING:
			return Lang.watingList;
		case STARRTING:
			return Lang.startingList;
		case GAMING:
			return Lang.gamingList;
		case FINISH:
			return Lang.finishList;
		case DEATHMATCH:
			return Lang.dmList;
		case DMSTARTING:
			return Lang.dmStartList;
		}
		return null;
	}

	public static String var(final Player p, final String s) {
		switch (GameStatus.getState()) {
		case WAITING:
			return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<starting>", "" + LobbyTask.timeLeft).replaceAll("<server>", "¡±b" + Lang.serverName);
		case STARRTING:
			return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<starting>", StartTask.timeLeft + "").replaceAll("<server>", "¡±b" + Lang.serverName);
		case GAMING:
			return s.replaceAll("<players>", "" + PlayerManager.players.size()).replaceAll("&", "¡±")
					.replaceAll("<time>", Utils.timeFormat(GameTask.timeLeft))
					.replaceAll("<max>", PlayerManager.max + "").replaceAll("<arena>", ArenaManager.usingArenaName + "")
					.replaceAll("<kills>", String.valueOf(SurvivalGames.getPlayerManager().getKills(p))).replaceAll("<server>", "¡±b" + Lang.serverName);
		case FINISH:
			return s.replaceAll("<player>", winnerName);
		case DEATHMATCH:
			return s.replaceAll("<players>", "" + PlayerManager.players.size()).replaceAll("&", "¡±")
					.replaceAll("<time>", (DeathMatchTask.a <= 0 ? Utils.timeFormat(0) : Utils.timeFormat(DeathMatchTask.a)))
					.replaceAll("<max>", PlayerManager.max + "").replaceAll("<arena>", ArenaManager.usingArenaName + "")
					.replaceAll("<kills>", String.valueOf(SurvivalGames.getPlayerManager().getKills(p))).replaceAll("<server>", "¡±b" + Lang.serverName);
		case DMSTARTING:
			return s.replaceAll("<players>", "" + PlayerManager.players.size()).replaceAll("&", "¡±").replaceAll("<starting>", DmStartTask.count + "")
					.replaceAll("<server>", "¡±b" + Lang.serverName);
		}
		return s;
	}

}
