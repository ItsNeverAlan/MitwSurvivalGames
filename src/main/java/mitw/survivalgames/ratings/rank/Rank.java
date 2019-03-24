package mitw.survivalgames.ratings.rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.ratings.PlayerCache;
import net.development.mitw.Mitw;
import net.development.mitw.utils.StringUtil;

public class Rank {

	private static final List<Rank> ranks = new ArrayList<>();

	private final String name;
	private final int min;
	private final int max;
	private final Map<String, String> displayName = new HashMap<>();

	public Rank(final ConfigurationSection section) {
		this.name = section.getName();
		this.min = section.getInt("min");
		if (section.get("max") instanceof String) {
			this.max = Integer.MAX_VALUE;
		} else {
			this.max = section.getInt("max");
		}
		final ConfigurationSection section2 = section.getConfigurationSection("displayname");
		for (final String key : section2.getKeys(false)) {
			this.displayName.put(key, StringUtil.cc(section2.getString(key)));
		}
		ranks.add(this);
	}

	public boolean isInRange(final int elo) {
		return elo > min && elo < max;
	}

	public String getDisplayName(final Player player) {
		return displayName.get(Mitw.getInstance().getLanguageData().getLang(player));
	}

	public String getIcon() {
		return displayName.get("icon");
	}

	public static Rank getRank(final Player player) {
		return getRank(player.getUniqueId());
	}

	public static Rank getRank(final UUID uuid) {
		return getRank(PlayerManager.getCache(uuid));
	}

	public static Rank getRank(final PlayerCache playerCache) {
		for (final Rank rank : ranks) {
			if (rank.isInRange(playerCache.getRating()))
				return rank;
		}
		return null;
	}

	public static Rank getRank(final int rating) {
		for (final Rank rank : ranks) {
			if (rank.isInRange(rating))
				return rank;
		}
		return null;
	}

}
