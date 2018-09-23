package me.lulu.MitwSG.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.options.Options;

public class GameTask extends BukkitRunnable {
	public static int timeLeft = 60 * 30 + 1;
	public static String timeStr = timeLeft / 60 + ":" + timeLeft % 60;

	@Override
	public void run() {
		timeLeft--;
		timeStr = timeLeft / 60 + ":" + timeLeft % 60;
		if (timeLeft < 1) {
			this.cancel();
			Main.getGm().startDeathMatch();
			return;
		}
		if (Status.isFinished()) {
			this.cancel();
			return;
		}
		if (timeLeft == 30 || (timeLeft > 0 && timeLeft <= 10)) {
			Options.playSoundAll(Sound.NOTE_PLING);
			Bukkit.broadcastMessage(Lang.gameCount.replaceAll("<time>", String.valueOf(timeLeft) + " ¡±c¬íÄÁ"));
			return;
		}
		if (timeLeft % 300 == 0 || timeLeft == 180 || timeLeft == 120 || timeLeft == 60) {
			Options.playSoundAll(Sound.NOTE_PLING);
			Bukkit.broadcastMessage(Lang.gameCount.replaceAll("<time>", String.valueOf(timeLeft / 60) + " ¡±c¤ÀÄÁ"));
			return;
		}

	}

}
