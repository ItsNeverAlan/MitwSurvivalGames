package mitw.survivalgames.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.manager.ArenaManager;

public class ItemBuilder {
	public static ItemBuilder ins;

	public ItemBuilder() {
		ins = this;
	}

	public ItemStack itemWithName(Material mat, String name) {
		final ItemStack i = new ItemStack(mat);
		final ItemMeta m = i.getItemMeta();
		m.setDisplayName(Utils.colored(name));
		i.setItemMeta(m);
		return i;
	}

	public ItemStack createVoteMapItem(Material mat, Arena a) {
		final ItemStack i = new ItemStack(mat);
		final ItemMeta m = i.getItemMeta();
		int votes = 0;
		for (final Arena arena : ArenaManager.getVotes().values()) {
			if (arena.equals(a))
				votes++;
		}
		m.setDisplayName(Common.colored(a.getDisplayName()));
		m.setLore(Arrays.asList("”fヘ玡布计: ”6" + votes));
		i.setItemMeta(m);
		return i;
	}

	public ItemStack createRandomMap(Material mat, String name) {
		final ItemStack i = new ItemStack(mat);
		final ItemMeta m = i.getItemMeta();
		final int votes = ArenaManager.getVoteRandom().size();
		m.setDisplayName(Utils.colored(name));
		final ArrayList<String> lore = new ArrayList<>();
		lore.add("”fヘ玡布计: ”6" + votes);
		m.setLore(lore);
		i.setItemMeta(m);
		return i;
	}

}
