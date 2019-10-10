package mitw.survivalgames.listener;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.manager.SgChestManager;
import net.minecraft.server.v1_8_R3.EntityLiving;

public class ArenaListener implements Listener {

	public void onEntitySpawn(final CreatureSpawnEvent event) {
		if (event.getEntity() instanceof EntityLiving || event.getEntity() instanceof Player)
			return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onChestopen(final InventoryOpenEvent e) {
		if (!GameStatus.isGaming(true))
			return;
		if (e.getInventory().getHolder() instanceof Chest) {
			final Chest c = (Chest) e.getInventory().getHolder();
			final Location l = c.getLocation();
			if (SgChestManager.opened.contains(l))
				return;
			SgChestManager.opened.add(l);
			final Inventory i = c.getBlockInventory();
			i.clear();
			if (ArenaManager.getUsingArena().getTir2Chests().contains(l)) {
				SurvivalGames.getSgChestManager().putTir2Item(i);
				return;
			}
			if (ArenaManager.getUsingArena().getCenterChests().contains(l)) {
				SurvivalGames.getSgChestManager().putCenteritem(i);
				return;
			}
			SurvivalGames.getSgChestManager().putTir1Item(i);
			return;
		}
		if (e.getInventory().getHolder() instanceof DoubleChest) {
			final DoubleChest c = (DoubleChest) e.getInventory().getHolder();
			final Location l = c.getLocation();
			if (SgChestManager.opened.contains(l))
				return;
			SgChestManager.opened.add(l);
			final Inventory i = c.getInventory();
			i.clear();
			for (int a = 0; a < 2; a++)
				if (ArenaManager.getUsingArena().getTir2Chests().contains(l)) {
					SurvivalGames.getSgChestManager().putTir2Item(i);
				} else {
					SurvivalGames.getSgChestManager().putTir1Item(i);
				}
			return;
		}

	}

	@EventHandler
	public void onBreakBoat(final VehicleDestroyEvent e) {
		if (e.getVehicle() instanceof Boat && e.getAttacker() instanceof Player) {
			final Player p = (Player) e.getAttacker();
			if (!PlayerManager.isGameingPlayer(p)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onRide(final VehicleEnterEvent e) {
		if (e.getVehicle() instanceof Boat && e.getEntered() instanceof Player) {
			final Player p = (Player) e.getEntered();
			if (!PlayerManager.isGameingPlayer(p)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onWeatherChange(final WeatherChangeEvent e) {
		e.setCancelled(true);
	}

}
