package me.lulu.MitwSG;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.lulu.MitwSG.commands.SgCommand;
import me.lulu.MitwSG.listener.ArenaListener;
import me.lulu.MitwSG.listener.InventoryListener;
import me.lulu.MitwSG.listener.JoinQuitListener;
import me.lulu.MitwSG.listener.PlayerListener;
import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.manager.FileManager;
import me.lulu.MitwSG.manager.GameManager;
import me.lulu.MitwSG.manager.PlayerManager;
import me.lulu.MitwSG.manager.SgChestManager;
import me.lulu.MitwSG.options.Options;
import me.lulu.MitwSG.scoreboard.BoardSetup;
import me.lulu.MitwSG.utils.ItemBuilder;

public class Main extends JavaPlugin {
	/*
	 * 2018/7/11 10:23 Start 
	 * 2018/7/14 First Version release
	 * 2018/7/22 2.0 release
	 */
	public static Main ins;
	

	public void onEnable() {
		ins = this;
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "RedisBungee");
		registerStaticClasses();
		getSGm().registerChestItems();
		Main.getFlm().loadConfigs();
		Status.setState(Status.WAITING);
		registerCommands();
		registerEvents();
		getAm().setupGameRule();
		BoardSetup.setup();
	}

	public static Main get() {
		return ins;
	}

	public static SgChestManager getSGm() {
		return SgChestManager.scGm;
	}

	public static FileManager getFlm() {
		return FileManager.ins;
	}

	public static GameManager getGm() {
		return GameManager.ins;
	}

	public static PlayerManager getPm() {
		return PlayerManager.ins;
	}

	public static ArenaManager getAm() {
		return ArenaManager.ins;
	}

	public static ItemBuilder getIb() {
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
		PluginManager p = Bukkit.getPluginManager();
		p.registerEvents(new ArenaListener(), this);
		p.registerEvents(new JoinQuitListener(), this);
		p.registerEvents(new PlayerListener(), this);
		p.registerEvents(new InventoryListener(), this);
	}

	private void registerCommands() {
		getCommand("sg").setExecutor(new SgCommand());
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
