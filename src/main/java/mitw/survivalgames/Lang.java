package mitw.survivalgames;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.utils.Utils;

public class Lang {
	public static Lang ins;
	public static String prefix = Utils.colored("&7[&6&lMitw&f&lSG&7]&f ");
	public static String Title = "&6&lMitw &7| &fSG";
	public static String deathMatchTitle = "&c&lThe Final";
	public static List<String> watingList = new ArrayList<>();
	public static List<String> startingList = new ArrayList<>();
	public static List<String> gamingList = new ArrayList<>();
	public static List<String> finishList = new ArrayList<>();
	public static List<String> dmStartList = new ArrayList<>();
	public static List<String> dmList = new ArrayList<>();
	public static ItemStack specTp, playAnotherGame, returnToLobby, iVoteMap, RandomMap, arrowTrails;
	public static String outArena;
	public static String cantStart;
	public static String needDeathMatch;
	public static String lobbyCount, startCount, gameCount, deathMatchStartCount, allStrikeLightCount;
	public static String youKills1, youGotKillByS1, s1Death;
	public static String pplJoin, pplLeave, ratingAdded;
	public static String gameStarted, allLightStart;
	public static String serverName,bungeeBroadCastCmd;
	public static List<String> victoryMsg = new ArrayList<>();
	public static List<String> infoMessage = new ArrayList<>();

	public Lang() {
		ins = this;
		setupList();
		setupString();
		setupItems();
	}

	public void setupString() {
		ratingAdded = Utils.colored("&6積分 +");
		outArena = Utils.colored(prefix + "&c&l你已經超出&f&l場地限制範圍!!&c&l請趕快回場地!!!");
		cantStart = Utils.colored(prefix + "&c遊戲人數不足造成無法開始");
		needDeathMatch = Utils.colored(prefix + "&c&l由於人數剩下&f&l4人!&c&l因此加快遊戲速度!&f&l2分鐘後便要&c&l死亡之戰!");
		gameStarted = Utils.colored(prefix + "&f&l遊戲開始了,祝福各位&6&l好運");
		allLightStart = Utils.colored(prefix + "&f&l現在開始你不管在哪裡都會受到&6&l傷害!&f&l請趕快擊殺對手!");

		lobbyCount = Utils.colored(prefix + "&f即將在&a <time> &f秒後傳送玩家");
		startCount = Utils.colored(prefix + "&6遊戲即將在&f <time> &6後開始");
		gameCount = Utils.colored(prefix + "&c死亡之戰倒數&f <time>");
		deathMatchStartCount = Utils.colored(prefix + "&c玩家即將在 <time> 秒後釋放");
		allStrikeLightCount = Utils.colored(prefix + "&c&l極限淘汰模式即將在 &f&l<time> &c&l秒後開始");

		youGotKillByS1 = Utils.colored(prefix + "&f你被&c <player> &f以&b <heart>&c\u2764 &f差距擊殺了");
		youKills1 = Utils.colored(prefix + "&f你擊殺了&6 <player>!");
		s1Death = Utils.colored(prefix + "&c<player> &f死亡了!遊戲還剩下&6 <size> &f人存活!");

		pplJoin = Utils.colored(prefix + "&6<player> &f加入了&6Mitw&eSG &7(<now>/&c24&7)");
		pplLeave = Utils.colored(prefix + "&6<player> &7離開了遊戲 &7(<now>/&c24&7)");

	}

	public void setupItems() {
		specTp = SurvivalGames.getItemBuilder().itemWithName(Material.SLIME_BALL, "&6隨機傳送&7(Random Teleport)");
		playAnotherGame = SurvivalGames.getItemBuilder().itemWithName(Material.PAPER, "&a&l再玩一次&7(Play Again)");
		returnToLobby = SurvivalGames.getItemBuilder().itemWithName(Material.BED, "&b回到大廳&7(Return to Lobby)");
		iVoteMap = SurvivalGames.getItemBuilder().itemWithName(Material.PAPER, Utils.colored("&6&l投票&f&l地圖"));
		arrowTrails = SurvivalGames.getItemBuilder().itemWithName(Material.CHEST, Utils.colored("&f&l選擇&e&l箭矢特效"));
		RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e隨緣&7(Random Map)");

	}

