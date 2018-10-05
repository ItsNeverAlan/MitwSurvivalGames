package mitw.survivalgames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.utils.Utils;

public class Lang {

	public static String prefix = Utils.colored("&7[&6&lMitw&f&lSG&7]&f ");
	public static String Title = "&6&lMitw &7| &fSG";
	public static String deathMatchTitle = "&c&lMitw &7| &fSG";

	public static ItemStack specTp, playAnotherGame, returnToLobby, iVoteMap, RandomMap, arrowTrails, statsItem;

	public static String serverName;

	public String zh_tw_statsWins = "勝利次數";
	public String zh_tw_statsKills = "擊殺次數";
	public String zh_tw_statsDeaths = "死亡次數";
	public String zh_tw_statsRating = "積分";
	public String zh_tw_statsRankTop = "積分排名";
	public String zh_tw_statsLeaderboard = "排行榜";
	public String zh_tw_statsTitle = "&6&l玩家&f&l資料";
	public String zh_tw_lbTitle = "&b&l排行&f&l榜";

	public List<String> zh_tw_watingList = Arrays.asList(
			"&7&m---------------------------",
			"&6人數: &f<players>",
			"&6投票結束: &f<starting>秒",
			"&6伺服器分流: &f<server>",
			"",
			"&b積分:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_startingList = Arrays.asList(
			"&7&m---------------------------",
			"&6人數: &f<players>",
			"&6遊戲開始: &f<starting>秒",
			"&6伺服器分流: &f<server>",
			"",
			"&b積分:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_gamingList = Arrays.asList(
			"&7&m---------------------------",
			"&6時間:&f <time>",
			"&6地圖:&f <arena>",
			"&6人數:&f <players>/<max>",
			"",
			"&6擊殺數:&f <kills>",
			"&b積分:&f <rating>",
			"",
			"&6伺服器分流: &f<server>",
			"&7&m---------------------------");
	public List<String> zh_tw_finishList = Arrays.asList(
			"&7&m---------------------------",
			"&6時間:&f <time>",
			"&e勝利者:&f <player>",
			"&6擊殺數:&f <playerKills>",
			"",
			"&b積分:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_dmStartList = Arrays.asList(
			"&7&m---------------------------",
			"&c人數: &f<players>",
			"&c死亡之戰開始: &f<starting>秒",
			"&c伺服器分流: &f<server>",
			"",
			"&b積分:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_dmList = Arrays.asList(
			"&7&m---------------------------",
			"&c時間:&f <time>",
			"&c地圖:&f <arena>",
			"&c人數:&f <players>/<max>",
			"",
			"&c擊殺數:&f <kills>",
			"&b積分:&f <rating>",
			"",
			"&c伺服器分流: &f<server>",
			"&7&m---------------------------");

	public String zh_tw_outArena, zh_tw_notOnline;
	public String zh_tw_cantStart, zh_tw_kills;
	public String zh_tw_needDeathMatch, zh_tw_seconds, zh_tw_minutes;
	public String zh_tw_lobbyCount, zh_tw_startCount, zh_tw_gameCount, zh_tw_deathMatchStartCount, zh_tw_allStrikeLightCount;
	public String zh_tw_youKills1, zh_tw_youGotKillByS1, zh_tw_s1Death;
	public String zh_tw_pplJoin, zh_tw_pplLeave, zh_tw_ratingAdded, zh_tw_ratingRemoved, zh_tw_ratingRemoveReason;
	public String zh_tw_gameStarted, zh_tw_allLightStart;
	public List<String> zh_tw_victoryMsg = new ArrayList<>();
	public List<String> zh_tw_infoMessage = new ArrayList<>();

	public List<String> en_us_watingList = Arrays.asList(
			"&7&m---------------------------",
			"&6Players: &f<players>",
			"&6Scattering in: &f<starting>second(s)",
			"&6Server: &f<server>",
			"",
			"&bRating:&f <rating>",
			"&7&m---------------------------");
	public List<String> en_us_startingList = Arrays.asList(
			"&7&m---------------------------",
			"&6Player: &f<players>",
			"&6Starting in: &f<starting>second(s)",
			"&6Server: &f<server>",
			"",
			"&bRating:&f <rating>",
			"&7&m---------------------------");
	public List<String> en_us_gamingList = Arrays.asList(
			"&7&m---------------------------",
			"&6Time:&f <time>",
			"&6Map:&f <arena>",
			"&6Remaining:&f <players>/<max>",
			"",
			"&6Kills:&f <kills>",
			"&bRating:&f <rating>",
			"",
			"&6Server: &f<server>",
			"&7&m---------------------------");
	public List<String> en_us_finishList = Arrays.asList(
			"&7&m---------------------------",
			"&6Time:&f <time>",
			"&eWinner:&f <player>",
			"&6Kills:&f <playerKills>",
			"",
			"&bRating:&f <rating>",
			"&7&m---------------------------");
	public List<String> en_us_dmStartList = Arrays.asList(
			"&7&m---------------------------",
			"&cPlayers: &f<players>",
			"&cDeathmatch in: &f<starting>second(s)",
			"&cServer: &f<server>",
			"",
			"&bRating:&f <rating>",
			"&7&m---------------------------");
	public List<String> en_us_dmList = Arrays.asList(
			"&7&m---------------------------",
			"&cTime:&f <time>",
			"&cMap:&f <arena>",
			"&cRemaining:&f <players>/<max>",
			"",
			"&cKills:&f <kills>",
			"&bRating:&f <rating>",
			"",
			"&cServer: &f<server>",
			"&7&m---------------------------");
	public String en_us_outArena, en_us_notOnline;
	public String en_us_cantStart, en_us_kills;
	public String en_us_needDeathMatch, en_us_seconds, en_us_minutes;
	public String en_us_lobbyCount, en_us_startCount, en_us_gameCount, en_us_deathMatchStartCount, en_us_allStrikeLightCount;
	public String en_us_youKills1, en_us_youGotKillByS1, en_us_s1Death;
	public String en_us_pplJoin, en_us_pplLeave, en_us_ratingAdded, en_us_ratingRemoved, en_us_ratingRemoveReason;
	public String en_us_gameStarted, en_us_allLightStart;
	public String en_us_serverName, en_us_bungeeBroadCastCmd;
	public List<String> en_us_victoryMsg = new ArrayList<>();
	public List<String> en_us_infoMessage = new ArrayList<>();

