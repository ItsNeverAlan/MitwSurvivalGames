package mitw.survivalgames.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.guis.StatsGUI;

public class StatsCommand extends BukkitCommand{

	public StatsCommand() {
		super("stats");
	}

	@Override
	public boolean execute(CommandSender sender, String cmd, String[] args) {
		if (args.length > 0) {
			final Player target = Bukkit.getPlayer(args[0]);
			if (target != null) {
				new StatsGUI((Player)sender, SurvivalGames.getPlayerManager().getCache(target.getUniqueId())).o((Player)sender);
			} else {
				sender.sendMessage(SurvivalGames.getLanguage().translate((Player)sender, "notOnline"));
			}
			return true;
		}
		new StatsGUI((Player)sender, SurvivalGames.getPlayerManager().getCache(((Player)sender).getUniqueId())).o((Player)sender);
		return false;
	}

}
