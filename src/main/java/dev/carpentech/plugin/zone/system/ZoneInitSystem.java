package dev.carpentech.plugin.zone.system;

import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zone.component.ZoneMapComponent;
import dev.carpentech.plugin.zone.data.ZoneConfig;
import dev.carpentech.plugin.zone.data.ZoneType;

// TODO: Add correct import for Hytale ECS System base class
// import com.hypixel.hytale.server.core.ecs.System;

public class ZoneInitSystem { // extends System

    // TODO: Uncomment when Hytale API is available
    /*
    @Override
    public void initialize(World world) {

        ZoneMapComponent zoneMap = new ZoneMapComponent();

        int spawnChunkX = world.getSpawnPosition().x >> 4;
        int spawnChunkZ = world.getSpawnPosition().z >> 4;

        for (int x = -ZoneConfig.PVP_BUFFER_RADIUS; x <= ZoneConfig.PVP_BUFFER_RADIUS; x++) {
            for (int z = -ZoneConfig.PVP_BUFFER_RADIUS; z <= ZoneConfig.PVP_BUFFER_RADIUS; z++) {

                int chunkX = spawnChunkX + x;
                int chunkZ = spawnChunkZ + z;

                int dist = Math.max(Math.abs(x), Math.abs(z));

                ZoneType type;
                if (dist <= ZoneConfig.SPAWN_RADIUS_CHUNKS)
                    type = ZoneType.SPAWN;
                else if (dist <= ZoneConfig.PVP_BUFFER_RADIUS)
                    type = ZoneType.PVP_BUFFER;
                else
                    type = ZoneType.WILDERNESS;

                zoneMap.set(chunkX, chunkZ, type);
            }
        }

        world.addComponent(zoneMap);
    }
    */
}

