package mitw.survivalgames.commands;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import mitw.survivalgames.Lang;
import net.development.mitw.utils.Common;

public abstract class PlayerCommands extends Command {

	protected PlayerCommands(String name) {
		super(name);
	}

	Player p;
	String[] args;
	@Setter
	String prefix = Lang.prefix;

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Common.tell(sender, "&c這個指令只能由玩家使用!");
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

	public abstract void run(Player p, String[] args);

	public void tell(String msg) {
		Common.tell(p, prefix + "&r " + msg);
	}

	public void returnTell(String msg) {
		throw new CommandException(msg);
	}

	public void checkArgsLengh(int requireArgs, String message) {
		if (args.length != requireArgs)
			returnTell(message);
	}

	public int checkDigitalLegit(String toCheck, String message) {
		int number = 0;
		try {
			number = Integer.parseInt(toCheck);
		} catch (final Exception e) {
			returnTell(message);
		}
		return number;
	}

	public int checkDigitalLegit(String toCheck, int from, int to, String message) {
		int number = 0;
		try {
			number = Integer.parseInt(toCheck);
			Validate.isTrue(number <= to && number >= from);
		} catch (final Exception e) {
			returnTell(message);
		}
		return number;
	}

	public boolean reachRequireArgs(int requireArgs) {
		if (args.length >= requireArgs)
			return true;
		return false;
	}

	public void checkNull(Object obj, String message) {
		if (obj == null)
			returnTell(message);
	}

	@RequiredArgsConstructor
	final class CommandException extends RuntimeException {
		private static final long serialVersionUID = 1L;
		final String tellMessage;
	}

}