	public String en_us_statsWins = "Wins";
	public String en_us_statsKills = "Kills";
	public String en_us_statsDeaths = "Deaths";
	public String en_us_statsRating = "Rating";
	public String en_us_statsRankTop = "Rating Top";
	public String en_us_statsLeaderboard = "Leaderboard";
	public String en_us_statsTitle = "&6&lPlayer&f&l stats";
	public String en_us_lbTitle = "&b&lLeader&f&lboard";

	public Lang() {
		setupList();
		setupString();
		setupItems();
	}

	public void setupString() {
		zh_tw_kills = "擊殺";
		zh_tw_ratingAdded = Utils.colored("&6積分 +");
		zh_tw_ratingRemoved = Utils.colored("&c積分 -");
		zh_tw_ratingRemoveReason = Utils.colored("&7(原有積分高於&c1200&7 並且在遊戲中死亡)");
		zh_tw_outArena = Utils.colored(prefix + "&c&l你已經超出&f&l場地限制範圍!!&c&l請趕快回場地!!!");
		zh_tw_cantStart = Utils.colored(prefix + "&c遊戲人數不足造成無法開始");
		zh_tw_needDeathMatch = Utils.colored(prefix + "&c&l由於人數剩下&f&l4人!&c&l因此加快遊戲速度!&f&l2分鐘後便要&c&l死亡之戰!");
		zh_tw_gameStarted = Utils.colored(prefix + "&f&l遊戲開始了,祝福各位&6&l好運");
		zh_tw_allLightStart = Utils.colored(prefix + "&f&l現在開始你不管在哪裡都會受到&6&l傷害!&f&l請趕快擊殺對手!");
		zh_tw_notOnline = "§c該玩家不存在或不在線!";
		zh_tw_seconds = "秒鐘";
		zh_tw_minutes = "分鐘";

		zh_tw_lobbyCount = Utils.colored(prefix + "&f即將在&a <time> &f秒後傳送玩家");
		zh_tw_startCount = Utils.colored(prefix + "&6遊戲即將在&f <time> &6後開始");
		zh_tw_gameCount = Utils.colored(prefix + "&c死亡之戰倒數&f <time>");
		zh_tw_deathMatchStartCount = Utils.colored(prefix + "&c玩家即將在 <time> 秒後釋放");
		zh_tw_allStrikeLightCount = Utils.colored(prefix + "&c&l極限淘汰模式即將在 &f&l<time> &c&l秒後開始");

		zh_tw_youGotKillByS1 = Utils.colored(prefix + "&f你被&c <player> &f以&b <heart>&c\u2764 &f差距擊殺了");
		zh_tw_youKills1 = Utils.colored(prefix + "&f你擊殺了&6 <player>!");
		zh_tw_s1Death = Utils.colored(prefix + "&c<player> &f死亡了!遊戲還剩下&6 <size> &f人存活!");

		zh_tw_pplJoin = Utils.colored(prefix + "&6<player> &f加入了 &7(<now>/&c24&7)");
		zh_tw_pplLeave = Utils.colored(prefix + "&6<player> &7離開了遊戲 &7(<now>/&c24&7)");

		/** EN **/

		en_us_kills = "kills";
		en_us_ratingAdded = Utils.colored("&6Rating +");
		en_us_ratingRemoved = Utils.colored("&cRating -");
		en_us_ratingRemoveReason = Utils.colored("&7(Rating more then &c1200&7 and died in this match)");
		en_us_outArena = Utils.colored(prefix + "&c&lYou're out of the &f&larena!! &c&lplease back to the arena center!!!");
		en_us_cantStart = Utils.colored(prefix + "&cPlayers not enough, can not start the match.");
		en_us_needDeathMatch = Utils.colored(prefix + "&c&lTheres only &f&l4 players remaining! &c&lso this match is going to finish faster! &f&l2 minutes later start &c&ldeathmatch!");
		en_us_gameStarted = Utils.colored(prefix + "&f&lGame started, &6&lgood luck&f&l.");
		en_us_allLightStart = Utils.colored(prefix + "&f&lNow you'll be &6&ldamaged&f&l by system whatever where you staying, !&f&lkill your opponents faster!");
		en_us_notOnline = "§cTarget aren't vaild or not online!";
		en_us_seconds = "second(s)";
		en_us_minutes = "minute(s)";

		en_us_lobbyCount = Utils.colored(prefix + "&fScatter in&a <time> &fsecond(s)");
		en_us_startCount = Utils.colored(prefix + "&6Starting in &f <time> &6second(s)");
		en_us_gameCount = Utils.colored(prefix + "&cDeathmatch in&f <time>");
		en_us_deathMatchStartCount = Utils.colored(prefix + "&cAll players will be unfreeze in <time> second(s)");
		en_us_allStrikeLightCount = Utils.colored(prefix + "&c&lSudden death in &f&l<time> &c&lsecond(s)");

		en_us_youGotKillByS1 = Utils.colored(prefix + "&fYou are slain by &c <player> &fwith&b <heart>&c\u2764 &fhearts gap");
		en_us_youKills1 = Utils.colored(prefix + "&fyou killed&6 <player>!");
		en_us_s1Death = Utils.colored(prefix + "&c<player> &fdied! theres only&6 <size> &fplayers remaining!");

		en_us_pplJoin = Utils.colored(prefix + "&6<player> &fjoined &7(<now>/&c24&7)");
		en_us_pplLeave = Utils.colored(prefix + "&6<player> &7quited &7(<now>/&c24&7)");

	}

