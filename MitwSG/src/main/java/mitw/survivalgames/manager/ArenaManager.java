package mitw.survivalgames.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

import lombok.Getter;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.options.Options;
import mitw.survivalgames.utils.Arena;

public class ArenaManager {

	@Getter
	private static ArenaManager instance;

	public static Map<UUID, Arena> editors = new HashMap<>();
	public static Map<UUID, Arena> votes = new HashMap<>();

	public static List<Material> canBreak = new ArrayList<>();
	public static List<Material> specCantUse = new ArrayList<>();
	public static List<UUID> voteRandom = new ArrayList<>();
	public static List<Arena> arenas = new ArrayList<>();

	public static Arena usingArena;
	public static String usingArenaName;

	public ArenaManager() {
		instance = this;
		setupCanBreaks();
		setupSpecCantuse();
	}

	public void refillChests() {
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
			if (a.getName().toLowerCase().equals(s.toLowerCase())) {
				return a;
			}
		}
		return null;
	}

	public void loadArena(Arena a) {
		Bukkit.broadcastMessage(Options.cc("&e地圖生成中......."));
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

		a.setCenter(Options.StrToLoc(c.getString("Arenas." + arenaName + ".center")));

		for (final String spawn : c.getStringList("Arenas." + arenaName + ".spawnPoints")) {
			a.addSpawn(Options.StrToLocPitch(spawn));
		}

		for (final String tir2 : c.getStringList("Arenas." + arenaName + ".Tir2s")) {
			a.addTir2(Options.StrToLoc(tir2));
		}

		for (final String centerChest : c.getStringList("Arenas." + arenaName + ".centerChest")) {
			a.addCenter(Options.StrToLoc(centerChest));
		}

		Bukkit.broadcastMessage(Options.cc("&e生成完畢"));
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
		Bukkit.getScheduler().runTaskLater(SurvivalGames.getInstance(), () -> Bukkit.getWorlds().forEach(w -> {

			w.setMonsterSpawnLimit(0);
			w.setAnimalSpawnLimit(0);

			w.setGameRuleValue("doMobSpawning", "false");
			w.setGameRuleValue("doDaylightCycle", "false");
			w.setTime(6000);

			w.setDifficulty(Difficulty.PEACEFUL);
			w.setDifficulty(Difficulty.EASY);

		}), 100L);

	}

}
