package mitw.survivalgames.guis;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.ratings.RatingManager;
import mitw.survivalgames.ratings.rank.Rank;
import mitw.survivalgames.utils.MenuBuilder;
import net.development.mitw.language.LanguageAPI;
import net.development.mitw.utils.ItemUtil;
import net.development.mitw.uuid.UUIDCache;

public class StatsGUI extends MenuBuilder {

	public StatsGUI(final Player p, final PlayerCache playerCache) {
		super(SurvivalGames.getLanguage().translate(p, "statsTitle") + "&7: &e" + UUIDCache.getName(playerCache.getUuid()), 1);
		final LanguageAPI lang = SurvivalGames.getLanguage();
		s(0, SurvivalGames.getItemBuilder().itemWithName(Material.GOLD_INGOT, "&f" + lang.translate(p, "statsWins") + ":&6 " + playerCache.getWins()));
		s(1, SurvivalGames.getItemBuilder().itemWithName(Material.IRON_SWORD, "&f" + lang.translate(p, "statsKills") + ":&6 " + playerCache.getKills()));
		s(2, ItemUtil.createItem1(Material.SKULL_ITEM, 1, 2, "&f" + lang.translate(p, "statsDeaths") + ":&6 " + playerCache.getDeaths()));
		s(3, ItemUtil.createItem1(Material.BOOK, 1, 0, "&fKDR:&6 " + playerCache.getKDRString()));
		s(4, ItemUtil.createItem1(Material.GOLD_AXE, 1, 0, "&f" + lang.translate(p, "statsRating") + ":&b " + playerCache.getRating()));
		s(5, ItemUtil.createItem1(Material.DIAMOND, 1, 0, "&f" + lang.translate(p, "statsRankTop") + ":&b " + playerCache.getRatingTop()));
		s(6, ItemUtil.createItem1(Material.EMERALD, 1, 0, "&f" + lang.translate(p, "rank") + ":&b " + Rank.getRank(playerCache).getDisplayName(p)));
		s(8, ItemUtil.createItem1(Material.ENCHANTED_BOOK, 1, 0, "&e" + lang.translate(p, "statsLeaderboard")));
	}

	@Override
	public void onClick(final Player p, final ItemStack i, final ItemStack[] items) {
		if (i.getType() == Material.ENCHANTED_BOOK) {
			if (RatingManager.getInstance().getRatingTop().isEmpty()) {
				p.sendMessage("§c暫時沒有排行榜.. (還沒有儲存的資料!)");
			}
			p.closeInventory();
			new TopGUI(p).o(p);
		}
	}

}
