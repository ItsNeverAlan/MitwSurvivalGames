package me.lulu.MitwSG.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.options.Options;
import me.lulu.MitwSG.utils.Arena;

public class FileManager {
	private File location, settings;
	private YamlConfiguration locConfig, settingsConfig;
	public static FileManager ins;

	public FileManager() {
		ins = this;
		setupFiles();
	}

	public void setupFiles() {
		if (!Main.get().getDataFolder().exists())
			Main.get().getDataFolder().mkdir();
		location = completeCreateFiles(location, "location.yml");
		settings = completeCreateFiles(settings, "settings.yml");
		locConfig = YamlConfiguration.loadConfiguration(location);
		settingsConfig = YamlConfiguration.loadConfiguration(settings);
	}

	public YamlConfiguration getClocation() {
		return locConfig;
	}

	public YamlConfiguration getCsettings() {
		return settingsConfig;
	}

	public void saveLocationConfig() {
		try {
			locConfig.save(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveSettingsConfig() {
		try {
			settingsConfig.save(settings);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File completeCreateFiles(File file, String name) {
		file = new File(Main.get().getDataFolder(), name);
		if (!file.exists())
			try {
				file.createNewFile();
				Main.get().saveResource(name, true);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return file;
	}

	public void writeNewArena(String name) {
		ArrayList<String> empty = new ArrayList<>();
		getClocation().set("Arenas." + name + ".spawnPoints", empty.toArray());
		getClocation().set("Arenas." + name + ".centerChest", empty.toArray());
		getClocation().set("Arenas." + name + ".Tir2s", empty.toArray());
		getClocation().set("Arenas." + name + ".center", null);
		saveLocationConfig();
	}

	public void loadConfigs() {
		loadLobby();
		loadArenas();
		loadOthers();
	}

	private void loadLobby() {
		if (getClocation().getString("Lobby") != null)
			PlayerManager.spawnLocation = (Options.StrToLocPitch(getClocation().getString("Lobby")));
	}

	private void loadArenas() {
		if (!getClocation().isConfigurationSection("Arenas"))
			return;
		for (String arena : getClocation().getConfigurationSection("Arenas").getKeys(false)) {
			/* load world first to let arena location can be cast */
			Arena a = new Arena();
			a.setName(arena);
			/* load arenas loacation */
			ArenaManager.arenas.add(a);
		}

	}

	private void loadOthers() {
		GameManager.minPlayerToStart = getCsettings().getInt("minPlayer");
		Lang.serverName = getCsettings().getString("serverName");
		Lang.bungeeBroadCastCmd = getCsettings().getString("broadCastCommand");
	}

}
