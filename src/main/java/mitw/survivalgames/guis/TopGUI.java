package mitw.survivalgames.guis;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.ratings.RatingManager;
import mitw.survivalgames.utils.MenuBuilder;
import net.development.mitw.utils.ItemBuilder;

public class TopGUI extends MenuBuilder {

	public TopGUI() {
		super("&b&l排行&f&l榜", 1);

		ItemBuilder itemBuilder = new ItemBuilder(Material.DIAMOND_BLOCK);
		itemBuilder.name("&b&l積分");

		Map<String, Integer> top = RatingManager.getInstance().getRatingTop();

		int i = 1;
		for (final String name : top.keySet()) {
			itemBuilder.lore("§e" + i + ") " + name + " §7- §6" + top.get(name));
			i++;
		}

		s(0, itemBuilder.build());

		itemBuilder = new ItemBuilder(Material.GOLD_BLOCK);
		itemBuilder.name("&6&l勝利次數");

		top = RatingManager.getInstance().getWinsTop();

		i = 1;
		for (final String name : top.keySet()) {
			itemBuilder.lore("§e" + i + ") " + name + " §7- §6" + top.get(name));
			i++;
		}

		s(1, itemBuilder.build());

		itemBuilder = new ItemBuilder(Material.IRON_BLOCK);
		itemBuilder.name("&e&l擊殺次數");

		top = RatingManager.getInstance().getKillsTop();

		i = 1;
		for (final String name : top.keySet()) {
			itemBuilder.lore("§e" + i + ") " + name + " §7- §6" + top.get(name));
			i++;
		}

		s(2, itemBuilder.build());

		final Map<String, Double> top2 = RatingManager.getInstance().getKdrTop();

		itemBuilder = new ItemBuilder(Material.REDSTONE_BLOCK);
		itemBuilder.name("&e&lKDR");

		i = 1;
		for (final String name : top2.keySet()) {
			itemBuilder.lore("§e" + i + ") " + name + " §7- §6" + top2.get(name));
			i++;
		}

		s(3, itemBuilder.build());
	}

	@Override
	public void onClick(Player p, ItemStack i, ItemStack[] items) {}

}
