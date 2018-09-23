package me.lulu.MitwSG.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.manager.PlayerManager;
import me.lulu.MitwSG.options.Options;
import me.lulu.MitwSG.utils.Arena;

public class SgCommand implements CommandExecutor {
	int tir2PointRound = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("sg.admin"))
			return false;
		if (args.length < 1) {
			sender.sendMessage("/sg addspawn");
			return false;
		}
		Player p = (Player) sender;
		String arenasName;
		Arena a;
		switch (args[0].toLowerCase()) {
		case "setlobby":
			PlayerManager.spawnLocation = p.getLocation();
			Main.getFlm().getClocation().set("Lobby", Options.locToStrPitch(p.getLocation()));
			Main.getFlm().saveLocationConfig();
			p.sendMessage(Options.colored("&a���\�]�w�����I"));
			break;
		case "clearmap":
			World w = p.getWorld();
			EntityType et;
			int clearCount = 0;
			for (Entity e : w.getEntities()) {
				et = e.getType();
				if (et.equals(EntityType.ITEM_FRAME) || et.equals(EntityType.PAINTING) || et.equals(EntityType.PLAYER)
						|| et.equals(EntityType.MINECART_HOPPER))
					continue;
				else {
					e.remove();
					clearCount++;
				}

			}
			p.sendMessage(Options.colored("&a���\�M�� " + clearCount + " �ӥͪ�"));
			break;
		case "createarena":
			tir2PointRound = 0;
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) != null) {
				Main.getAm().loadArena(Main.getAm().getArena(arenasName));
				p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
				p.sendMessage(Options.colored("&e���a�w�g�s�b!�]���N�z�ǰe�ܦ����a!"));
				return false;
			}
			Main.getFlm().writeNewArena(arenasName);
			Main.getAm().createNewArena(arenasName);
			p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
			p.sendMessage(Options.colored("&a���a�Ыا���!,���a�����I�w�]���z�������I,�p���ݽШϥ�&f/sg setcenter&a�Ӧۦ�M�w�����I!"));
			break;
		case "tp":
			tir2PointRound = 0;
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			if (Bukkit.getWorld(arenasName) == null)
				Main.getAm().loadArena(Main.getAm().getArena(arenasName));
			p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
			p.sendMessage(Options.colored("&a���\�ǰe�� " + arenasName));
			break;
		case "addspawn":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			a.addSpawn(p.getLocation());
			a.saveSpawns();
			p.sendMessage(Options.colored("&a���\�s�W�����I,�ثe�@�� &f" + a.getSpawnPoints().size() + "&a �ӭ����I�w�g�Q�]�w"));
			break;
		case "nextt2":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			p.teleport(a.getTir2Chests().get(tir2PointRound));
			tir2PointRound++;
			if (tir2PointRound >= a.getTir2Chests().size())
				tir2PointRound = 0;
			System.out.println(tir2PointRound);
			break;
		case "setcenter":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			a.setCenter(p.getLocation());
			a.saveCenter();
			p.sendMessage(Options.colored("&a���\�]�w�����I!"));
			break;
		case "addt2":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			ArenaManager.editors.put(p.getUniqueId(), a);
			Main.getPm().giveSetChestItem(p, arenasName);
			p.sendMessage(Options.colored("&e�A���\��o�s�� " + a.getName() + " �����c�l�����v!"));
			break;
		case "builder":
			UUID u = p.getUniqueId();
			if (PlayerManager.builders.contains(u)) {
				PlayerManager.builders.remove(u);
				p.sendMessage(Options.colored("&e�ؿv�Ҧ�:&c ����"));
				break;
			}
			PlayerManager.builders.add(u);
			p.sendMessage(Options.colored("&e�ؿv�Ҧ�:&a �}��"));
			break;
		case "autodetectchest":
			Location loc = p.getLocation();
			World wo = p.getWorld();
			boolean isTrap = false;
			for (int x = loc.getBlockX() + 500; x > loc.getBlockX() - 500; x--)
				for (int z = loc.getBlockZ() + 500; z > loc.getBlockZ() - 500; z--)
					for (int y = loc.getBlockY() - 40; y < loc.getBlockY() + 150; y++) {
						Location bLoc = new Location(wo, x, y, z);
						if (wo.getBlockAt(bLoc).getType().equals(Material.ENDER_CHEST)) {
							System.out.println("detect");
							if (isTrap) {
								wo.getBlockAt(bLoc).setType(Material.CHEST);
								isTrap = false;
							} else {
								wo.getBlockAt(bLoc).setType(Material.TRAPPED_CHEST);
								isTrap = true;
							}
							Main.getSGm().setTir2(bLoc, ArenaManager.editors.get(p.getUniqueId()));
						}
					}
		default:
			break;
		}
		return false;
	}

}
