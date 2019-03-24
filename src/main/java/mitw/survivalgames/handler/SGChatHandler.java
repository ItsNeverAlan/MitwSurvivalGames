package mitw.survivalgames.handler;

import org.bukkit.entity.Player;

import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.ratings.rank.Rank;
import net.development.mitw.chat.ChatHandler;

public class SGChatHandler implements ChatHandler{

	@Override
	public String getPrefix(final Player p) {
		return "&7[" + Rank.getRank(p).getIcon() + PlayerManager.getCache(p.getUniqueId()).getRating() + "&7]";
	}

	@Override
	public String getSuffix(final Player arg0) {
		return "";
	}

}
