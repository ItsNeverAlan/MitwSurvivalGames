package mitw.survivalgames.commands;

import java.util.UUID;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.utils.Utils;

public class SurvivalGamesCommand extends BukkitCommand {

	public SurvivalGamesCommand() {
		super("sg");
	}

	int tir2PointRound = 0;

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (!sender.hasPermission(Lang.ADMIN_PERM))
			return false;
		if (args.length < 1) {
			sender.sendMessage("/sg addspawn");
			return false;
		}

		final Player p = (Player) sender;
		switch (args[0].toLowerCase()) {

		case "setlobby":
			PlayerManager.setSpawnLocation(p.getLocation());
			SurvivalGames.getFileManager().getClocation().set("Lobby", Utils.locToStrPitch(p.getLocation()));
			SurvivalGames.getFileManager().saveLocationConfig();
			p.sendMessage(Utils.colored("&a成功設定重生點"));
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
			p.sendMessage(Utils.colored("&a成功清除 " + clearCount + " 個生物"));
			break;
			/*case "nextt2":
			arenasName = args[1];
			if (SurvivalGames.getArenaManager().getArena(arenasName) == null) {
				p.sendMessage("此場地不存在");
				return false;
			}
			a = SurvivalGames.getArenaManager().getArena(arenasName);
			p.teleport(a.getTir2Chests().get(tir2PointRound));
			tir2PointRound++;
			if (tir2PointRound >= a.getTir2Chests().size())
				tir2PointRound = 0;
			System.out.println(tir2PointRound);
			break;*/
		case "builder":
			final UUID u = p.getUniqueId();
			if (PlayerManager.getBuilders().contains(u)) {
				PlayerManager.getBuilders().remove(u);
				p.sendMessage(Utils.colored("&e建築模式:&c 關閉"));
				break;
			}
			PlayerManager.getBuilders().add(u);
			p.sendMessage(Utils.colored("&e建築模式:&a 開啟"));
			break;
			/*case "autodetectchest":
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
					}*/
		}

		return false;
	}

}
