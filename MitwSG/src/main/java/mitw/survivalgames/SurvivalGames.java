package mitw.survivalgames;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import mitw.survivalgames.commands.SgCommand;
import mitw.survivalgames.listener.ArenaListener;
import mitw.survivalgames.listener.InventoryListener;
import mitw.survivalgames.listener.JoinQuitListener;
import mitw.survivalgames.listener.PlayerListener;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.FileManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.manager.SgChestManager;
import mitw.survivalgames.options.Options;
import mitw.survivalgames.scoreboard.BoardSetup;
import mitw.survivalgames.utils.FastRandom;
import mitw.survivalgames.utils.ItemBuilder;

public class SurvivalGames extends JavaPlugin {

	@Getter
	private static FastRandom random;

	@Getter
	private static SurvivalGames instance;


	@Override
	public void onEnable() {

		instance = this;

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		registerStaticClasses();
		getSgChestManager().registerChestItems();
		SurvivalGames.getFileManager().loadConfigs();

		GameStatus.setState(GameStatus.WAITING);

		registerCommands();
		registerEvents();

		getArenaManager().setupGameRule();
		BoardSetup.setup();
	}

	public static SgChestManager getSgChestManager() {
		return SgChestManager.scGm;
	}

	public static FileManager getFileManager() {
		return FileManager.ins;
	}

	public static GameManager getGameManager() {
		return GameManager.ins;
	}

	public static PlayerManager getPlayerManager() {
		return PlayerManager.ins;
	}

	public static ArenaManager getArenaManager() {
		return ArenaManager.getInstance();
	}

	public static ItemBuilder getItemBuilder() {
		return ItemBuilder.ins;
	}

	private void registerStaticClasses() {
		new Options();
		new ItemBuilder();

		new Lang();

		new ArenaManager();
		new SgChestManager();
		new GameManager();
		new PlayerManager();
		new FileManager();
	}

	private void registerEvents() {
		Arrays.asList(new ArenaListener(),
				new JoinQuitListener(),
				new PlayerListener(),
				new InventoryListener())
		.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, SurvivalGames.this));
	}

	private void registerCommands() {
		getCommand("sg").setExecutor(new SgCommand());
	}

}
