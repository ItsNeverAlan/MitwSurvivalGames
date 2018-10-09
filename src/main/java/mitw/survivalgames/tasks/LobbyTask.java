package mitw.survivalgames.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.utils.Utils;

public class LobbyTask extends BukkitRunnable {
	public static int timeLeft = 30;

	@Override
	public void run() {
		if (!GameManager.canStart()) {
			GameManager.starting = false;
			GameStatus.setState(GameStatus.WAITING);
			Bukkit.getOnlinePlayers().forEach(pl -> pl.sendMessage(SurvivalGames.getLanguage().translate(pl, "cantStart")));
			Utils.playSoundAll(Sound.VILLAGER_HIT);
			timeLeft = 30;
			this.cancel();
			return;
		}
		timeLeft--;
		if (timeLeft < 1) {
			for (final Player p : Bukkit.getOnlinePlayers()) {
				PlayerManager.clearInventory(p);
			}
			GameStatus.setState(GameStatus.STARRTING);
			GameManager.setupGame();
			new StartTask().runTaskTimer(SurvivalGames.getInstance(), 0, 20);
			this.cancel();
		} else {
			if (timeLeft <= 5 || timeLeft == 10 || timeLeft == 15)
				Bukkit.getOnlinePlayers().forEach(pl -> pl.sendMessage(SurvivalGames.getLanguage().translate(pl, "lobbyCount").replaceAll("<time>", String.valueOf(timeLeft))));
		}

	}

}
