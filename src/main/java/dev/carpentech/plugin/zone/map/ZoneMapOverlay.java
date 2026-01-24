package dev.carpentech.plugin.zone.map;

import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zone.ZoneManager;
import dev.carpentech.plugin.zone.data.ZoneType;
import dev.carpentech.plugin.zone.data.Warp;
import dev.carpentech.plugin.zone.data.WarpRegistry;

/**
 * Map overlay system that shows zones as colored squares on the in-game map (M key)
 */
public class ZoneMapOverlay {

    /**
     * Get zone color for map display
     */
    public static int getZoneColor(ZoneType type) {
        return type.getColor();
    }

    /**
     * Check if a chunk should be highlighted on the map
     */
    public static boolean shouldHighlightChunk(World world, int chunkX, int chunkZ) {
        FastNoiseLite.Vector3 pos = new FastNoiseLite.Vector3(chunkX * 16.0, 100, chunkZ * 16.0);
        ZoneType zone = ZoneManager.getZone(world, pos);
        return zone != ZoneType.WILDERNESS;
    }

    /**
     * Get zone type for a chunk
     */
    public static ZoneType getChunkZone(World world, int chunkX, int chunkZ) {
        FastNoiseLite.Vector3 pos = new FastNoiseLite.Vector3(chunkX * 16.0, 100, chunkZ * 16.0);
        return ZoneManager.getZone(world, pos);
    }

    /**
     * Render zone overlay on map for player
     * This should be called when player opens map (M key)
     */
    public static void renderZoneOverlay(PlayerRef player, World world, int centerChunkX, int centerChunkZ, int viewRadius) {
        // Render zones as colored squares around the player's view
        for (int x = -viewRadius; x <= viewRadius; x++) {
            for (int z = -viewRadius; z <= viewRadius; z++) {
                int chunkX = centerChunkX + x;
                int chunkZ = centerChunkZ + z;
                
                ZoneType zone = getChunkZone(world, chunkX, chunkZ);
                
                if (zone != ZoneType.WILDERNESS) {
                    // Draw colored square on map
                    drawZoneSquare(player, chunkX, chunkZ, zone);
                }
            }
        }
        
        // Draw warp markers
        for (Warp warp : WarpRegistry.getAllWarps()) {
            int warpChunkX = warp.getChunkX();
            int warpChunkZ = warp.getChunkZ();
            
            if (Math.abs(warpChunkX - centerChunkX) <= viewRadius && 
                Math.abs(warpChunkZ - centerChunkZ) <= viewRadius) {
                drawWarpMarker(player, warpChunkX, warpChunkZ, warp);
            }
        }
    }

    private static void drawZoneSquare(PlayerRef player, int chunkX, int chunkZ, ZoneType zone) {
        // This would integrate with Hytale's map rendering system
        // For now, we'll use a placeholder that can be hooked into the actual map API
        try {
            // Try to find map rendering methods
            // The actual implementation depends on Hytale's map API
            // This is a placeholder structure
        } catch (Exception e) {
            // Map overlay not available
        }
    }

    private static void drawWarpMarker(PlayerRef player, int chunkX, int chunkZ, Warp warp) {
        // Draw warp icon on map
        try {
            // Integrate with map marker system
        } catch (Exception e) {
            // Map marker not available
        }
    }

    /**
     * Get zone information for display in map tooltip
     */
    public static String getZoneTooltip(ZoneType zone) {
        switch (zone) {
            case SPAWN:
                return "§a🟢 Safe Zone - Spawn";
            case PVP_BUFFER:
                return "§e🟡 PvP Buffer Zone";
            case WARP_SAFE:
                return "§b🔵 Warp Safe Zone";
            case WILDERNESS:
                return "§c🔴 Wilderness - PvP Enabled";
            default:
                return "Unknown Zone";
        }
    }
}