	public void setupList() {
		watingList.add("&7&m---------------------------");
		watingList.add("&f人數: &6<players>");
		watingList.add("&f投票即將在 &6<starting>&f 秒後中止!");
		watingList.add("");
		watingList.add("&f伺服器分流: &6<server>");
		watingList.add("");
		watingList.add("&6&lMitw.Rip");
		watingList.add("&7&m---------------------------");

		startingList.add("&7&m---------------------------");
		startingList.add("&f人數: &6<players>");
		startingList.add("&f遊戲將在 &6<starting>&f 秒後開始!");
		startingList.add("");
		startingList.add("&f伺服器分流: &6<server>");
		startingList.add("");
		startingList.add("&6&lMitw.Rip");
		startingList.add("&7&m---------------------------");

		gamingList.add("&7&m---------------------------");
		gamingList.add("&f時間剩下:&6 <time>");
		gamingList.add("&f地圖:&6 <arena>");
		gamingList.add("&f人數:&6 <players>/<max>");
		gamingList.add("&f擊殺數:&6 <kills>");
		gamingList.add("");
		gamingList.add("&f伺服器分流: &6<server>");
		gamingList.add("");
		gamingList.add("&6&lMitw.Rip");
		gamingList.add("&7&m---------------------------");

		finishList.add("&7&m---------------------------");
		finishList.add("&a&l遊戲結束!");
		finishList.add("&6&l恭喜 <player> 獲得勝利");
		finishList.add(" ");
		finishList.add("&6&lMitw.Rip");
		finishList.add("&7&m---------------------------");

		victoryMsg.add("§7§m--------------------------");
		victoryMsg.add("");
		victoryMsg.add("  §e§l遊戲結束");
		victoryMsg.add("§f恭喜 §6§l<player> §f獲得勝利");
		victoryMsg.add("");
		victoryMsg.add("§f勝利者擊殺數:§b <playerKills>");
		victoryMsg.add("");
		victoryMsg.add("§7§m--------------------------");

		dmStartList.add("&7&m---------------------------");
		dmStartList.add("&f存活玩家: &c<players>");
		dmStartList.add("&f死亡之戰將在 &c<starting>&f 秒後開始!");
		dmStartList.add("");
		dmStartList.add("&f伺服器分流: &c<server>");
		dmStartList.add("");
		dmStartList.add("&6&lMitw.Rip");
		dmStartList.add("&7&m---------------------------");

		dmList.add("&7&m---------------------------");
		dmList.add("&f極限淘汰:&c <time>");
		dmList.add("&f地圖:&c <arena>");
		dmList.add("&f存活玩家:&c <players>/<max>");
		dmList.add("&f擊殺數:&c <kills>");
		dmList.add("");
		dmList.add("&f伺服器分流: &c<server>");
		dmList.add("");
		dmList.add("&6&lMitw.Rip");
		dmList.add("&7&m---------------------------");

		infoMessage.add(Utils.colored("&f&m-----------------------------"));
		infoMessage.add(Utils.colored("              &6&l歡迎來到MitwSG"));
		infoMessage.add(Utils.colored("           &f盡你所能的搶到最多箱子"));
		infoMessage.add(Utils.colored("&f可以的話,嘗試到隱密的地方搜尋&e高等箱"));
		infoMessage.add(Utils.colored("              &c&l並且隨時注意身旁"));
		infoMessage.add(Utils.colored("                &a&l目標獲得勝利!"));
		infoMessage.add(Utils.colored("&f&m-----------------------------"));
	}

}
