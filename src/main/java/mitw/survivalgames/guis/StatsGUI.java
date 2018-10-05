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
		super("&6&l���a&f&l���", 1);
		s(0, SurvivalGames.getItemBuilder().itemWithName(Material.GOLD_INGOT, "&f�ӧQ����:&6 " + playerCache.getWins()));
		s(1, SurvivalGames.getItemBuilder().itemWithName(Material.IRON_SWORD, "&f��������:&6 " + playerCache.getKills()));
		s(2, ItemUtil.createItem1(Material.SKULL_ITEM, 1, 2, "&f���`����:&6 " + playerCache.getDeaths()));
		s(3, ItemUtil.createItem1(Material.BOOK, 1, 0, "&fKDR:&6 " + (playerCache.getDeaths() == 0 ? (double) playerCache.getKills() : ((double) playerCache.getKills() / playerCache.getDeaths()))));
		s(4, ItemUtil.createItem1(Material.GOLD_AXE, 1, 0, "&f�n��:&b " + playerCache.getRating()));
		s(5, ItemUtil.createItem1(Material.DIAMOND, 1, 0, "&f�n���ƦW:&b " + playerCache.getRatingTop()));
		s(8, ItemUtil.createItem1(Material.ENCHANTED_BOOK, 1, 0, "&e�n���Ʀ�]"));
	}

	@Override
	public void onClick(Player p, ItemStack i, ItemStack[] items) {
		if (i.getItemMeta().getDisplayName().equals("��e�n���Ʀ�]")) {
			if (RatingManager.getInstance().getRatingTop().isEmpty()) {
				p.sendMessage("��c�ȮɨS���Ʀ�].. (�٨S���x�s�����!)");
			}
			p.closeInventory();
			new TopGUI().o(p);
		}
	}

}
