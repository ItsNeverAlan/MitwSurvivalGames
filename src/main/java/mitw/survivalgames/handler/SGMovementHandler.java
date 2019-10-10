package mitw.survivalgames.handler;

import mitw.survivalgames.GameStatus;
import mitw.survivalgames.manager.GameManager;
import mitw.survivalgames.manager.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import spg.lgdev.movement.ListenType;
import spg.lgdev.movement.MovementListener;
import spg.lgdev.movement.MovementType;

public class SGMovementHandler {

    public static void register() {
        new MovementListener()
                .listenType(ListenType.EVERY_BLOCK_WITHOUT_Y)
                .type(MovementType.XYZ)
                .callback(movementValues -> {
                    final Player p = movementValues.getPlayer();
                    if (PlayerManager.isBuilder(p))
                        return;
                    if ((GameStatus.isStarting() || GameStatus.isDmStarting()) && PlayerManager.getPlayers().contains(p.getUniqueId())) {
                        Location from = movementValues.getFrom();
                        p.teleport(new Location(from.getWorld(), from.getBlockX(), from.getY(), from.getBlockZ()));
                    }
                }).start();
    }

}
