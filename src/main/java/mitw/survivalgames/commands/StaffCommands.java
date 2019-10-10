package mitw.survivalgames.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mitw.survivalgames.Lang;
import net.development.mitw.utils.Common;

public abstract class StaffCommands extends PlayerCommands {

	protected StaffCommands(final String name) {
		super(name);
	}

	@Override
	public boolean execute(final CommandSender sender, final String label, final String[] args) {
		if (!(sender instanceof Player)) {
			Common.tell(sender, "&cOnly staff can use this command!");
			return false;
		}
		if (!sender.hasPermission(Lang.ADMIN_PERM)) {
			Common.tell(sender, "&c你沒有權限.");
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
