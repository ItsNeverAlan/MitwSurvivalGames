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

	private final String nullArena = "&c�����a���s�b";

	@Override
	public void run(Player p, String[] args) {
		if (args.length < 1) {
			help();
			return;
		}
		Arena ar;
		switch (args[0].toLowerCase()) {
		case "create":
			checkArgsLengh(2, "&f�ϥΤ�k: &7/arena create <���a�W��>");
			final String name = args[1];
			ar = ArenaManager.getArena(name);
			if (ar != null) {
				ArenaManager.loadArena(ar);
				p.teleport(ar.getCenter());
				returnTell("&e���a�w�g�s�b!�]���N�z�ǰe�ܦ����a!");
			}
			ArenaManager.createNewArena(name);
			ar = ArenaManager.getArena(name);
			p.teleport(ar.getCenter());
			tell("&a���a�Ыا���!,���a�����I�w�]���z�������I,�p���ݽШϥ�&f/sg setcenter&a�Ӧۦ�M�w�����I!");
			break;
		case "remove":

			break;
		case "setdisplayname":
			checkArgsLengh(3, "&f�ϥΤ�k: &7/arena setdisplayname <���a�W��> <�W��>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ar.setDisplayName(args[2]);
			ar.saveDisplayName();
			tell("&a���\�]�w " + ar.getName() + " ���a����ܦW�٬� " + ar.getDisplayName());
			break;
		case "tp":
			checkArgsLengh(2, "&f�ϥΤ�k: &7/arena tp <���a�W��>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			if (Bukkit.getWorld(ar.getName()) == null)
				ArenaManager.loadArena(ar);
			p.teleport(ar.getCenter());
			tell("&a���\�ǰe�� " + ar.getName());
			break;
		case "addspawn":
			checkArgsLengh(2, "&f�ϥΤ�k: &7/arena addspawn <���a�W��>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ar.addSpawn(p.getLocation());
			ar.saveSpawns();
			tell("&a���\�s�W�����I,�ثe�@��&f " + ar.getSpawnPoints().size() + " &a�ӭ����I�w�g�Q�]�w");
			break;
		case "setcenter":
			checkArgsLengh(2, "&f�ϥΤ�k: &7/arena addspawn <���a�W��>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ar.setCenter(p.getLocation());
			ar.saveCenter();
			tell("&a���\�]�w�����I!");
			break;
		case "addt2":
			checkArgsLengh(2, "&f�ϥΤ�k: &7/arena addt2 <���a�W��>");
			checkNull(ar = ArenaManager.getArena(args[1]), nullArena);
			ArenaManager.getEditors().put(p.getUniqueId(), ar);
			PlayerManager.giveSetChestItem(p, ar.getName());
			p.sendMessage(Utils.colored("&e�A���\��o�s�� " + ar.getName() + " �����c�l�����v!"));
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
				"&7/arena create <���a�W��>",
				"&7/arena tp <���a�W��>",
				"&7/arena addt2 <���a�W��>",
				"&7/arena addspawn <���a�W��>",
				"&7/arena setcenter <���a�W��>",
				"&7/arena setdisplayname <���a�W��> <�W��>",
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
