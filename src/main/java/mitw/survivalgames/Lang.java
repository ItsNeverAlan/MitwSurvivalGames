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

	public String zh_tw_statsWins = "�ӧQ����";
	public String zh_tw_statsKills = "��������";
	public String zh_tw_statsDeaths = "���`����";
	public String zh_tw_statsRating = "�n��";
	public String zh_tw_statsRankTop = "�n���ƦW";
	public String zh_tw_statsLeaderboard = "�Ʀ�]";
	public String zh_tw_statsTitle = "&6&l���a&f&l���";
	public String zh_tw_lbTitle = "&b&l�Ʀ�&f&l�]";

	public List<String> zh_tw_watingList = Arrays.asList(
			"&7&m---------------------------",
			"&6�H��: &f<players>",
			"&6�벼����: &f<starting>��",
			"&6���A�����y: &f<server>",
			"",
			"&b�n��:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_startingList = Arrays.asList(
			"&7&m---------------------------",
			"&6�H��: &f<players>",
			"&6�C���}�l: &f<starting>��",
			"&6���A�����y: &f<server>",
			"",
			"&b�n��:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_gamingList = Arrays.asList(
			"&7&m---------------------------",
			"&6�ɶ�:&f <time>",
			"&6�a��:&f <arena>",
			"&6�H��:&f <players>/<max>",
			"",
			"&6������:&f <kills>",
			"&b�n��:&f <rating>",
			"",
			"&6���A�����y: &f<server>",
			"&7&m---------------------------");
	public List<String> zh_tw_finishList = Arrays.asList(
			"&7&m---------------------------",
			"&6�ɶ�:&f <time>",
			"&e�ӧQ��:&f <player>",
			"&6������:&f <playerKills>",
			"",
			"&b�n��:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_dmStartList = Arrays.asList(
			"&7&m---------------------------",
			"&c�H��: &f<players>",
			"&c���`���Զ}�l: &f<starting>��",
			"&c���A�����y: &f<server>",
			"",
			"&b�n��:&f <rating>",
			"&7&m---------------------------");
	public List<String> zh_tw_dmList = Arrays.asList(
			"&7&m---------------------------",
			"&c�ɶ�:&f <time>",
			"&c�a��:&f <arena>",
			"&c�H��:&f <players>/<max>",
			"",
			"&c������:&f <kills>",
			"&b�n��:&f <rating>",
			"",
			"&c���A�����y: &f<server>",
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
		zh_tw_kills = "����";
		zh_tw_ratingAdded = Utils.colored("&6�n�� +");
		zh_tw_ratingRemoved = Utils.colored("&c�n�� -");
		zh_tw_ratingRemoveReason = Utils.colored("&7(�즳�n������&c1200&7 �åB�b�C�������`)");
		zh_tw_outArena = Utils.colored(prefix + "&c&l�A�w�g�W�X&f&l���a����d��!!&c&l�л��֦^���a!!!");
		zh_tw_cantStart = Utils.colored(prefix + "&c�C���H�Ƥ����y���L�k�}�l");
		zh_tw_needDeathMatch = Utils.colored(prefix + "&c&l�ѩ�H�ƳѤU&f&l4�H!&c&l�]���[�ֹC���t��!&f&l2������K�n&c&l���`����!");
		zh_tw_gameStarted = Utils.colored(prefix + "&f&l�C���}�l�F,���֦U��&6&l�n�B");
		zh_tw_allLightStart = Utils.colored(prefix + "&f&l�{�b�}�l�A���ަb���̳��|����&6&l�ˮ`!&f&l�л����������!");
		zh_tw_notOnline = "��c�Ӫ��a���s�b�Τ��b�u!";
		zh_tw_seconds = "����";
		zh_tw_minutes = "����";

		zh_tw_lobbyCount = Utils.colored(prefix + "&f�Y�N�b&a <time> &f���ǰe���a");
		zh_tw_startCount = Utils.colored(prefix + "&6�C���Y�N�b&f <time> &6��}�l");
		zh_tw_gameCount = Utils.colored(prefix + "&c���`���ԭ˼�&f <time>");
		zh_tw_deathMatchStartCount = Utils.colored(prefix + "&c���a�Y�N�b <time> �������");
		zh_tw_allStrikeLightCount = Utils.colored(prefix + "&c&l�����^�O�Ҧ��Y�N�b &f&l<time> &c&l���}�l");

		zh_tw_youGotKillByS1 = Utils.colored(prefix + "&f�A�Q&c <player> &f�H&b <heart>&c\u2764 &f�t�Z�����F");
		zh_tw_youKills1 = Utils.colored(prefix + "&f�A�����F&6 <player>!");
		zh_tw_s1Death = Utils.colored(prefix + "&c<player> &f���`�F!�C���ٳѤU&6 <size> &f�H�s��!");

		zh_tw_pplJoin = Utils.colored(prefix + "&6<player> &f�[�J�F &7(<now>/&c24&7)");
		zh_tw_pplLeave = Utils.colored(prefix + "&6<player> &7���}�F�C�� &7(<now>/&c24&7)");

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
		en_us_notOnline = "��cTarget aren't vaild or not online!";
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
		specTp = SurvivalGames.getItemBuilder().itemWithName(Material.SLIME_BALL, "&6�H���ǰe&7(Random Teleport)");
		playAnotherGame = SurvivalGames.getItemBuilder().itemWithName(Material.PAPER, "&a&l�A���@��&7(Play Again)");
		returnToLobby = SurvivalGames.getItemBuilder().itemWithName(Material.BED, "&b�^��j�U&7(Return to Lobby)");
		iVoteMap = SurvivalGames.getItemBuilder().itemWithName(Material.PAPER, Utils.colored("&6&l�벼&f&l�a��&7(Vote maps)"));
		arrowTrails = SurvivalGames.getItemBuilder().itemWithName(Material.CHEST, Utils.colored("&f&l���&e&l�b�گS��&7(Arrow trails)"));
		RandomMap = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&e�H�t&7(Random Map)");
		statsItem = SurvivalGames.getItemBuilder().createRandomMap(Material.SIGN, "&b���a���&7(Player stats)");
	}

	public void setupList() {

		zh_tw_victoryMsg.add("��7��m--------------------------");
		zh_tw_victoryMsg.add("");
		zh_tw_victoryMsg.add("  ��e��l�C������");
		zh_tw_victoryMsg.add("��f���� ��6��l<player> ��f��o�ӧQ");
		zh_tw_victoryMsg.add("");
		zh_tw_victoryMsg.add("��f�ӧQ��������:��b <playerKills>");
		zh_tw_victoryMsg.add("");
		zh_tw_victoryMsg.add("��7��m--------------------------");

		en_us_victoryMsg.add("��7��m--------------------------");
		en_us_victoryMsg.add("");
		en_us_victoryMsg.add("  ��e��lMatch ended");
		en_us_victoryMsg.add("��fCongratulations ��6��l<player> ��fwins the game!");
		en_us_victoryMsg.add("");
		en_us_victoryMsg.add("��fWinner kills:��b <playerKills>");
		en_us_victoryMsg.add("");
		en_us_victoryMsg.add("��7��m--------------------------");

		zh_tw_infoMessage.add(Utils.colored("&f&m-----------------------------"));
		zh_tw_infoMessage.add(Utils.colored("              &6&l�w��Ө�MitwSG"));
		zh_tw_infoMessage.add(Utils.colored("           &f�ɧA�ү઺�m��̦h�c�l"));
		zh_tw_infoMessage.add(Utils.colored("&f�i�H����,���ը����K���a��j�M&e�����c"));
		zh_tw_infoMessage.add(Utils.colored("              &c&l�åB�H�ɪ`�N����"));
		zh_tw_infoMessage.add(Utils.colored("                &a&l�ؼ���o�ӧQ!"));
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
