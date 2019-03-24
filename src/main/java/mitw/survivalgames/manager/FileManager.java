package mitw.survivalgames.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.ratings.rank.Rank;
import mitw.survivalgames.utils.Utils;

public class FileManager {
	private File location, settings, ranks;
	private YamlConfiguration locConfig, settingsConfig, ranksConfig;
	public static FileManager ins;

	public FileManager() {
		ins = this;
		setupFiles();
	}

	public void setupFiles() {
		if (!SurvivalGames.getInstance().getDataFolder().exists()) {
			SurvivalGames.getInstance().getDataFolder().mkdir();
		}
		location = completeCreateFiles(location, "location.yml");
		settings = completeCreateFiles(settings, "settings.yml");
		ranks = completeCreateFiles(ranks, "ranks.yml");
		locConfig = YamlConfiguration.loadConfiguration(location);
		settingsConfig = YamlConfiguration.loadConfiguration(settings);
		ranksConfig = YamlConfiguration.loadConfiguration(ranks);
	}

	public YamlConfiguration getClocation() {
		return locConfig;
	}

	public YamlConfiguration getCsettings() {
		return settingsConfig;
	}

	public YamlConfiguration getRanks() {
		return ranksConfig;
	}

	public void saveRanks() {
		try {
			ranksConfig.save(ranks);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void saveLocationConfig() {
		try {
			locConfig.save(location);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void saveSettingsConfig() {
		try {
			settingsConfig.save(settings);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private File completeCreateFiles(File file, final String name) {
		file = new File(SurvivalGames.getInstance().getDataFolder(), name);
		if (!file.exists()) {
			try {
				file.createNewFile();
				SurvivalGames.getInstance().saveResource(name, true);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		return file;
	}

	public void writeNewArena(final String name) {
		final ArrayList<String> empty = new ArrayList<>();
		getClocation().set("Arenas." + name + ".displayName", "");
		getClocation().set("Arenas." + name + ".spawnPoints", empty.toArray());
		getClocation().set("Arenas." + name + ".centerChest", empty.toArray());
		getClocation().set("Arenas." + name + ".Tir2s", empty.toArray());
		getClocation().set("Arenas." + name + ".center", "");
		saveLocationConfig();
	}

	public void loadConfigs() {
		loadLobby();
		loadArenas();
		loadOthers();
		loadRanks();
	}

	private void loadRanks() {
		for (final String key : ranksConfig.getKeys(false)) {
			new Rank(ranksConfig.getConfigurationSection(key));
		}
	}

	private void loadLobby() {
		if (getClocation().getString("Lobby") != null) {
			PlayerManager.setSpawnLocation(Utils.StrToLocPitch(getClocation().getString("Lobby")));
		}
	}

	private void loadArenas() {
		if (!getClocation().isConfigurationSection("Arenas"))
			return;
		for (final String arena : getClocation().getConfigurationSection("Arenas").getKeys(false)) {
			/* load world first to let arena location can be cast */
			final Arena a = new Arena();
			a.setName(arena);
			a.setDisplayName(getClocation().getString("Arenas." + arena + ".displayName", a.getName()));
			/* load arenas loacation */
			ArenaManager.getArenas().add(a);
		}

	}

	private void loadOthers() {
		GameManager.minPlayerToStart = getCsettings().getInt("minPlayer");
		Lang.serverName = getCsettings().getString("serverName");
	}

}
