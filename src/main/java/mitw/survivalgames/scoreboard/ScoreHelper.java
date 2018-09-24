package mitw.survivalgames.scoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import lombok.Getter;

/**
 *
 * @author crisdev333
 *
 */
public class ScoreHelper {

	public static HashMap<UUID, ScoreHelper> players = new HashMap<>();

	public static boolean hasScore(final Player player) {
		return players.containsKey(player.getUniqueId());
	}

	public static ScoreHelper createScore(final Player player) {
		return new ScoreHelper(player);
	}

	public static ScoreHelper getByPlayer(final Player player) {
		return players.get(player.getUniqueId());
	}

	public static ScoreHelper removeScore(final Player player) {
		return players.remove(player.getUniqueId());
	}

	@SuppressWarnings("unused")
	private final UUID uuid;

	private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	private final Objective sidebar;

	private ScoreHelper(final Player player) {
		uuid = player.getUniqueId();
		sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
		sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
		// Create Teams
		for (int i = 1; i <= 15; i++) {
			final Team team = scoreboard.registerNewTeam("SLOT_" + i);
			team.addEntry(genEntry(i));
		}
		player.setScoreboard(scoreboard);
		players.put(player.getUniqueId(), this);
	}

	public void setTitle(String title) {
		title = ChatColor.translateAlternateColorCodes('&', title);
		sidebar.setDisplayName(title.length() > 32 ? title.substring(0, 32) : title);
	}

	public void setSlot(final int slot, String text) {
		final Team team = scoreboard.getTeam("SLOT_" + slot);
		final String entry = genEntry(slot);
		if (!scoreboard.getEntries().contains(entry)) {
			sidebar.getScore(entry).setScore(slot);
		}

		text = ChatColor.translateAlternateColorCodes('&', text);
		final Entry ts = split(text);
		team.setPrefix(ts.getLeft());
		team.setSuffix(ts.getRight());
	}

	public void removeSlot(final int slot) {
		final String entry = genEntry(slot);
		if (scoreboard.getEntries().contains(entry)) {
			scoreboard.resetScores(entry);
		}
	}

	public void setSlotsFromList(final List<String> list) {
		while (list.size() > 15) {
			list.remove(list.size() - 1);
		}

		int slot = list.size();

		if (slot < 15) {
			for (int i = (slot + 1); i <= 15; i++) {
				removeSlot(i);
			}
		}

		for (final String line : list) {
			setSlot(slot, line);
			slot--;
		}
	}

	private String genEntry(final int slot) {
		return ChatColor.values()[slot].toString();
	}

	public static Entry split(final String text) {
		final Entry entry = new Entry();
		if (text.length() <= 16) {
			entry.left = text;
			entry.right = "";
		} else {
			String prefix = text.substring(0, 16), suffix = "";
			if (prefix.endsWith("\u00a7")) {
				prefix = prefix.substring(0, prefix.length() - 1);
				suffix = "\u00a7" + suffix;
			}
			suffix = StringUtils.left(ChatColor.getLastColors(prefix) + suffix + text.substring(16), 16);
			entry.left = prefix;
			entry.right = suffix;
		}
		return entry;
	}

	public static class Entry {

		@Getter
		private String left = "", right = "";

	}

}