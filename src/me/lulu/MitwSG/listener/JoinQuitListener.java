package me.lulu.MitwSG.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import me.lulu.MitwSG.Lang;
import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import me.lulu.MitwSG.manager.ArenaManager;
import me.lulu.MitwSG.manager.GameManager;
import me.lulu.MitwSG.manager.PlayerManager;
import me.lulu.MitwSG.options.Options;
import me.lulu.MitwSG.scoreboard.ScoreHelper;
import me.lulu.MitwSG.tasks.LobbyTask;

public class JoinQuitListener implements Listener {
	public boolean isCast = false;

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		switch (Status.getState()) {
		case WAITING:
			PlayerManager pm = Main.getPm();
			pm.clearInventory(p);
			pm.tpToSpawn(p);
			pm.giveWaitingItem(p);
			pm.putUuidDb(p);
			p.setGameMode(GameMode.SURVIVAL);
			if (!isCast) {
				Bukkit.getScheduler().runTaskLater(Main.get(), new Runnable() {
					@Override
					public void run() {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Lang.bungeeBroadCastCmd);
						isCast = true;
					}
				}, 5);

			}
			if (Main.getGm().canStart() && !GameManager.starting) {
				GameManager.starting = true;
				Options.playSoundAll(Sound.NOTE_PLING);
				new LobbyTask().runTaskTimer(Main.get(), 0, 20);
			}
			e.setJoinMessage(Lang.pplJoin.replaceAll("<player>", p.getName()).replaceAll("<now>", String.valueOf(Bukkit.getOnlinePlayers().size())));
			break;
		default:
			Main.getPm().setSpec(p);
			Main.getPm().randomTeleportPlayer(p);
			e.setJoinMessage(null);
			break;
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player p = e.getPlayer();
		ScoreHelper.removeScore(p);
		switch (Status.getState()) {
		case WAITING:
			PlayerManager.players.remove(p.getUniqueId());
			e.setQuitMessage(
					Lang.pplLeave.replaceAll("<player>", p.getName()).replaceAll("<now>", String.valueOf(Bukkit.getOnlinePlayers().size() - 1)));
			if (ArenaManager.votes.containsKey(p.getUniqueId()))
				ArenaManager.votes.remove(p.getUniqueId());
			if (ArenaManager.voteRandom.contains(p.getUniqueId()))
				ArenaManager.voteRandom.remove(p.getUniqueId());
			if (Bukkit.getOnlinePlayers().size() - 1 <= 0F)
				isCast = false;
			break;
		case GAMING:
			if (Main.getPm().isGameingPlayer(p)) {
				p.setHealth(0.0);
				PlayerManager.players.remove(p.getUniqueId());
				Main.getGm().checkWin();
			}
			break;
		case STARRTING:
			if (Main.getPm().isGameingPlayer(p))
				PlayerManager.players.remove(p.getUniqueId());
			break;
		case DMSTARTING:
			if (Main.getPm().isGameingPlayer(p)) {
				p.setHealth(0.0);
				PlayerManager.players.remove(p.getUniqueId());
				Main.getGm().checkWin();
			}
		case DEATHMATCH:
			if (Main.getPm().isGameingPlayer(p)) {
				p.setHealth(0.0);
				PlayerManager.players.remove(p.getUniqueId());
				Main.getGm().checkWin();
			}
		default:
			break;
		}
	}

	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		if (Status.isWaiting() && Main.getGm().isFull())
			e.disallow(Result.KICK_WHITELIST, Options.colored("&c遊戲滿人了!!"));
		else if (Status.isStarting())
			e.disallow(Result.KICK_WHITELIST, Options.colored("&c遊戲正在傳送中,請稍等再加入觀戰"));
		else if (Status.isFinished())
			e.disallow(Result.KICK_WHITELIST, Options.colored("&c遊戲結束了!等等再加入遊玩吧!"));

	}

}
