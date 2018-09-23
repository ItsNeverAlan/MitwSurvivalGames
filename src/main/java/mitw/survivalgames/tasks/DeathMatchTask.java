package mitw.survivalgames.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
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
			Bukkit.broadcastMessage(Lang.allLightStart);
		} else if (a > 0 && (a % 60 == 0 || a <= 5))
			Bukkit.broadcastMessage(Lang.allStrikeLightCount.replaceAll("<time>", String.valueOf(a)));
		if (a % 2 != 0)
			return;
		for (final UUID u : PlayerManager.players) {
			final Player p = Bukkit.getPlayer(u);
			if (p == null)
				continue;
			if (a < 1) {
				p.damage(2.0);
				continue;
			}
			if (SurvivalGames.getGameManager().isOutBlock(p.getLocation())) {
				p.getWorld().strikeLightningEffect(p.getLocation());
				p.damage(2.0);
				p.sendMessage(Lang.outArena);
			}
		}

	}

}
