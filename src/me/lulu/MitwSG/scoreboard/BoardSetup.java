package me.lulu.MitwSG.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.manager.PlayerManager;
import me.lulu.MitwSG.options.Options;
import me.lulu.MitwSG.tasks.DeathMatchTask;
import me.lulu.MitwSG.tasks.DmStartTask;
import me.lulu.MitwSG.tasks.GameTask;
import me.lulu.MitwSG.tasks.LobbyTask;
import me.lulu.MitwSG.tasks.StartTask;

public class BoardSetup {
	public static String winnerName;

	public static void setup() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					updateScore(p);
				}
			}
		}.runTaskTimerAsynchronously(Main.get(), 2l, 2l);
	}

	public static void setTitile(String title) {
		title = Options.colored(title);
		for (ScoreHelper s : new ArrayList<>(ScoreHelper.players.values()))
			s.setTitle(title);
	}

	public static void updateScore(Player p) {
		if (ScoreHelper.hasScore(p)) {
			ScoreHelper helper = ScoreHelper.getByPlayer(p);
			List<String> setboardList = new ArrayList<>();
			for (String s : getList()) {
				setboardList.add(var(p, s));
			}
			helper.setSlotsFromList(setboardList);
		} else {
			ScoreHelper helper = ScoreHelper.createScore(p);
			helper.setTitle(Lang.Title);
		}
	}

	public static List<String> getList() {
		switch (Status.getState()) {
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

	public static String var(Player p, String s) {
		switch (Status.getState()) {
		case WAITING:
			return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<starting>", "" + LobbyTask.timeLeft).replaceAll("<server>", "¡±b" + Lang.serverName);
		case STARRTING:
			return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "¡±")
					.replaceAll("<starting>", StartTask.timeLeft + "").replaceAll("<server>", "¡±b" + Lang.serverName);
		case GAMING:
			return s.replaceAll("<players>", "" + PlayerManager.players.size()).replaceAll("&", "¡±")
					.replaceAll("<time>", Options.timeFormat(GameTask.timeLeft))
					//.replaceAll("<kills>", Main.getGM().getData.get(p.getUniqueId()).getKills() + "")
					.replaceAll("<max>", PlayerManager.max + "").replaceAll("<arena>", ArenaManager.usingArenaName + "")
					.replaceAll("<kills>", String.valueOf(Main.getPm().getKills(p))).replaceAll("<server>", "¡±b" + Lang.serverName);
		case FINISH:
			return s.replaceAll("<player>", winnerName);
		case DEATHMATCH:
			return s.replaceAll("<players>", "" + PlayerManager.players.size()).replaceAll("&", "¡±")
					.replaceAll("<time>", (DeathMatchTask.a <= 0 ? Options.timeFormat(0) : Options.timeFormat(DeathMatchTask.a)))
					.replaceAll("<max>", PlayerManager.max + "").replaceAll("<arena>", ArenaManager.usingArenaName + "")
					.replaceAll("<kills>", String.valueOf(Main.getPm().getKills(p))).replaceAll("<server>", "¡±b" + Lang.serverName);
		case DMSTARTING:
			return s.replaceAll("<players>", "" + PlayerManager.players.size()).replaceAll("&", "¡±").replaceAll("<starting>", DmStartTask.count + "")
					.replaceAll("<server>", "¡±b" + Lang.serverName);
		}
		return s;
	}

}
