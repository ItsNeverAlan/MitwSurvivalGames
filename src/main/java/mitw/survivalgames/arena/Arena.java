package mitw.survivalgames.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.World;

import lombok.Data;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.utils.Utils;

@Data
public class Arena {

	public List<Location> spawnPoints = new ArrayList<>();
	public List<Location> tir2Chests = new ArrayList<>();
	public List<Location> centerChests = new ArrayList<>();
	public Location center;
	private String name;
	private World world;

	public void addSpawn(final Location l) {
		spawnPoints.add(l);
	}

	public void addTir2(final Location l) {
		tir2Chests.add(l);
	}

	public void addCenter(final Location l) {
		centerChests.add(l);
	}

	public void saveSpawns() {
		final List<String> temp = spawnPoints.stream().map(Utils::locToStrPitch).collect(Collectors.toList());
		SurvivalGames.getFileManager().getClocation().set("Arenas." + name + ".spawnPoints", temp.toArray());
		SurvivalGames.getFileManager().saveLocationConfig();
	}

	public void saveCenter() {
		SurvivalGames.getFileManager().getClocation().set("Arenas." + name + ".center", Utils.locToStr(center));
		SurvivalGames.getFileManager().saveLocationConfig();
	}

	public void saveTir2Chests() {
		SurvivalGames.getFileManager().getClocation().set("Arenas." + name + ".Tir2s", Utils.locToStringList(tir2Chests).toArray());
		SurvivalGames.getFileManager().saveLocationConfig();
	}

	public void saveCenterChest() {
		SurvivalGames.getFileManager().getClocation().set("Arenas." + name + ".centerChest", Utils.locToStringList(centerChests).toArray());
		SurvivalGames.getFileManager().saveLocationConfig();
	}

}
