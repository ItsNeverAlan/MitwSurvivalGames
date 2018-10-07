package mitw.survivalgames.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.utils.Utils;

public class ArenaManager {
	public static ArenaManager ins;
	public static ArrayList<Material> canBreak = new ArrayList<>();
	public static ArrayList<Material> specCantUse = new ArrayList<>();
	public static HashMap<UUID, Arena> editors = new HashMap<>();
	public static HashMap<UUID, Arena> votes = new HashMap<>();
	public static ArrayList<UUID> voteRandom = new ArrayList<>();
	public static ArrayList<Arena> arenas = new ArrayList<>();
	public static Arena usingArena;
	public static String usingArenaName;

	public ArenaManager() {
		ins = this;
		setupCanBreaks();
		setupSpecCantuse();
	}

	public void reFillChest() {
		SgChestManager.opened.clear();
	}

	public void createNewArena(String name) {
		final Arena a = new Arena();
		a.setName(name);
		arenas.add(a);
		Bukkit.createWorld(new WorldCreator(name));
		/* debug */
		a.setWorld(Bukkit.getWorld(name));
		a.setCenter(a.getWorld().getSpawnLocation());
	}

	public Arena getArena(String s) {
		for (final Arena a : arenas) {
			if (a.getName().toLowerCase().equals(s.toLowerCase()))
				return a;
		}
		return null;
	}

	public void loadArena(Arena a) {
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(SurvivalGames.getLanguage().translate(player, "generatingWorld")));
		final String arenaName = a.getName();
		final FileConfiguration c = SurvivalGames.getFileManager().getClocation();
		final World w = Bukkit.createWorld(new WorldCreator(arenaName));
		w.setAutoSave(false);
		a.setWorld(w);
		w.setMonsterSpawnLimit(0);
		w.setAnimalSpawnLimit(0);
		w.setGameRuleValue("doMobSpawning", "false");
		w.setGameRuleValue("doDaylightCycle", "false");
		w.setGameRuleValue("keepInventory", "false");
		w.setTime(6000);
		w.setDifficulty(Difficulty.PEACEFUL);
		w.setDifficulty(Difficulty.EASY);
		a.setCenter(Utils.StrToLoc(c.getString("Arenas." + arenaName + ".center")));
		for (final String spawn : c.getStringList("Arenas." + arenaName + ".spawnPoints"))
			a.addSpawn(Utils.StrToLocPitch(spawn));
		for (final String tir2 : c.getStringList("Arenas." + arenaName + ".Tir2s"))
			a.addTir2(Utils.StrToLoc(tir2));
		for (final String centerChest : c.getStringList("Arenas." + arenaName + ".centerChest"))
			a.addCenter(Utils.StrToLoc(centerChest));
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(SurvivalGames.getLanguage().translate(player, "generatedWorld")));
	}

	private void setupCanBreaks() {
		canBreak.add(Material.LEAVES);
		canBreak.add(Material.LEAVES_2);
		canBreak.add(Material.LONG_GRASS);
		canBreak.add(Material.DOUBLE_PLANT);
		canBreak.add(Material.YELLOW_FLOWER);
	}

	private void setupSpecCantuse() {
		specCantUse.add(Material.WOOD_PLATE);
		specCantUse.add(Material.STONE_PLATE);
		specCantUse.add(Material.IRON_PLATE);
		specCantUse.add(Material.GOLD_PLATE);
		specCantUse.add(Material.STRING);
	}

	public void setupGameRule() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (final World w : Bukkit.getWorlds()) {
					w.setMonsterSpawnLimit(0);
					w.setAnimalSpawnLimit(0);
					w.setGameRuleValue("doMobSpawning", "false");
					w.setGameRuleValue("doDaylightCycle", "false");
					w.setTime(6000);
					w.setDifficulty(Difficulty.PEACEFUL);
					w.setDifficulty(Difficulty.EASY);
				}
			}
		}.runTaskLater(SurvivalGames.getInstance(), 20 * 5);

	}

}
