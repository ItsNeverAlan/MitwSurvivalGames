package me.lulu.MitwSG.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import me.lulu.MitwSG.utils.MenuBuilder;

public class InventoryListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		for (MenuBuilder m : MenuBuilder.listMenu) {
			if (e.getInventory().equals((Object) m.i()) && e.getCurrentItem() != null
					&& m.i().contains(e.getCurrentItem()) && e.getWhoClicked() instanceof Player) {
				m.onClick((Player) e.getWhoClicked(), e.getCurrentItem(), e.getInventory().getContents());
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onCloseInventory(InventoryCloseEvent e) {
		for (MenuBuilder m : MenuBuilder.listMenu) {
			if (m.i().equals(e.getInventory())) {
				MenuBuilder.listMenu.remove(m);
				return;
			}
		}
	}
}
