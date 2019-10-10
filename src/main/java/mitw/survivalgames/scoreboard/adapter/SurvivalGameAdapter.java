package mitw.survivalgames.scoreboard.adapter;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.ratings.rank.Rank;
import mitw.survivalgames.scoreboard.FrameAdapter;
import mitw.survivalgames.tasks.*;
import mitw.survivalgames.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SurvivalGameAdapter implements FrameAdapter {

    @Override
    public String getTitle(Player player) {
        return GameStatus.isDeathMatch() ? Lang.deathMatchTitle : Lang.Title;
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> setboardList = new ArrayList<>();
        for (final String s : getList(player)) {
            setboardList.add(var(player, s));
        }
        return setboardList;
    }

    public static List<String> getList(final Player p) {
        switch (GameStatus.getState()) {
            case WAITING:
                return SurvivalGames.getLanguage().translateArrays(p, "watingList");
            case STARRTING:
                return SurvivalGames.getLanguage().translateArrays(p, "startingList");
            case GAMING:
                return SurvivalGames.getLanguage().translateArrays(p, "gamingList");
            case FINISH:
                return SurvivalGames.getLanguage().translateArrays(p, "finishList");
            case DEATHMATCH:
                return SurvivalGames.getLanguage().translateArrays(p, "dmList");
            case DMSTARTING:
                return SurvivalGames.getLanguage().translateArrays(p, "dmStartList");
        }
        return null;
    }

    public static String var(final Player p, String s) {
        final PlayerCache playerCache = PlayerManager.getCache(p.getUniqueId());
        s = s.replaceAll("<rating>", playerCache.getRating() + "");
        if (s.contains("<rank>")) {
            s = s.replaceAll("<rank>", Rank.getRank(playerCache).getDisplayName(p));
        }
        switch (GameStatus.getState()) {
            case WAITING:
                return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "§")
                        .replaceAll("<starting>", "" + LobbyTask.timeLeft).replaceAll("<server>", Lang.serverName);
            case STARRTING:
                return s.replaceAll("<players>", "" + Bukkit.getOnlinePlayers().size()).replaceAll("&", "§")
                        .replaceAll("<starting>", StartTask.timeLeft + "").replaceAll("<server>", Lang.serverName);
            case GAMING:
                return s.replaceAll("<players>", "" + PlayerManager.getPlayers().size()).replaceAll("&", "§")
                        .replaceAll("<time>", Utils.timeFormat(GameTask.timeLeft))
                        .replaceAll("<max>", PlayerManager.getMax() + "").replaceAll("<arena>", ArenaManager.getUsingArena().getName())
                        .replaceAll("<kills>", String.valueOf(PlayerManager.getKills(p))).replaceAll("<server>", Lang.serverName);
            case FINISH:
                return s.replaceAll("<player>", GameManager.winnerName).replaceAll("<playerKills>", GameManager.winnerKills).replaceAll("<time>", Utils.timeFormat(GameTask.timeLeft));
            case DEATHMATCH:
                return s.replaceAll("<players>", "" + PlayerManager.getPlayers().size()).replaceAll("&", "§")
                        .replaceAll("<time>", (DeathMatchTask.a <= 0 ? Utils.timeFormat(0) : Utils.timeFormat(DeathMatchTask.a)))
                        .replaceAll("<max>", PlayerManager.getMax() + "").replaceAll("<arena>", ArenaManager.getUsingArena().getName())
                        .replaceAll("<kills>", String.valueOf(PlayerManager.getKills(p))).replaceAll("<server>", Lang.serverName);
            case DMSTARTING:
                return s.replaceAll("<players>", "" + PlayerManager.getPlayers().size()).replaceAll("&", "§").replaceAll("<starting>", DmStartTask.count + "")
                        .replaceAll("<server>", Lang.serverName);
        }
        return s;
    }
}