	public void setupItems() {
		specTp = SurvivalGames.getItemBuilder().itemWithName(Material.SLIME_BALL, "&6隨機傳送&7(Random Teleport)");
		playAnotherGame = SurvivalGames.getItemBuilder().itemWithName(Material.PAPER, "&a&l再玩一次&7(Play Again)");
		returnToLobby = SurvivalGames.getItemBuilder().itemWithName(Material.BED, "&b回到大廳&7(Return to Lobby)");
		iVoteMap = SurvivalGames.getItemBuilder().itemWithName(Material.PAPER, Utils.colored("&6&l投票&f&l地圖&7(Vote maps)"));
		arrowTrails = SurvivalGames.getItemBuilder().itemWithName(Material.CHEST, Utils.colored("&f&l選擇&e&l箭矢特效&7(Arrow trails)"));
		RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");
		statsItem = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&b玩家資料&7(Player stats)");
	}

	public void setupList() {

		zh_tw_victoryMsg.add("§7§m--------------------------");
		zh_tw_victoryMsg.add("");
		zh_tw_victoryMsg.add("  §e§l遊戲結束");
		zh_tw_victoryMsg.add("§f恭喜 §6§l<player> §f獲得勝利");
		zh_tw_victoryMsg.add("");
		zh_tw_victoryMsg.add("§f勝利者擊殺數:§b <playerKills>");
		zh_tw_victoryMsg.add("");
		zh_tw_victoryMsg.add("§7§m--------------------------");

		en_us_victoryMsg.add("§7§m--------------------------");
		en_us_victoryMsg.add("");
		en_us_victoryMsg.add("  §e§lMatch ended");
		en_us_victoryMsg.add("§fCongratulations §6§l<player> §fwins the game!");
		en_us_victoryMsg.add("");
		en_us_victoryMsg.add("§fWinner kills:§b <playerKills>");
		en_us_victoryMsg.add("");
		en_us_victoryMsg.add("§7§m--------------------------");

		zh_tw_infoMessage.add(Utils.colored("&f&m-----------------------------"));
		zh_tw_infoMessage.add(Utils.colored("              &6&l歡迎來到MitwSG"));
		zh_tw_infoMessage.add(Utils.colored("           &f盡你所能的搶到最多箱子"));
		zh_tw_infoMessage.add(Utils.colored("&f可以的話,嘗試到隱密的地方搜尋&e高等箱"));
		zh_tw_infoMessage.add(Utils.colored("              &c&l並且隨時注意身旁"));
		zh_tw_infoMessage.add(Utils.colored("                &a&l目標獲得勝利!"));
		zh_tw_infoMessage.add(Utils.colored("&f&m-----------------------------"));

		en_us_infoMessage.add(Utils.colored("&f&m-----------------------------"));
		en_us_infoMessage.add(Utils.colored("              &6&lWelcome to MitwSG"));
		en_us_infoMessage.add(Utils.colored("           &fFind a lot chest as you can"));
		en_us_infoMessage.add(Utils.colored("  &fTry to find the hidden &eTier 2 chest"));
		en_us_infoMessage.add(Utils.colored("              &c&lBe careful of your surroundings"));
		en_us_infoMessage.add(Utils.colored("                &a&lAnd won the match!"));
		en_us_infoMessage.add(Utils.colored("&f&m-----------------------------"));
	}

}
