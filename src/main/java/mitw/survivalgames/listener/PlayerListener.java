package mitw.survivalgames.listener;

import java.text.DecimalFormat;
import java.util.UUID;

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
import mitw.survivalgames.guis.VoteGUI;
import mitw.survivalgames.manager.ArenaManager;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.ratings.PlayerCache;
import mitw.survivalgames.utils.Utils;
import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener {

	@EventHandler
	public void onDeath(final PlayerDeathEvent e) {
		final Player p = e.getEntity();
		if (!SurvivalGames.getPlayerManager().isGameingPlayer(p))
			return;
		SurvivalGames.getPlayerManager().setSpec(p);
		p.getInventory().setHeldItemSlot(3);
		PlayerManager.players.remove(p.getUniqueId());
		p.getWorld().strikeLightningEffect(p.getLocation());
		if (p.getKiller() != null) {
			final Player k = p.getKiller();
			final DecimalFormat df = new DecimalFormat("##.0");
			final String kHeart = df.format(k.getHealth() / 2D);
			SurvivalGames.getPlayerManager().addKills(k);
			p.sendMessage(Lang.youGotKillByS1.replaceAll("<player>", k.getName()).replaceAll("<heart>", kHeart));
			k.sendMessage(Lang.youKills1.replaceAll("<player>", p.getName()));

			final int ratingAdded = SurvivalGames.getRandom().nextInt(5, 15);
			final PlayerCache killerCache = SurvivalGames.getPlayerManager().getCache(k.getUniqueId());
			killerCache.setRating(killerCache.getRating() + ratingAdded);

			k.sendMessage(Lang.ratingAdded + ratingAdded);
		}
		e.setDeathMessage(Lang.s1Death.replaceAll("<player>", p.getName()).replaceAll("<size>", String.valueOf(PlayerManager.players.size())));
		SurvivalGames.getGameManager().checkWin();
	}

	@EventHandler
	public void onDamage(final EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		final Player p = (Player) e.getEntity();
		if (!GameStatus.isGaming(true) || !SurvivalGames.getPlayerManager().isGameingPlayer(p)) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onDamageByEntity(final EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		final Player damager = (Player) e.getDamager();
		if (!GameStatus.isGaming(true) || !SurvivalGames.getPlayerManager().isGameingPlayer(damager)) {
			e.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onPickup(final PlayerPickupItemEvent e) {
		if (!GameStatus.isGaming(true) || !SurvivalGames.getPlayerManager().isGameingPlayer(e.getPlayer())) {
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onDrop(final PlayerDropItemEvent e) {
		if (!GameStatus.isGaming(true) || !SurvivalGames.getPlayerManager().isGameingPlayer(e.getPlayer())) {
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
			a = ArenaManager.editors.get(u);
			if (!ArenaManager.editors.get(u).tir2Chests.contains(l)) {
				SurvivalGames.getSgChestManager().setTir2(l, ArenaManager.editors.get(u));
				p.sendMessage(Utils.colored("&a成功新增高等箱子,目前共有 &f" + a.getTir2Chests().size() + "&a 個高等箱子"));
			} else {
				SurvivalGames.getSgChestManager().removeTir2(l, ArenaManager.editors.get(u));
				p.sendMessage(Utils.colored("&c成功移除高等箱子,目前剩下 &f" + a.getTir2Chests().size() + "&a 個高等箱子"));
			}
			return;
		}
		if (e.getPlayer().getItemInHand().getType().equals(Material.GHAST_TEAR)) {
			e.setCancelled(true);
			final Location l = e.getBlock().getLocation();
			final Player p = e.getPlayer();
			final UUID u = p.getUniqueId();
			a = ArenaManager.editors.get(u);
			if (!ArenaManager.editors.get(u).centerChests.contains(l)) {
				SurvivalGames.getSgChestManager().setCenter(l, a);
				p.sendMessage(Utils.colored("&e成功新增中心點箱子,目前共有 &f" + a.centerChests.size() + "&e 個高等箱子"));
			} else {
				SurvivalGames.getSgChestManager().removeCenterTir2(l, ArenaManager.editors.get(u));
				p.sendMessage(Utils.colored("&e成功移除中心點箱子,目前剩下 &f" + a.centerChests.size() + "&e 個高等箱子"));
			}
			return;
		}

		if ((!GameStatus.isGaming(true) || !ArenaManager.canBreak.contains(b.getType())
				|| !SurvivalGames.getPlayerManager().isGameingPlayer(e.getPlayer())) && !SurvivalGames.getPlayerManager().isBuilder(e.getPlayer())) {
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
				SurvivalGames.getPlayerManager().randomTeleportPlayer(p);
				return;
			}
			if (i.equals(Lang.returnToLobby)) {
				e.setCancelled(true);
				SurvivalGames.getGameManager().sendToLobbyServer(p);
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

		}
		if (a.equals(Action.RIGHT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_AIR))
			if (!SurvivalGames.getPlayerManager().isGameingPlayer(p) && !PlayerManager.builders.contains(p.getUniqueId())) {
				e.setCancelled(true);
				return;
			}
		if (a.equals(Action.LEFT_CLICK_BLOCK) || a.equals(Action.RIGHT_CLICK_BLOCK))
			if ((e.getClickedBlock().getType().equals(Material.FIRE)) && !GameStatus.isGaming(true)
					&& !SurvivalGames.getPlayerManager().isGameingPlayer(p)) {
				e.setCancelled(true);
				return;
			}
		if (a.equals(Action.PHYSICAL)) {
			final Material m = e.getClickedBlock().getType();
			if (m == Material.SOIL || (!SurvivalGames.getPlayerManager().isGameingPlayer(p) && ArenaManager.specCantUse.contains(m))) {
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
		if (!PlayerManager.players.contains(p.getUniqueId())) {
			e.setFoodLevel(20);
		}
	}

	@EventHandler
	public void onBucketEmpty(final PlayerBucketEmptyEvent e) {
		if (!PlayerManager.builders.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent e) {
		if (SurvivalGames.getPlayerManager().isBuilder(e.getPlayer()))
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
