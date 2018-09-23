package mitw.survivalgames.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Options {
	public static String locToStr(Location l) {
		return l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l.getWorld().getName();
	}

	public static Location StrToLoc(String a) {
		String[] temp = a.split(",");
		return new Location(Bukkit.getWorld(temp[3]), Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
	}

	public static String locToStrPitch(Location l) {
		return l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l.getYaw() + "," + l.getPitch() + "," + l.getWorld().getName();
	}

	public static Location StrToLocPitch(String a) {
		String[] temp = a.split(",");
		return new Location(Bukkit.getWorld(temp[5]), Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]),
				Float.parseFloat(temp[3]), Float.parseFloat(temp[4]));
	}

	public static String cc(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static void playSoundAll(Sound s) {
		for (Player p : Bukkit.getOnlinePlayers())
			p.playSound(p.getLocation(), s, 3, 1);

	}

	public static ArrayList<String> locToStringList(List<Location> l) {
		ArrayList<String> temp = new ArrayList<>();
		for (Location loc : l)
			temp.add(locToStr(loc));
		return temp;
	}

	public static ArrayList<String> locToStringListPitch(List<Location> l) {
		ArrayList<String> temp = new ArrayList<>();
		for (Location loc : l)
			temp.add(locToStrPitch(loc));
		return temp;
	}

	public static String timeFormat(int o) {
		String timer;
		int totalSecs = o;
		int hours = totalSecs / 3600;
		int minutes = totalSecs % 3600 / 60;
		int seconds = totalSecs % 60;
		if (totalSecs >= 3600)
			timer = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		else
			timer = String.format("%02d:%02d", minutes, seconds);
		return timer;
	}

	public static ArrayList<UUID> getTopArray(Map<UUID, Integer> map) {
		ArrayList<UUID> workArr = new ArrayList<>();
		Comparator<Map.Entry<UUID, Integer>> valueComparator = new Comparator<Map.Entry<UUID, Integer>>() {
			@Override
			public int compare(Entry<UUID, Integer> o1, Entry<UUID, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		};
		List<Map.Entry<UUID, Integer>> list = new ArrayList<Map.Entry<UUID, Integer>>(map.entrySet());
		Collections.sort(list, valueComparator);

		for (Map.Entry<UUID, Integer> entry : list) {
			workArr.add(entry.getKey());
		}
		return workArr;

	}

}
