package mitw.survivalgames.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class MenuBuilder {

	public static List<MenuBuilder> listMenu = new ArrayList<>();

	String s = null;
	public Inventory _inv;

	public MenuBuilder(String name, int rows) {
		this._inv = Bukkit.createInventory((InventoryHolder) null, (int) (9 * rows),
				(String) ChatColor.translateAlternateColorCodes((char) '&', (String) name));
		this.s = name;
		listMenu.add(this);
	}

	public void a(ItemStack stack) {
		this._inv.addItem(new ItemStack[] { stack });
	}

	public void s(int i, ItemStack stack) {
		this._inv.setItem(i, stack);
	}

	public void s(ItemStack[] stack) {
		this._inv.addItem(stack);
	}

	public Inventory i() {
		return this._inv;
	}

	public String n() {
		return this._inv.getName();
	}

	public void o(Player p) {
		p.openInventory(this._inv);
	}

	public abstract void onClick(Player p, ItemStack i, ItemStack[] items);
}