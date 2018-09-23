package me.lulu.MitwSG.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.PlayerManager;

public class DeathMatchTask extends BukkitRunnable {
	public static int a = 180;

	@Override
	public void run() {
		if (Status.isFinished()) {
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
		for (UUID u : PlayerManager.players) {
			Player p = Bukkit.getPlayer(u);
			if (p == null)
				continue;
			if (a < 1) {
				p.damage(2.0);
				continue;
			}
			if (Main.getGm().isOutBlock(p.getLocation())) {
				p.getWorld().strikeLightningEffect(p.getLocation());
				p.damage(2.0);
				p.sendMessage(Lang.outArena);
			}
		}

	}

}
