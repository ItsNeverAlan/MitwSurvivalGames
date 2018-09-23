package me.lulu.MitwSG.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.GameManager;
import me.lulu.MitwSG.options.Options;

public class DmStartTask extends BukkitRunnable {
	public static int count = 11;

	@Override
	public void run() {
		Main.getGm().getWinner();
		if (Status.isFinished()) {
			this.cancel();
			return;
		}
		count--;
		if (count > 0)
			Bukkit.broadcastMessage(Lang.deathMatchStartCount.replaceAll("<time>", String.valueOf(count)));
		else {
			Status.setState(Status.DEATHMATCH);
			GameManager.isDeathMatching = true;
			new DeathMatchTask().runTaskTimer(Main.get(), 0, 20);
			Options.playSoundAll(Sound.WITHER_SPAWN);
			this.cancel();
			return;
		}

	}

}
