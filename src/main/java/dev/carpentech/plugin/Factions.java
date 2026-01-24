package dev.carpentech.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;

import dev.carpentech.plugin.zone.ZoneListener;
import dev.carpentech.plugin.zone.ZoneManager;
import dev.carpentech.plugin.zone.data.WarpRegistry;
import dev.carpentech.plugin.zone.command.WarpCommand;
import dev.carpentech.plugin.zone.command.MarketCommand;
import dev.carpentech.plugin.zone.command.SpawnCommand;
import dev.carpentech.plugin.zone.command.MapCommand;

import javax.annotation.Nonnull;

public class Factions extends JavaPlugin {

    public Factions(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup() {
        super.setup();

        // Initialize warps (loaded from warps.json and WarpRegistry defaults)
        System.out.println("Loaded " + WarpRegistry.getAllWarps().size() + " warps");
        
        // Get the main world
        World world = Universe.get().getWorld("default");

        // Initialize zones for the world (includes warp safe zones)
        ZoneManager.initialize(world);

        // Register listener for zone protection (PvP, block break, etc.)
        // TODO: Register ZoneListener with event system when API is available
        
        // Register warp commands - try to register them
        try {
            // Try to get command manager and register commands
            java.lang.reflect.Method getServerMethod = this.getClass().getMethod("getServer");
            Object server = getServerMethod.invoke(this);
            
            java.lang.reflect.Method getCommandManagerMethod = server.getClass().getMethod("getCommandManager");
            Object commandManager = getCommandManagerMethod.invoke(server);
            
            java.lang.reflect.Method registerMethod = commandManager.getClass().getMethod("register", Object.class);
            
            registerMethod.invoke(commandManager, new WarpCommand());
            registerMethod.invoke(commandManager, new MarketCommand());
            registerMethod.invoke(commandManager, new SpawnCommand());
            registerMethod.invoke(commandManager, new MapCommand());
            
            System.out.println("Registered warp commands successfully");
        } catch (Exception e) {
            System.out.println("Commands may auto-register, or registration failed: " + e.getMessage());
            // Commands extending AbstractPlayerCommand may be auto-registered
        }
        
        System.out.println("Zone system initialized with " + WarpRegistry.getAllWarps().size() + " warps");
        System.out.println("Zone map rendered to: zone-maps/world-zones.png");
    }
}
