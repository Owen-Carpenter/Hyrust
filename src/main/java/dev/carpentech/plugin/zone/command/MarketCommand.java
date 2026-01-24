package dev.carpentech.plugin.zone.command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import dev.carpentech.plugin.zone.data.Warp;
import dev.carpentech.plugin.zone.data.WarpRegistry;

import javax.annotation.Nonnull;

public class MarketCommand extends AbstractPlayerCommand {

    public MarketCommand() {
        super("market", "Teleport to market", false);
    }

    @Override
    protected void execute(
            @Nonnull CommandContext commandContext,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
    ) {
        Warp warp = WarpRegistry.getWarp("market");
        if (warp != null) {
            try {
                java.lang.reflect.Method teleportMethod = playerRef.getClass().getMethod("teleport", double.class, double.class, double.class);
                teleportMethod.invoke(playerRef, (double)warp.getX(), (double)warp.getY(), (double)warp.getZ());
            } catch (Exception e) {
                commandContext.sendMessage(Message.raw("Teleport failed"));
            }
            commandContext.sendMessage(Message.raw("Teleported to market"));
        } else {
            commandContext.sendMessage(Message.raw("Market warp not found!"));
        }
    }
}
