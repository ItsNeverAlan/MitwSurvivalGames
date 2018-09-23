package me.lulu.MitwSG.inventorys;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.utils.Arena;
import me.lulu.MitwSG.utils.MenuBuilder;

public class VoteMenu extends MenuBuilder {

	public VoteMenu() {
		super("&6投票地圖", 3);
		int run = 0;
		for (Arena a : ArenaManager.arenas) {
			s(run, Main.getIb().createVoteMapItem(Material.EMPTY_MAP, a));
			run++;
		}
		Lang.RandomMap = Main.getIb().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
		s(i().getSize() - 1, Lang.RandomMap);

	}

	@Override
	public void onClick(Player p, ItemStack i, ItemStack[] items) {
		if (ArenaManager.voteRandom.contains(p.getUniqueId()))
			ArenaManager.voteRandom.remove(p.getUniqueId());
		if (ArenaManager.votes.containsKey(p.getUniqueId()))
			ArenaManager.votes.remove(p.getUniqueId());
		if (i.equals(Lang.RandomMap))
			ArenaManager.voteRandom.add(p.getUniqueId());
		else
			ArenaManager.votes.put(p.getUniqueId(), Main.getAm().getArena(i.getItemMeta().getDisplayName()));
		Lang.RandomMap = Main.getIb().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
		new VoteMenu().o(p);
	}

}
