package dev.carpentech.plugin.zone.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.carpentech.plugin.zone.map.ZoneMapOverlay;
import dev.carpentech.plugin.zone.ui.WarpUI;

import javax.annotation.Nonnull;

/**
 * Command to show zone map with overlays
 */
public class MapCommand extends AbstractPlayerCommand {

    public MapCommand() {
        super("zones", "Show zone map with colored squares", false);
    }

    @Override
    protected void execute(
            @Nonnull CommandContext commandContext,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
    ) {
        // Get player position
        try {
            java.lang.reflect.Method getPositionMethod = playerRef.getClass().getMethod("getPosition");
            Object position = getPositionMethod.invoke(playerRef);
            
            if (position != null) {
                java.lang.reflect.Field xField = position.getClass().getField("x");
                java.lang.reflect.Field zField = position.getClass().getField("z");
                double x = ((Number)xField.get(position)).doubleValue();
                double z = ((Number)zField.get(position)).doubleValue();
                
                int chunkX = ((int)x) >> 4;
                int chunkZ = ((int)z) >> 4;
                
                // Show zone map overlay
                ZoneMapOverlay.renderZoneOverlay(playerRef, world, chunkX, chunkZ, 20);
                
                commandContext.sendMessage(Message.raw("§6Zone map displayed! Press M to view in-game map."));
                commandContext.sendMessage(Message.raw("§7Green = Safe, Yellow = Buffer, Cyan = Warp Safe, Red = Wilderness"));
            }
        } catch (Exception e) {
            commandContext.sendMessage(Message.raw("§cCould not display zone map"));
            System.err.println("Map command error: " + e.getMessage());
        }
    }
}
