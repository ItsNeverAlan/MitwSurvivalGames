package mitw.survivalgames.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.utils.MenuBuilder;

public class VoteGUI extends MenuBuilder {

	public VoteGUI() {
		super("&6投票地圖", 3);
		int run = 0;
		for (Arena a : ArenaManager.arenas) {
			s(run, SurvivalGames.getItemBuilder().createVoteMapItem(Material.EMPTY_MAP, a));
			run++;
		}
		Lang.RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
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
			ArenaManager.votes.put(p.getUniqueId(), SurvivalGames.getArenaManager().getArena(i.getItemMeta().getDisplayName()));
		Lang.RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
		new VoteGUI().o(p);
	}

}
