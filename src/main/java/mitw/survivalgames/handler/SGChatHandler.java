package mitw.survivalgames.handler;

import org.bukkit.entity.Player;

import mitw.survivalgames.manager.PlayerManager;
import net.development.mitw.chat.ChatHandler;

public class SGChatHandler implements ChatHandler{

	@Override
	public String getPrefix(Player p) {
		return "&7[&e" + PlayerManager.getCache(p.getUniqueId()).getRating() + "&7]";
	}

	@Override
	public String getSuffix(Player arg0) {
		return "";
	}

}
