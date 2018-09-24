package mitw.survivalgames.handler;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.manager.PlayerManager;
import mitw.survivalgames.utils.Utils;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import top.potl.spigot.handler.MovementHandler;

public class CustomMovementHandler implements MovementHandler {

	@Override
	public void handleUpdateLocation(final Player player, final Location from, final Location to, final PacketPlayInFlying packet) {
		if (to.getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY()
				&& to.getBlockZ() == from.getBlockZ())
			return;
		if ((GameStatus.isStarting() || GameStatus.isDmStarting()) && PlayerManager.players.contains(player.getUniqueId())) {

			final double x = Math.round(from.getX()) + 0.5D;
			final double z = Math.round(from.getX()) + 0.5D;

			Utils.teleport(player, new Location(from.getWorld(), x, from.getBlockY(), z));
			return;
		}
	}

	@Override
	public void handleUpdateRotation(final Player player, final Location from, final Location to, final PacketPlayInFlying packet) {}

}
