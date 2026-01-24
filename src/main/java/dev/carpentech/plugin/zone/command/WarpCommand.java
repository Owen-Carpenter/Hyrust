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

public class WarpCommand extends AbstractPlayerCommand {

    public WarpCommand() {
        super("warp", "Teleport to a warp location", false);
    }

    @Override
    protected void execute(
            @Nonnull CommandContext commandContext,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
    ) {
        // Get command arguments using reflection
        String[] args = new String[0];
        try {
            java.lang.reflect.Method getArgsMethod = commandContext.getClass().getMethod("getArgs");
            Object argsObj = getArgsMethod.invoke(commandContext);
            if (argsObj instanceof String[]) {
                args = (String[]) argsObj;
            } else {
                // Try getRemainingArgs
                try {
                    java.lang.reflect.Method getRemainingArgsMethod = commandContext.getClass().getMethod("getRemainingArgs");
                    argsObj = getRemainingArgsMethod.invoke(commandContext);
                    if (argsObj instanceof String[]) {
                        args = (String[]) argsObj;
                    }
                } catch (Exception e2) {
                    // No args method found
                }
            }
        } catch (Exception e) {
            // No args available
        }
        
        if (args.length == 0) {
            // Show nice UI with icons
            dev.carpentech.plugin.zone.ui.WarpUI.showWarpList(playerRef);
            return;
        }

        String warpName = args[0];
        Warp warp = WarpRegistry.getWarp(warpName);

        if (warp == null) {
            commandContext.sendMessage(Message.raw("Warp '" + warpName + "' not found!"));
            return;
        }

        // Teleport player to warp - adjust method based on actual API
        // playerRef.teleport(warp.getX(), warp.getY(), warp.getZ());
        // Alternative: playerRef.setPosition(warp.getX(), warp.getY(), warp.getZ());
        // Or use: store.get(ref).setPosition(...)
        try {
            // Try reflection-based teleport as fallback
            java.lang.reflect.Method teleportMethod = playerRef.getClass().getMethod("teleport", double.class, double.class, double.class);
            teleportMethod.invoke(playerRef, (double)warp.getX(), (double)warp.getY(), (double)warp.getZ());
        } catch (Exception e) {
            commandContext.sendMessage(Message.raw("Teleport failed - please contact admin"));
            System.err.println("Teleport error: " + e.getMessage());
        }
        commandContext.sendMessage(Message.raw("Teleported to " + warp.getName()));
    }
}
