package mitw.survivalgames.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mitw.survivalgames.Lang;
import net.development.mitw.utils.Common;

public abstract class StaffCommands extends PlayerCommands {

	protected StaffCommands(String name) {
		super(name);
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Common.tell(sender, "&c這個指令只能由玩家使用!");
			return false;
		}
		if (!sender.hasPermission(Lang.ADMIN_PERM)) {
			Common.tell(sender, "&c抱歉,您沒有權限使用此指令");
			return false;
		}
		p = (Player) sender;
		this.args = args;
		try {
			run(p, args);
		} catch (final CommandException e) {
			tell(e.tellMessage);
		}
		return true;
	}
}
