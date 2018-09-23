package me.lulu.MitwSG.handler;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.lulu.MitwSG.Main;
import me.lulu.MitwSG.Status;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import top.potl.spigot.handler.MovementHandler;

public class CustomMovementHandler implements MovementHandler {

	@Override
	public void handleUpdateLocation(Player player, Location from, Location to, PacketPlayInFlying packet) {
		if (Status.isStarting() && Main.getPm().isGameingPlayer(player)) {
			player.teleport(from);
			((CraftPlayer) player).getHandle().playerConnection.checkMovement = false;
			return;
		}
	}

	@Override
	public void handleUpdateRotation(Player player, Location from, Location to, PacketPlayInFlying packet) {

	}

}
