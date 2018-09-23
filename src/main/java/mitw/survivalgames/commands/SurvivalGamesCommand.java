package mitw.survivalgames.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.utils.Utils;

public class SurvivalGamesCommand extends BukkitCommand {

	public SurvivalGamesCommand() {
		super("sg");
	}

	int tir2PointRound = 0;

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.hasPermission("sg.admin"))
			return false;
		if (args.length < 1) {
			sender.sendMessage("/sg addspawn");
			return false;
		}

		final Player p = (Player) sender;
		String arenasName;
		Arena a;

		switch (args[0].toLowerCase()) {

		case "setlobby":
			PlayerManager.spawnLocation = p.getLocation();
			SurvivalGames.getFileManager().getClocation().set("Lobby", Utils.locToStrPitch(p.getLocation()));
			SurvivalGames.getFileManager().saveLocationConfig();
			p.sendMessage(Utils.colored("&a���\�]�w�����I"));
			break;
		case "clearmap":
			final World w = p.getWorld();
			EntityType et;
			int clearCount = 0;
			for (final Entity e : w.getEntities()) {
				et = e.getType();
				if (et.equals(EntityType.ITEM_FRAME) || et.equals(EntityType.PAINTING) || et.equals(EntityType.PLAYER)
						|| et.equals(EntityType.MINECART_HOPPER))
					continue;
				else {
					e.remove();
					clearCount++;
				}

			}
			p.sendMessage(Utils.colored("&a���\�M�� " + clearCount + " �ӥͪ�"));
			break;
		case "createarena":
			tir2PointRound = 0;
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) != null) {
				SurvivalGames.getArenaManager().loadArena(SurvivalGames.getArenaManager().getArena(arenasName));
				p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
				p.sendMessage(Utils.colored("&e���a�w�g�s�b!�]���N�z�ǰe�ܦ����a!"));
				return false;
			}
			SurvivalGames.getFileManager().writeNewArena(arenasName);
			SurvivalGames.getArenaManager().createNewArena(arenasName);
			p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
			p.sendMessage(Utils.colored("&a���a�Ыا���!,���a�����I�w�]���z�������I,�p���ݽШϥ�&f/sg setcenter&a�Ӧۦ�M�w�����I!"));
			break;
		case "tp":
			tir2PointRound = 0;
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			if (Bukkit.getWorld(arenasName) == null)
				SurvivalGames.getArenaManager().loadArena(SurvivalGames.getArenaManager().getArena(arenasName));
			p.teleport(Bukkit.getWorld(arenasName).getSpawnLocation());
			p.sendMessage(Utils.colored("&a���\�ǰe�� " + arenasName));
			break;
		case "addspawn":
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = SurvivalGames.getArenaManager().getArena(arenasName);
			a.addSpawn(p.getLocation());
			a.saveSpawns();
			p.sendMessage(Utils.colored("&a���\�s�W�����I,�ثe�@�� &f" + a.getSpawnPoints().size() + "&a �ӭ����I�w�g�Q�]�w"));
			break;
		case "nextt2":
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = SurvivalGames.getArenaManager().getArena(arenasName);
			p.teleport(a.getTir2Chests().get(tir2PointRound));
			tir2PointRound++;
			if (tir2PointRound >= a.getTir2Chests().size())
				tir2PointRound = 0;
			System.out.println(tir2PointRound);
			break;
		case "setcenter":
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = SurvivalGames.getArenaManager().getArena(arenasName);
			a.setCenter(p.getLocation());
			a.saveCenter();
			p.sendMessage(Utils.colored("&a���\�]�w�����I!"));
			break;
		case "addt2":
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) == null) {
				p.sendMessage("�����a���s�b");
				return false;
			}
			a = SurvivalGames.getArenaManager().getArena(arenasName);
			ArenaManager.editors.put(p.getUniqueId(), a);
			SurvivalGames.getPlayerManager().giveSetChestItem(p, arenasName);
			p.sendMessage(Utils.colored("&e�A���\��o�s�� " + a.getName() + " �����c�l�����v!"));
			break;
		case "builder":
			final UUID u = p.getUniqueId();
			if (PlayerManager.builders.contains(u)) {
				PlayerManager.builders.remove(u);
				p.sendMessage(Utils.colored("&e�ؿv�Ҧ�:&c ����"));
				break;
			}
			PlayerManager.builders.add(u);
			p.sendMessage(Utils.colored("&e�ؿv�Ҧ�:&a �}��"));
			break;
		case "autodetectchest":
			final Location loc = p.getLocation();
			final World wo = p.getWorld();
			boolean isTrap = false;
			for (int x = loc.getBlockX() + 500; x > loc.getBlockX() - 500; x--)
				for (int z = loc.getBlockZ() + 500; z > loc.getBlockZ() - 500; z--)
					for (int y = loc.getBlockY() - 40; y < loc.getBlockY() + 150; y++) {
						final Location bLoc = new Location(wo, x, y, z);
						if (wo.getBlockAt(bLoc).getType().equals(Material.ENDER_CHEST)) {
							System.out.println("detect");
							if (isTrap) {
								wo.getBlockAt(bLoc).setType(Material.CHEST);
								isTrap = false;
							} else {
								wo.getBlockAt(bLoc).setType(Material.TRAPPED_CHEST);
								isTrap = true;
							}
							SurvivalGames.getSgChestManager().setTir2(bLoc, ArenaManager.editors.get(p.getUniqueId()));
						}
					}
		}

		return false;
	}

}
