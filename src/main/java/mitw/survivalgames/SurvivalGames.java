package mitw.survivalgames;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import mitw.survivalgames.commands.SurvivalGamesCommand;
import mitw.survivalgames.listener.ArenaListener;
import mitw.survivalgames.listener.InventoryListener;
import mitw.survivalgames.listener.JoinQuitListener;
import mitw.survivalgames.listener.PlayerListener;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.FileManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.manager.SgChestManager;
import mitw.survivalgames.scoreboard.BoardSetup;
import mitw.survivalgames.utils.FastRandom;
import mitw.survivalgames.utils.ItemBuilder;
import mitw.survivalgames.utils.Utils;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class SurvivalGames extends JavaPlugin {

	/*
	 * 2018/7/11 10:23 Start
	 * 2018/7/14 First Version release
	 * 2018/7/22 2.0 release
	 */
	@Getter
	private static SurvivalGames instance;

	@Getter
	private static FastRandom random = new FastRandom();

	@Override
	public void onEnable() {
		instance = this;

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		registerStaticClasses();

		getSgChestManager().registerChestItems();
		getFileManager().loadConfigs();

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
		return ArenaManager.ins;
	}

	public static ItemBuilder getItemBuilder() {
		return ItemBuilder.ins;
	}

	private void registerStaticClasses() {
		new Utils();
		new ItemBuilder();

		new Lang();

		new ArenaManager();
		new SgChestManager();
		new GameManager();
		new PlayerManager();
		new FileManager();
	}

	private void registerEvents() {
		Arrays.asList(
				new ArenaListener(),
				new JoinQuitListener(),
				new PlayerListener(),
				new InventoryListener())
		.forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
	}

	private void registerCommands() {
		Arrays.asList(
				new SurvivalGamesCommand()
				)
		.forEach(command -> MinecraftServer.getServer().server.getCommandMap().register("mitwsg", command));
	}

	/*
	 * 1.Chest
	 * 2.lobby waiting
	 * 3.DeathMatch
	 * 4.Spawn
	 * 5.Score board
	 * 6.deathLighting
	 * 7.
	 *
	 */

}
