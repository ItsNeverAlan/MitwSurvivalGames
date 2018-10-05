package mitw.survivalgames.tasks;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.utils.Utils;

public class StartTask extends BukkitRunnable {
	public static int timeLeft = 15;

	@Override
	public void run() {
		if (!SurvivalGames.getGameManager().canStart()) {
			Bukkit.shutdown();
			this.cancel();
		}
		timeLeft--;
		if (timeLeft < 1) {
			GameStatus.setState(GameStatus.GAMING);
			final PotionEffect eff = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 15 * 20, 255);
			for (final UUID u : PlayerManager.players)
				eff.apply(Bukkit.getPlayer(u));
			PlayerManager.oringalPlayers = new ArrayList<>(PlayerManager.players);
			new GameTask().runTaskTimer(SurvivalGames.getInstance(), 0, 20);
			Utils.playSoundAll(Sound.ENDERDRAGON_GROWL);
			Bukkit.getOnlinePlayers().forEach(pl -> pl.sendMessage(SurvivalGames.getLanguage().translate(pl, "gameStarted")));
			SurvivalGames.getGameManager().checkWin();
			this.cancel();
			return;
		} else if (timeLeft <= 5 || timeLeft == 10) {
			Utils.playSoundAll(Sound.NOTE_STICKS);
			Bukkit.getOnlinePlayers().forEach(pl -> pl.sendMessage(SurvivalGames.getLanguage().translate(pl, "startCount").replaceAll("<time>", String.valueOf(timeLeft))));
		}

	}

}
