package mitw.survivalgames.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mitw.survivalgames.Lang;

public class Common {

	public static void tell(CommandSender sender, String msg) {
		sender.sendMessage(colored(msg));
	}

	public static void tell(CommandSender sender, String... msgs) {
		for (final String s : msgs)
			sender.sendMessage(colored(s));
	}

	public static void prefixTell(CommandSender sender, String msg) {
		sender.sendMessage(colored(Lang.prefix + msg));
	}

	public static void prefixTell(CommandSender sender, String... msgs) {
		for (final String s : msgs)
			sender.sendMessage(colored(Lang.prefix + s));
	}

	public static void broadCast(String str) {
		Bukkit.broadcastMessage(colored(str));
	}

	public static void prefixBroadCast(String str) {
		Bukkit.broadcastMessage(colored(Lang.prefix + str));
	}


	public static String colored(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static ArrayList<String> colored(List<String> l) {
		final ArrayList<String> temp = new ArrayList<>();
		l.forEach(str -> temp.add(colored(str)));
		return temp;
	}

	public static void sound(Player p, Sound sound) {
		p.playSound(p.getLocation(), sound, 1, 1);
	}

	public static void soundAll(Sound sound) {
		Bukkit.getOnlinePlayers().forEach(p -> sound(p, sound));
	}


	public static void sound(Location loc,Sound sound) {
		loc.getWorld().playSound(loc, sound, 5, 1);
	}

	public static void registerCommand(Command command) {
		try {
			final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);

			final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
			commandMap.register(command.getLabel(), command);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
