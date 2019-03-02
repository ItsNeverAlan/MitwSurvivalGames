package mitw.survivalgames.listener;

import java.text.DecimalFormat;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.Lang;
import mitw.survivalgames.SurvivalGames;
import mitw.survivalgames.arena.Arena;
import mitw.survivalgames.guis.StatsGUI;
import mitw.survivalgames.guis.VoteGUI;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {

	@EventHandler
	public void onDeath(final PlayerDeathEvent e) {
		final Player p = e.getEntity();
		e.setDeathMessage(null);
		if (!PlayerManager.isGameingPlayer(p))
			return;
		PlayerManager.setSpec(p);
		p.getInventory().setHeldItemSlot(3);
		PlayerManager.getPlayers().remove(p.getUniqueId());
		p.getWorld().strikeLightningEffect(p.getLocation());
		if (p.getKiller() != null) {
			final Player k = p.getKiller();
			final DecimalFormat df = new DecimalFormat("##.0");
			final String kHeart = df.format(k.getHealth() / 2D);
			PlayerManager.addKills(k);
			p.sendMessage(SurvivalGames.getLanguage().translate(p, "youGotKillByS1").replaceAll("<player>", k.getName()).replaceAll("<heart>", kHeart));
			k.sendMessage(SurvivalGames.getLanguage().translate(k, "youKills1").replaceAll("<player>", p.getName()));

			final int ratingAdded = SurvivalGames.getRandom().nextInt(4, 6);
			final PlayerCache killerCache = PlayerManager.getCache(k.getUniqueId());
			killerCache.setRating(killerCache.getRating() + ratingAdded);

			k.sendMessage(SurvivalGames.getLanguage().translate(k, "ratingAdded") + ratingAdded);
		}
		final PlayerCache playerCache = PlayerManager.getCache(p.getUniqueId());
		if (playerCache.getRating() > 1200) {
			final int ratingRemove = SurvivalGames.getRandom().nextInt(5, 9);
			p.sendMessage(SurvivalGames.getLanguage().translate(p, "ratingRemoved") + ratingRemove + " " + SurvivalGames.getLanguage().translate(p, "ratingRemoveReason"));
			playerCache.setRating(playerCache.getRating() - ratingRemove);
		}
		Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(SurvivalGames.getLanguage().translate(player, "s1Death")
				.replaceAll("<player>", p.getName())
				.replaceAll("<size>", String.valueOf(PlayerManager.getPlayers().size()))));
		GameManager.checkWin();
	}

	@EventHandler
	public void onDamage(final EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		final Player p = (Player) e.getEntity();
		if (!GameStatus.isGaming(true) || !PlayerManager.isGameingPlayer(p)) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onDamageByEntity(final EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		final Player damager = (Player) e.getDamager();
		if (!GameStatus.isGaming(true) || !PlayerManager.isGameingPlayer(damager)) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPickup(final PlayerPickupItemEvent e) {
		if (!GameStatus.isGaming(true) || !PlayerManager.isGameingPlayer(e.getPlayer())) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onDrop(final PlayerDropItemEvent e) {
		if (!GameStatus.isGaming(true) || !PlayerManager.isGameingPlayer(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(final BlockBreakEvent e) {
		final Block b = e.getBlock();
		Arena a;
		if (e.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)) {
			e.setCancelled(true);
			final Location l = e.getBlock().getLocation();
			final Player p = e.getPlayer();
			final UUID u = p.getUniqueId();
			a = ArenaManager.getEditors().get(u);
			if (!a.getTir2Chests().contains(l)) {
				SurvivalGames.getSgChestManager().setTir2(l, a);
				p.sendMessage(Utils.colored("&a成功新增高等箱子,目前共有 &f" + a.getTir2Chests().size() + "&a 個高等箱子"));
			} else {
				SurvivalGames.getSgChestManager().removeTir2(l, a);
				p.sendMessage(Utils.colored("&c成功移除高等箱子,目前剩下 &f" + a.getTir2Chests().size() + "&a 個高等箱子"));
			}
			return;
		}
		if (e.getPlayer().getItemInHand().getType().equals(Material.GHAST_TEAR)) {
			e.setCancelled(true);
			final Location l = e.getBlock().getLocation();
			final Player p = e.getPlayer();
			final UUID u = p.getUniqueId();
			a = ArenaManager.getEditors().get(u);
			if (!a.getCenterChests().contains(l)) {
				SurvivalGames.getSgChestManager().setCenter(l, a);
				p.sendMessage(Utils.colored("&e成功新增中心點箱子,目前共有 &f" + a.getCenterChests().size() + "&e 個高等箱子"));
			} else {
				SurvivalGames.getSgChestManager().removeCenterTir2(l, a);
				p.sendMessage(Utils.colored("&e成功移除中心點箱子,目前剩下 &f" + a.getCenterChests().size() + "&e 個高等箱子"));
			}
			return;
		}

		if ((!GameStatus.isGaming(true) || !ArenaManager.getCanBreak().contains(b.getType())
				|| !PlayerManager.isGameingPlayer(e.getPlayer())) && !PlayerManager.isBuilder(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(final PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		final Action a = e.getAction();
		if (e.getItem() != null) {
			final ItemStack i = e.getItem();
			if (i.equals(Lang.specTp)) {
				e.setCancelled(true);
				PlayerManager.randomTeleportPlayer(p);
				return;
			}
			if (i.equals(Lang.returnToLobby)) {
				e.setCancelled(true);
				GameManager.sendToLobbyServer(p);
				return;
			}
			if (i.equals(Lang.arrowTrails)) {
				e.setCancelled(true);
				p.chat("/arrowtrails");
				return;
			}
			if (i.equals(Lang.playAnotherGame)) {
				e.setCancelled(true);
				p.chat("/chooseServer");
				return;
			}
			if (i.equals(Lang.iVoteMap)) {
				new VoteGUI().o(p);
				return;
			}
			if (i.equals(Lang.statsItem)) {
				new StatsGUI(p, PlayerManager.getCache(p.getUniqueId())).o(p);
				return;
			}

		}
		if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR))
			if (!PlayerManager.isGameingPlayer(p) && !PlayerManager.getBuilders().contains(p.getUniqueId())) {
				e.setCancelled(true);
				return;
			}
		if (a.equals(Action.LEFT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_BLOCK))
			if ((e.getClickedBlock().getType().equals(Material.FIRE)) && !GameStatus.isGaming(true)
					&& !PlayerManager.isGameingPlayer(p)) {
				e.setCancelled(true);
				return;
			}
		if (a.equals(Action.PHYSICAL)) {
			final Material m = e.getClickedBlock().getType();
			if (m == Material.SOIL || (!PlayerManager.isGameingPlayer(p) && ArenaManager.getSpecCantUse().contains(m))) {
				e.setCancelled(true);
			}
			return;
		}
	}

	@EventHandler
	public void onSleep(final PlayerBedEnterEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onHungerLose(final FoodLevelChangeEvent e) {
		final Player p = (Player) e.getEntity();
		if (PlayerManager.getBuilders().contains(p.getUniqueId()) || !GameStatus.isGaming(true) || !PlayerManager.isGameingPlayer(p)) {
			e.setFoodLevel(20);
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBucketEmpty(final PlayerBucketEmptyEvent e) {
		if (!PlayerManager.getBuilders().contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent e) {
		if (PlayerManager.isBuilder(e.getPlayer()))
			return;
		if (e.getBlock().getType().equals(Material.FIRE)) {
			final Player p = e.getPlayer();
			final ItemStack i = p.getItemInHand();
			i.setDurability((short) (i.getDurability() + (i.getType().getMaxDurability() / 4)));
			if (i.getDurability() >= i.getType().getMaxDurability()) {
				p.getInventory().remove(i);
				p.playSound(p.getLocation(), Sound.ITEM_BREAK, 1, 1);
			}
			return;
		}
		e.setCancelled(true);
	}

	@EventHandler
	public void onPing(final ServerListPingEvent e) {
		switch (GameStatus.getState()) {
		case WAITING:
			e.setMotd(ChatColor.GREEN + "等待中!馬上加入!");
			break;
		case STARRTING:
			e.setMotd(ChatColor.RED + "傳送玩家中....");
			break;
		case GAMING:
			e.setMotd(ChatColor.RED + "遊戲已經開始");
			break;
		case FINISH:
			e.setMotd(ChatColor.BLUE + "遊戲結束了,準備重置");
			break;
		case DEATHMATCH:
			e.setMotd(ChatColor.RED + "遊戲已經開始");
			break;
		default:
			break;
		}
	}

}
