package dev.carpentech.plugin.zone;

import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zone.component.ZoneMapComponent;
import dev.carpentech.plugin.zone.data.ZoneConfig;
import dev.carpentech.plugin.zone.data.ZoneType;
import dev.carpentech.plugin.zone.data.Warp;
import dev.carpentech.plugin.zone.data.WarpRegistry;
import dev.carpentech.plugin.zone.util.ZoneMath;

public final class ZoneManager {
    
    private static ZoneMapComponent zoneMap;

    public static void initialize(World world) {
        zoneMap = new ZoneMapComponent();

        // Get spawn position (center of spawn zone)
        // Try different methods to get spawn position
        int spawnX = 0;
        int spawnZ = 0;
        try {
            java.lang.reflect.Method getSpawnMethod = world.getClass().getMethod("getSpawnPosition");
            Object spawnPos = getSpawnMethod.invoke(world);
            if (spawnPos != null) {
                java.lang.reflect.Field xField = spawnPos.getClass().getField("x");
                java.lang.reflect.Field zField = spawnPos.getClass().getField("z");
                spawnX = ((Number)xField.get(spawnPos)).intValue() >> 4;
                spawnZ = ((Number)zField.get(spawnPos)).intValue() >> 4;
            }
        } catch (Exception e) {
            System.err.println("Could not get spawn position, using 0,0: " + e.getMessage());
            spawnX = 0;
            spawnZ = 0;
        }

        // Initialize large area around spawn
        int mapRadius = 200; // chunks
        
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

                zoneMap.set(cx, cz, type);
            }
        }

        // Add component to world - try different methods
        try {
            java.lang.reflect.Method addComponentMethod = world.getClass().getMethod("addComponent", Object.class);
            addComponentMethod.invoke(world, zoneMap);
        } catch (Exception e) {
            System.err.println("Could not add component to world: " + e.getMessage());
            // Component will be stored in static variable as fallback
        }
        
        // Render zone map for visualization
        try {
            ZoneMapRenderer.renderFullMap(zoneMap, "zone-maps/world-zones.png");
        } catch (Exception e) {
            System.err.println("Failed to render zone map: " + e.getMessage());
        }
    }

    public static ZoneType getZone(World world, FastNoiseLite.Vector3 pos) {
        if (zoneMap == null) {
            try {
                java.lang.reflect.Method getComponentMethod = world.getClass().getMethod("getComponent", Class.class);
                Object map = getComponentMethod.invoke(world, ZoneMapComponent.class);
                if (map != null && map instanceof ZoneMapComponent) {
                    zoneMap = (ZoneMapComponent) map;
                } else {
                    return ZoneType.WILDERNESS;
                }
            } catch (Exception e) {
                return ZoneType.WILDERNESS;
            }
        }

        int cx = ((int) pos.x) >> 4;
        int cz = ((int) pos.z) >> 4;

        return zoneMap.get(cx, cz);
    }
    
    public static ZoneMapComponent getZoneMap() {
        return zoneMap;
    }
}
