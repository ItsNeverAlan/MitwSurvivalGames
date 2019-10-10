package mitw.survivalgames;

import java.lang.reflect.Field;
import java.util.Arrays;

import mitw.survivalgames.handler.SGMovementHandler;
import mitw.survivalgames.scoreboard.Frame;
import mitw.survivalgames.scoreboard.adapter.SurvivalGameAdapter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import mitw.survivalgames.commands.ArenaCommand;
import mitw.survivalgames.commands.StatsCommand;
import mitw.survivalgames.commands.SurvivalGamesCommand;
import mitw.survivalgames.handler.SGChatHandler;
import mitw.survivalgames.listener.ArenaListener;
import mitw.survivalgames.listener.InventoryListener;
import mitw.survivalgames.listener.JoinQuitListener;
import mitw.survivalgames.listener.PlayerListener;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.FileManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.manager.SgChestManager;
import mitw.survivalgames.ratings.RatingManager;
import mitw.survivalgames.utils.FastRandom;
import mitw.survivalgames.utils.ItemBuilder;
import mitw.survivalgames.utils.Utils;
import net.development.mitw.Mitw;
import net.development.mitw.language.LanguageAPI;
import net.development.mitw.language.LanguageAPI.LangType;
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

	@Getter
	private static LanguageAPI language;

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

		SGMovementHandler.register();

		getArenaManager().setupGameRule();
		new Frame(this, new SurvivalGameAdapter());

	}

	public static SgChestManager getSgChestManager() {
		return SgChestManager.scGm;
	}

	public static FileManager getFileManager() {
		return FileManager.ins;
	}

	public static ArenaManager getArenaManager() {
		return ArenaManager.getIns();
	}

	public static ItemBuilder getItemBuilder() {
		return ItemBuilder.ins;
	}

	private void registerStaticClasses() {
		new FileManager();
		new Utils();
		new ItemBuilder();

		new ArenaManager();
		new SgChestManager();
		new GameManager();
		new PlayerManager();

		language = new LanguageAPI(LangType.CLASS, this, Mitw.getInstance().getLanguageData(), new Lang());

		new RatingManager();
		Mitw.getInstance().addChatHandler(new SGChatHandler());
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
		registerCommand(new ArenaCommand());
		Arrays.asList(
				new SurvivalGamesCommand(),
				new StatsCommand()
				)
		.forEach(command -> MinecraftServer.getServer().server.getCommandMap().register("mitwsg", command));
	}

	public static void registerCommand(final Command command) {
		try {
			final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);

			final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
			commandMap.register(command.getLabel(), command);

		} catch (final Exception e) {
			e.printStackTrace();
		}
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
