package mitw.survivalgames.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.utils.Utils;

public class DmStartTask extends BukkitRunnable {
	public static int count = 11;

	@Override
	public void run() {
		SurvivalGames.getGameManager().getWinner();
		if (GameStatus.isFinished()) {
			this.cancel();
			return;
		}
		count--;
		if (count > 0)
			Bukkit.broadcastMessage(Lang.deathMatchStartCount.replaceAll("<time>", String.valueOf(count)));
		else {
			GameStatus.setState(GameStatus.DEATHMATCH);
			GameManager.isDeathMatching = true;
			new DeathMatchTask().runTaskTimer(SurvivalGames.getInstance(), 0, 20);
			Utils.playSoundAll(Sound.WITHER_SPAWN);
			this.cancel();
			return;
		}

	}

}
