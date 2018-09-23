package me.lulu.MitwSG.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.GameManager;
import me.lulu.MitwSG.options.Options;

public class LobbyTask extends BukkitRunnable {
	public static int timeLeft = 30;

	@Override
	public void run() {
		if (!Main.getGm().canStart()) {
			GameManager.starting = false;
			Status.setState(Status.WAITING);
			Bukkit.broadcastMessage(Lang.cantStart);
			Options.playSoundAll(Sound.VILLAGER_HIT);
			timeLeft = 30;
			this.cancel();
			return;
		}
		timeLeft--;
		if (timeLeft < 1) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				Main.getPm().clearInventory(p);
			}
			Status.setState(Status.STARRTING);
			Main.getGm().setupGame();
			new StartTask().runTaskTimer(Main.get(), 0, 20);
			this.cancel();
		} else {
			if (timeLeft <= 5 || timeLeft == 10 || timeLeft == 15)
				Bukkit.broadcastMessage(Lang.lobbyCount.replaceAll("<time>", String.valueOf(timeLeft)));
		}

	}

}
