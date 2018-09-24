package mitw.survivalgames.listener;

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

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.scoreboard.ScoreHelper;
import mitw.survivalgames.tasks.LobbyTask;
import mitw.survivalgames.utils.Utils;

public class JoinQuitListener implements Listener {
	public boolean isCast = false;

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		SurvivalGames.getPlayerManager().createCache(p);
		switch (GameStatus.getState()) {
		case WAITING:
			final PlayerManager pm = SurvivalGames.getPlayerManager();
			pm.clearInventory(p);
			pm.tpToSpawn(p);
			pm.giveWaitingItem(p);
			pm.putUuidDb(p);
			p.setGameMode(GameMode.SURVIVAL);
			if (!isCast) {
				Bukkit.getScheduler().runTaskLater(SurvivalGames.getInstance(), () -> {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Lang.bungeeBroadCastCmd);
					isCast = true;
				}, 5);

			}
			if (SurvivalGames.getGameManager().canStart() && !GameManager.starting) {
				GameManager.starting = true;
				Utils.playSoundAll(Sound.NOTE_PLING);
				new LobbyTask().runTaskTimer(SurvivalGames.getInstance(), 0, 20);
			}
			e.setJoinMessage(Lang.pplJoin.replaceAll("<player>", p.getName()).replaceAll("<now>", String.valueOf(Bukkit.getOnlinePlayers().size())));
			break;
		default:
			SurvivalGames.getPlayerManager().setSpec(p);
			SurvivalGames.getPlayerManager().randomTeleportPlayer(p);
			e.setJoinMessage(null);
			break;
		}
	}

	@EventHandler
	public void onLeave(final PlayerQuitEvent e) {
		e.setQuitMessage(null);
		final Player p = e.getPlayer();
		ScoreHelper.removeScore(p);
		SurvivalGames.getPlayerManager().saveCache(p);
		switch (GameStatus.getState()) {
		case WAITING:
			PlayerManager.players.remove(p.getUniqueId());
			e.setQuitMessage(
					Lang.pplLeave.replaceAll("<player>", p.getName()).replaceAll("<now>", String.valueOf(Bukkit.getOnlinePlayers().size() - 1)));
			if (ArenaManager.votes.containsKey(p.getUniqueId())) {
				ArenaManager.votes.remove(p.getUniqueId());
			}
			if (ArenaManager.voteRandom.contains(p.getUniqueId())) {
				ArenaManager.voteRandom.remove(p.getUniqueId());
			}
			if (Bukkit.getOnlinePlayers().size() - 1 <= 0F) {
				isCast = false;
			}
			return;
		case GAMING:
			if (SurvivalGames.getPlayerManager().isGameingPlayer(p)) {
				p.setHealth(0.0);
				PlayerManager.players.remove(p.getUniqueId());
				SurvivalGames.getGameManager().checkWin();
			}
			break;
		case STARRTING:
			if (SurvivalGames.getPlayerManager().isGameingPlayer(p)) {
				PlayerManager.players.remove(p.getUniqueId());
			}
			break;
		case DMSTARTING:
			if (SurvivalGames.getPlayerManager().isGameingPlayer(p)) {
				p.setHealth(0.0);
				PlayerManager.players.remove(p.getUniqueId());
				SurvivalGames.getGameManager().checkWin();
			}
		case DEATHMATCH:
			if (SurvivalGames.getPlayerManager().isGameingPlayer(p)) {
				p.setHealth(0.0);
				PlayerManager.players.remove(p.getUniqueId());
				SurvivalGames.getGameManager().checkWin();
			}
		default:
			break;
		}
		PlayerManager.kills.remove(p.getUniqueId());
	}

	@EventHandler
	public void onLogin(final PlayerLoginEvent e) {
		if (GameStatus.isWaiting() && SurvivalGames.getGameManager().isFull()) {
			e.disallow(Result.KICK_WHITELIST, Utils.colored("&c遊戲滿人了!!"));
		} else if (GameStatus.isStarting()) {
			e.disallow(Result.KICK_WHITELIST, Utils.colored("&c遊戲正在傳送中,請稍等再加入觀戰"));
		} else if (GameStatus.isFinished()) {
			e.disallow(Result.KICK_WHITELIST, Utils.colored("&c遊戲結束了!等等再加入遊玩吧!"));
		}

	}

}
