package me.lulu.MitwSG.tasks;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.PlayerManager;
import me.lulu.MitwSG.options.Options;

public class StartTask extends BukkitRunnable {
	public static int timeLeft = 15;

	@Override
	public void run() {
		if (!Main.getGm().canStart()) {
			Bukkit.shutdown();
			this.cancel();
		}
		timeLeft--;
		if (timeLeft < 1) {
			Status.setState(Status.GAMING);
			PotionEffect eff = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 15 * 20, 255);
			for (UUID u : PlayerManager.players)
				eff.apply(Bukkit.getPlayer(u));
			new GameTask().runTaskTimer(Main.get(), 0, 20);
			Options.playSoundAll(Sound.ENDERDRAGON_GROWL);
			Bukkit.broadcastMessage(Lang.gameStarted);
			Main.getGm().checkWin();
			this.cancel();
			return;
		} else if (timeLeft <= 5 || timeLeft == 10) {
			Options.playSoundAll(Sound.NOTE_STICKS);
			Bukkit.broadcastMessage(Options.colored(Lang.startCount).replaceAll("<time>", String.valueOf(timeLeft)));
		}

	}

}
