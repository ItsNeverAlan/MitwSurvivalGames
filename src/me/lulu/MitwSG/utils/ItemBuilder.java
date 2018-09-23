package me.lulu.MitwSG.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.options.Options;

public class ItemBuilder {
	public static ItemBuilder ins;

	public ItemBuilder() {
		ins = this;
	}

	public ItemStack itemWithName(Material mat, String name) {
		ItemStack i = new ItemStack(mat);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(Options.colored(name));
		i.setItemMeta(m);
		return i;
	}

	public ItemStack createVoteMapItem(Material mat, Arena a) {
		ItemStack i = new ItemStack(mat);
		ItemMeta m = i.getItemMeta();
		int votes = 0;
		for (Arena arena : ArenaManager.votes.values()) {
			if (arena.equals(a))
				votes++;
		}
		m.setDisplayName(a.getName());
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§f目前票數: §6" + votes);
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}

	public ItemStack createRandomMap(Material mat, String name) {
		ItemStack i = new ItemStack(mat);
		ItemMeta m = i.getItemMeta();
		int votes = ArenaManager.voteRandom.size();
		m.setDisplayName(Options.colored(name));
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§f目前票數: §6" + votes);
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}

}
