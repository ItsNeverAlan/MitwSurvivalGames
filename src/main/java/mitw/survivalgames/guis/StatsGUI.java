package mitw.survivalgames.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.ratings.RatingManager;
import mitw.survivalgames.utils.MenuBuilder;
import net.development.mitw.utils.ItemUtil;

public class StatsGUI extends MenuBuilder {

	public StatsGUI(PlayerCache playerCache) {
		super("&6&l玩家&f&l資料", 1);
		s(0, SurvivalGames.getItemBuilder().itemWithName(Material.GOLD_INGOT, "&f勝利次數:&6 " + playerCache.getWins()));
		s(1, SurvivalGames.getItemBuilder().itemWithName(Material.IRON_SWORD, "&f擊殺次數:&6 " + playerCache.getKills()));
		s(2, ItemUtil.createItem1(Material.SKULL_ITEM, 1, 2, "&f死亡次數:&6 " + playerCache.getDeaths()));
		s(3, ItemUtil.createItem1(Material.BOOK, 1, 0, "&fKDR:&6 " + (playerCache.getDeaths() == 0 ? (double) playerCache.getKills() : ((double) playerCache.getKills() / playerCache.getDeaths()))));
		s(4, ItemUtil.createItem1(Material.GOLD_AXE, 1, 0, "&f積分:&b " + playerCache.getRating()));
		s(5, ItemUtil.createItem1(Material.DIAMOND, 1, 0, "&f積分排名:&b " + playerCache.getRatingTop()));
		s(8, ItemUtil.createItem1(Material.ENCHANTED_BOOK, 1, 0, "&e積分排行榜"));
	}

	@Override
	public void onClick(Player p, ItemStack i, ItemStack[] items) {
		if (i.getItemMeta().getDisplayName().equals("§e積分排行榜")) {
			if (RatingManager.getInstance().getRatingTop().isEmpty()) {
				p.sendMessage("§c暫時沒有排行榜.. (還沒有儲存的資料!)");
			}
			p.closeInventory();
			new TopGUI().o(p);
		}
	}

}
