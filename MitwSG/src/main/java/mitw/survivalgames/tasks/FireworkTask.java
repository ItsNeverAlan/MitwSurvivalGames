package mitw.survivalgames.tasks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkTask extends BukkitRunnable {
	public FireworkTask(Player p) {
		this.p = p;
		w = p.getWorld();
	}

	Player p;
	World w;
	int times = 0;
	boolean isNight = false;

	@Override
	public void run() {
		if (times > 10 || p == null) {
			w.setTime(18000);
			this.cancel();
			return;
		}
		Firework fw = p.getWorld().spawn(p.getLocation(), Firework.class);
		Builder builder = FireworkEffect.builder();
		FireworkMeta fwm = fw.getFireworkMeta();
		fwm.addEffect(builder.flicker(true).withColor(Color.ORANGE).build());
		fwm.addEffect(builder.trail(true).build());
		fwm.addEffect(builder.withFade(Color.AQUA).build());
		fwm.setPower(1);
		fw.setFireworkMeta(fwm);
		if (isNight) {
			w.setTime(6000);
			isNight = false;
		} else {
			w.setTime(18000);
			isNight = true;
		}

		times++;

	}

}
