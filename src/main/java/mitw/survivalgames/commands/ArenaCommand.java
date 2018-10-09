package mitw.survivalgames.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.utils.Common;
import mitw.survivalgames.utils.Utils;

public class ArenaCommand extends StaffCommands {

	public ArenaCommand() {
		super("arena");
	}

	private final String nullArena = "&c此場地不存在";

	@Override
	public void run(Player p, String[] args) {
		if (args.length < 1) {
			help();
			return;
		}
		Arena ar;
		switch (args[0].toLowerCase()) {
		case "create":
			checkArgsLengh(2, "&f使用方法: &7/arena create <場地名稱>");
			final String name = args[1];
			ar = ArenaManager.getArena(name);
			if (ar != null) {
				ArenaManager.loadArena(ar);
				p.teleport(ar.getCenter());
				returnTell("&e場地已經存在!因此將您傳送至此場地!");
			}
			ArenaManager.createNewArena(name);
			ar = ArenaManager.getArena(name);
			p.teleport(ar.getCenter());
			tell("&a場地創建完成!,場地中心點預設為您的重生點,如有需請使用&f/sg setcenter&a來自行決定中心點!");
			break;
		case "remove":

			break;
		case "setdisplayname":
			checkArgsLengh(3, "&f使用方法: &7/arena setdisplayname <場地名稱> <名稱>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ar.setDisplayName(args[2]);
			ar.saveDisplayName();
			tell("&a成功設定 " + ar.getName() + " 場地的顯示名稱為 " + ar.getDisplayName());
			break;
		case "tp":
			checkArgsLengh(2, "&f使用方法: &7/arena tp <場地名稱>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			if (Bukkit.getWorld(ar.getName()) == null)
				ArenaManager.loadArena(ar);
			p.teleport(ar.getCenter());
			tell("&a成功傳送至 " + ar.getName());
			break;
		case "addspawn":
			checkArgsLengh(2, "&f使用方法: &7/arena addspawn <場地名稱>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ar.addSpawn(p.getLocation());
			ar.saveSpawns();
			tell("&a成功新增重生點,目前共有&f " + ar.getSpawnPoints().size() + " &a個重生點已經被設定");
			break;
		case "setcenter":
			checkArgsLengh(2, "&f使用方法: &7/arena addspawn <場地名稱>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ar.setCenter(p.getLocation());
			ar.saveCenter();
			tell("&a成功設定中心點!");
			break;
		case "addt2":
			checkArgsLengh(2, "&f使用方法: &7/arena addt2 <場地名稱>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ArenaManager.getEditors().put(p.getUniqueId(), ar);
			PlayerManager.giveSetChestItem(p, ar.getName());
			p.sendMessage(Utils.colored("&e你成功獲得編輯 " + ar.getName() + " 高等箱子的授權!"));
			break;
		default:
			help();
			break;
		}
	}

	private void help() {
		Common.tell(p,
				"&8&m-----------------&6&lMitw&f&lSG&8&m-----------------",
				"",
				"&7/arena create <場地名稱>",
				"&7/arena tp <場地名稱>",
				"&7/arena addt2 <場地名稱>",
				"&7/arena addspawn <場地名稱>",
				"&7/arena setcenter <場地名稱>",
				"&7/arena setdisplayname <場地名稱> <名稱>",
				"",
				"&8&m---------------------------------------");
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if(args.length == 2) {
			final ArrayList<String> workArr = new ArrayList<>();
			for(final Arena a : ArenaManager.getArenas())
				if(a.getName().toLowerCase().startsWith(args[1].toLowerCase()))
					workArr.add(a.getName());
			Collections.sort(workArr);
			return workArr;
		}
		return null;
	}

}
