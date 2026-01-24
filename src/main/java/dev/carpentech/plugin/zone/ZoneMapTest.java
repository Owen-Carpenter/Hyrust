package dev.carpentech.plugin.zone;

import dev.carpentech.plugin.zone.component.ZoneMapComponent;
import dev.carpentech.plugin.zone.data.Warp;
import dev.carpentech.plugin.zone.data.WarpRegistry;
import dev.carpentech.plugin.zone.data.ZoneConfig;
import dev.carpentech.plugin.zone.data.ZoneType;
import dev.carpentech.plugin.zone.util.ZoneMath;

/**
 * Test utility to generate a zone map without needing the full Hytale server running.
 * Run this to preview what your zone layout will look like.
 */
public class ZoneMapTest {

    public static void main(String[] args) {
        System.out.println("=== Zone Map Generator ===");
        System.out.println();
        
        // Create zone map
        ZoneMapComponent map = new ZoneMapComponent();
        
        // Spawn position (0, 0 in chunk coordinates)
        int spawnX = 0;
        int spawnZ = 0;
        
        // Initialize zones (200 chunk radius)
        int mapRadius = 100;
        
        System.out.println("Generating zones in " + (mapRadius * 2) + "x" + (mapRadius * 2) + " chunk area...");
        
        for (int x = -mapRadius; x <= mapRadius; x++) {
            for (int z = -mapRadius; z <= mapRadius; z++) {
                int cx = spawnX + x;
                int cz = spawnZ + z;

                // Calculate distance from spawn
                int distFromSpawn = Math.max(Math.abs(x), Math.abs(z));

                // Default zone type based on distance from spawn
                ZoneType type;
                if (distFromSpawn <= ZoneConfig.SPAWN_RADIUS_CHUNKS) {
                    type = ZoneType.SPAWN;
                } else if (distFromSpawn <= ZoneConfig.PVP_BUFFER_RADIUS) {
                    type = ZoneType.PVP_BUFFER;
                } else {
                    type = ZoneType.WILDERNESS;
                }

                // Check if this chunk is within any warp's safe zone
                for (Warp warp : WarpRegistry.getAllWarps()) {
                    int warpChunkX = warp.getChunkX();
                    int warpChunkZ = warp.getChunkZ();
                    int distFromWarp = ZoneMath.chunkDistance(cx, cz, warpChunkX, warpChunkZ);
                    
                    if (distFromWarp <= warp.getRadiusChunks()) {
                        type = ZoneType.WARP_SAFE;
                        break;
                    }
                }

                map.set(cx, cz, type);
            }
        }
        
        System.out.println("Zone generation complete!");
        System.out.println();
        
        // Print warp information
        System.out.println("=== Registered Warps ===");
        for (Warp warp : WarpRegistry.getAllWarps()) {
            System.out.println(String.format("  %s: (%d, %d, %d) - %d chunk radius",
                warp.getName(), 
                warp.getX(), 
                warp.getY(), 
                warp.getZ(),
                warp.getRadiusChunks()));
        }
        System.out.println();
        
        // Print zone statistics
        System.out.println("=== Zone Configuration ===");
        System.out.println("  Spawn radius: " + ZoneConfig.SPAWN_RADIUS_CHUNKS + " chunks");
        System.out.println("  PvP buffer radius: " + ZoneConfig.PVP_BUFFER_RADIUS + " chunks");
        System.out.println();
        
        // Print zone colors
        System.out.println("=== Zone Colors ===");
        for (ZoneType type : ZoneType.values()) {
            System.out.println(String.format("  %s: RGB(%d, %d, %d) - #%06X",
                type.name(),
                type.getRed(),
                type.getGreen(),
                type.getBlue(),
                type.getColor()));
        }
        System.out.println();
        
        // Render the map
        System.out.println("Rendering zone map...");
        String outputPath = "zone-maps/test-world-zones.png";
        ZoneMapRenderer.renderFullMap(map, outputPath);
        System.out.println("Zone map saved to: " + outputPath);
        System.out.println();
        
        // Sample some zones
        System.out.println("=== Sample Zone Checks ===");
        checkZone(map, 0, 0, "Spawn center");
        checkZone(map, 5, 5, "Near spawn");
        checkZone(map, 10, 10, "PvP buffer");
        checkZone(map, 31, 31, "Market warp (500, _, 500)");
        checkZone(map, 50, 50, "Wilderness");
        
        System.out.println();
        System.out.println("=== Test Complete ===");
    }
    
    private static void checkZone(ZoneMapComponent map, int chunkX, int chunkZ, String location) {
        ZoneType type = map.get(chunkX, chunkZ);
        System.out.println(String.format("  Chunk (%d, %d) [%s]: %s",
            chunkX, chunkZ, location, type.name()));
    }
}
