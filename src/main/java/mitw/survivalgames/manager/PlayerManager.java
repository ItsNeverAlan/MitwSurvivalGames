package mitw.survivalgames.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;

public class PlayerManager {
	public static PlayerManager ins;
	public static int max = 0;
	public static Location spawnLocation;
	public static ArrayList<UUID> players = new ArrayList<>();
	public static ArrayList<UUID> builders = new ArrayList<>();
	public static HashMap<UUID, String> uuidDB = new HashMap<>();
	public static HashMap<Location, Player> spawns = new HashMap<>();
	public static HashMap<UUID, Integer> kills = new HashMap<>();
	public ArrayList<UUID> inCooldown = new ArrayList<>();

	public PlayerManager() {
		ins = this;
	}

	public void setSpec(Player p) {
		hidePlayer(p);
		p.setHealth(20.0);
		p.setGameMode(GameMode.SURVIVAL);
		clearInventory(p);
		p.setAllowFlight(true);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
		giveSpecItem(p);
	}

	public void tpToSpawn(Player p) {
		if (spawnLocation != null)
			p.teleport(spawnLocation);
	}

	public void clearInventory(Player p) {
		final PlayerInventory i = p.getInventory();
		p.setHealth(20.0);
		p.setFoodLevel(20);
		i.clear();
		i.setArmorContents(null);
		for (final PotionEffect potion : p.getActivePotionEffects()) {
			p.removePotionEffect(potion.getType());
		}

	}

	public void hidePlayer(Player p) {
		for (final Player p2 : Bukkit.getOnlinePlayers()) {
			p2.hidePlayer(p);
		}
	}

	public void giveWaitingItem(Player p) {
		final PlayerInventory i = p.getInventory();
		i.setItem(0, Lang.iVoteMap);
		i.setItem(1, Lang.arrowTrails);
		i.setItem(8, Lang.returnToLobby);
	}

	private void giveSpecItem(Player p) {
		Bukkit.getScheduler().runTaskLater(SurvivalGames.getInstance(), new Runnable() {
			PlayerInventory inv = p.getInventory();

			@Override
			public void run() {
				inv.setItem(0, Lang.specTp);
				inv.setItem(7, Lang.playAnotherGame);
				inv.setItem(8, Lang.returnToLobby);
			}
		}, 2L);
	}

	public void randomTeleportPlayer(Player p) {
		final UUID u = p.getUniqueId();
		if (inCooldown.contains(u))
			return;
		p.teleport(Bukkit.getPlayer(players.get(SurvivalGames.getRandom().nextInt(players.size()))));
		inCooldown.add(u);
		Bukkit.getScheduler().runTaskLater(SurvivalGames.getInstance(), () -> inCooldown.remove(u), 20);
	}

	public String getNameByUUID(UUID u) {
		return uuidDB.get(u);
	}

	public void putUuidDb(Player p) {
		uuidDB.put(p.getUniqueId(), p.getName());
	}

	public boolean isBuilder(Player p) {
		if (builders.contains(p.getUniqueId()))
			return true;
		return false;
	}

	public boolean isGameingPlayer(Player p) {
		if (players.contains(p.getUniqueId()))
			return true;
		return false;
	}

	public void giveSetChestItem(Player p, String name) {
		final ItemStack i = new ItemStack(Material.BLAZE_ROD);
		final ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		i.setItemMeta(m);
		p.getInventory().addItem(i);

	}

	public void addKills(Player p) {
		final UUID u = p.getUniqueId();
		kills.put(u, kills.get(u) + 1);
	}

	public int getKills(Player p) {
		if (kills.get(p.getUniqueId()) != null)
			return kills.get(p.getUniqueId());
		return 0;
	}

}
