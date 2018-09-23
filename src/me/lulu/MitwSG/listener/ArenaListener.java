package me.lulu.MitwSG.listener;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;

import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.manager.PlayerManager;
import me.lulu.MitwSG.manager.SgChestManager;

public class ArenaListener implements Listener {
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Main.getPm().isBuilder(p))
			return;
		if ((Status.isStarting() || Status.isDmStarting()) && PlayerManager.players.contains(p.getUniqueId())
				&& !Main.getGm().sameBlock(e.getTo(), e.getFrom())) {
			e.setTo(e.getFrom());
			return;
		}

	}

	@EventHandler
	public void onChestopen(InventoryOpenEvent e) {
		if (!Status.isGaming(true))
			return;
		if (e.getInventory().getHolder() instanceof Chest) {
			Chest c = (Chest) e.getInventory().getHolder();
			Location l = c.getLocation();
			if (SgChestManager.opened.contains(l))
				return;
			SgChestManager.opened.add(l);
			Inventory i = c.getBlockInventory();
			i.clear();
			if (ArenaManager.usingArena.tir2Chests.contains(l)) {
				Main.getSGm().putTir2Item(i);
				return;
			}
			if (ArenaManager.usingArena.centerChests.contains(l)) {
				Main.getSGm().putCenteritem(i);
				return;
			}
			Main.getSGm().putTir1Item(i);
			return;
		}
		if (e.getInventory().getHolder() instanceof DoubleChest) {
			DoubleChest c = (DoubleChest) e.getInventory().getHolder();
			Location l = c.getLocation();
			if (SgChestManager.opened.contains(l))
				return;
			SgChestManager.opened.add(l);
			Inventory i = c.getInventory();
			i.clear();
			for (int a = 0; a < 2; a++)
				if (ArenaManager.usingArena.tir2Chests.contains(l))
					Main.getSGm().putTir2Item(i);
				else
					Main.getSGm().putTir1Item(i);
			return;
		}

	}

	@EventHandler
	public void onBreakBoat(VehicleDestroyEvent e) {
		if (e.getVehicle() instanceof Boat && e.getAttacker() instanceof Player) {
			Player p = (Player) e.getAttacker();
			if (!Main.getPm().isGameingPlayer(p))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onRide(VehicleEnterEvent e) {
		if (e.getVehicle() instanceof Boat && e.getEntered() instanceof Player) {
			Player p = (Player) e.getEntered();
			if (!Main.getPm().isGameingPlayer(p))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

}
