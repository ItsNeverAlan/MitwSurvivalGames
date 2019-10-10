package mitw.survivalgames.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldServer;

public class Utils {
	public static String locToStr(final Location l) {
		return l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l.getWorld().getName();
	}

	public static Location StrToLoc(final String a) {
		final String[] temp = a.split(",");
		return new Location(Bukkit.getWorld(temp[3]), Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
	}

	public static String locToStrPitch(final Location l) {
		return l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + "," + l.getYaw() + "," + l.getPitch() + "," + l.getWorld().getName();
	}

	public static Location StrToLocPitch(final String a) {
		final String[] temp = a.split(",");
		return new Location(Bukkit.getWorld(temp[5]), Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]),
				Float.parseFloat(temp[3]), Float.parseFloat(temp[4]));
	}

	public static String colored(final String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

	public static void playSoundAll(final Sound s) {
		for (final Player p : Bukkit.getOnlinePlayers()) {
			p.playSound(p.getLocation(), s, 3, 1);
		}
	}

	public static List<String> locToStringList(final List<Location> l) {
		return l.stream().map(Utils::locToStr).collect(Collectors.toList());
	}

	public static List<String> locToStringListPitch(final List<Location> l) {
		return l.stream().map(Utils::locToStrPitch).collect(Collectors.toList());
	}

	public static String timeFormat(final int o) {
		String timer;
		final int totalSecs = o;
		final int hours = totalSecs / 3600;
		final int minutes = totalSecs % 3600 / 60;
		final int seconds = totalSecs % 60;
		if (totalSecs >= 3600) {
			timer = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			timer = String.format("%02d:%02d", minutes, seconds);
		}
		return timer;
	}

	public static ArrayList<UUID> getTopArray(final Map<UUID, Integer> map) {
		final ArrayList<UUID> workArr = new ArrayList<>();
		final Comparator<Map.Entry<UUID, Integer>> valueComparator = (o1, o2) -> o2.getValue() - o1.getValue();
		final List<Map.Entry<UUID, Integer>> list = new ArrayList<>(map.entrySet());
		Collections.sort(list, valueComparator);

		for (final Map.Entry<UUID, Integer> entry : list) {
			workArr.add(entry.getKey());
		}
		return workArr;

	}

}
