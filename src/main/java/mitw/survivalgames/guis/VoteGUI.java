package mitw.survivalgames.guis;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.utils.Common;
import mitw.survivalgames.utils.MenuBuilder;

public class VoteGUI extends MenuBuilder {
	private final Map<ItemStack, Arena> arenaItems = new HashMap<>();

	public VoteGUI() {
		super("&6投票地圖", 3);
		int run = 0;
		for (final Arena a : ArenaManager.getArenas()) {
			s(run, SurvivalGames.getItemBuilder().createVoteMapItem(Material.EMPTY_MAP, a), a);
			run++;
		}
		Lang.RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
		s(i().getSize() - 1, Lang.RandomMap);

	}

	@Override
	public void onClick(Player p, ItemStack i, ItemStack[] items) {
		if (ArenaManager.getVoteRandom().contains(p.getUniqueId()))
			ArenaManager.getVoteRandom().remove(p.getUniqueId());
		if (ArenaManager.getVotes().containsKey(p.getUniqueId()))
			ArenaManager.getVotes().remove(p.getUniqueId());
		if (i.equals(Lang.RandomMap))
			ArenaManager.getVoteRandom().add(p.getUniqueId());
		else
			ArenaManager.getVotes().put(p.getUniqueId(), arenaItems.get(i));
		Common.sound(p, Sound.CLICK);
		Lang.RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
		new VoteGUI().o(p);
	}

	public void s(int i, ItemStack stack, Arena a) {
		super.s(i, stack);
		arenaItems.put(stack, a);
	}

}
