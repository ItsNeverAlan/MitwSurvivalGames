package mitw.survivalgames.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;

public class DeathMatchTask extends BukkitRunnable {
	public static int a = 180;

	@Override
	public void run() {
		if (GameStatus.isFinished()) {
			this.cancel();
			return;
		}
		a--;
		if (a == 0) {
			Bukkit.getOnlinePlayers()
			.forEach(pl -> pl.sendMessage(SurvivalGames.getLanguage().translate(pl, "allLightStart")));
		} else if (a > 0 && (a % 60 == 0 || a <= 5))
			Bukkit.getOnlinePlayers()
			.forEach(pl -> pl.sendMessage(SurvivalGames.getLanguage().translate(pl, "allStrikeLightCount").replaceAll("<time>", String.valueOf(a))));
		if (a % 2 != 0)
			return;
		for (final UUID u : PlayerManager.getPlayers()) {
			final Player p = Bukkit.getPlayer(u);
			if (p == null)
				continue;
			if (a < 1) {
				p.damage(2.0);
				continue;
			}
			if (GameManager.isOutBlock(p.getLocation())) {
				p.getWorld().strikeLightningEffect(p.getLocation());
				p.damage(2.0);
				p.sendMessage(SurvivalGames.getLanguage().translate(p, "outArena"));
			}
		}

	}

}
