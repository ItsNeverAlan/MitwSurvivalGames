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
			p.sendMessage(Options.colored("&a成功設定重生點"));
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
			p.sendMessage(Options.colored("&a成功清除 " + clearCount + " 個生物"));
			break;
		case "createarena":
			tir2PointRound = 0;
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) != null) {
				Main.getAm().loadArena(Main.getAm().getArena(arenasName));
				p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
				p.sendMessage(Options.colored("&e場地已經存在!因此將您傳送至此場地!"));
				return false;
			}
			Main.getFlm().writeNewArena(arenasName);
			Main.getAm().createNewArena(arenasName);
			p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
			p.sendMessage(Options.colored("&a場地創建完成!,場地中心點預設為您的重生點,如有需請使用&f/sg setcenter&a來自行決定中心點!"));
			break;
		case "tp":
			tir2PointRound = 0;
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("此場地不存在");
				return false;
			}
			if (Bukkit.getWorld(arenasName) == null)
				Main.getAm().loadArena(Main.getAm().getArena(arenasName));
			p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
			p.sendMessage(Options.colored("&a成功傳送至 " + arenasName));
			break;
		case "addspawn":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("此場地不存在");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			a.addSpawn(p.getLocation());
			a.saveSpawns();
			p.sendMessage(Options.colored("&a成功新增重生點,目前共有 &f" + a.getSpawnPoints().size() + "&a 個重生點已經被設定"));
			break;
		case "nextt2":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("此場地不存在");
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
				p.sendMessage("此場地不存在");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			a.setCenter(p.getLocation());
			a.saveCenter();
			p.sendMessage(Options.colored("&a成功設定中心點!"));
			break;
		case "addt2":
			arenasName = args[1];
			if (Main.getAm().getArena(arenasName) == null) {
				p.sendMessage("此場地不存在");
				return false;
			}
			a = Main.getAm().getArena(arenasName);
			ArenaManager.editors.put(p.getUniqueId(), a);
			Main.getPm().giveSetChestItem(p, arenasName);
			p.sendMessage(Options.colored("&e你成功獲得編輯 " + a.getName() + " 高等箱子的授權!"));
			break;
		case "builder":
			UUID u = p.getUniqueId();
			if (PlayerManager.builders.contains(u)) {
				PlayerManager.builders.remove(u);
				p.sendMessage(Options.colored("&e建築模式:&c 關閉"));
				break;
			}
			PlayerManager.builders.add(u);
			p.sendMessage(Options.colored("&e建築模式:&a 開啟"));
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
