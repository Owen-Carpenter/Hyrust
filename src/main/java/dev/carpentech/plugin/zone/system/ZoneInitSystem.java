package dev.carpentech.plugin.zones.system;

import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zones.component.ZoneMapComponent;
import dev.carpentech.plugin.zones.data.ZoneConfig;
import dev.carpentech.plugin.zones.data.ZoneType;

public class ZoneInitSystem extends System {

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
                if (dist <= ZoneConfig.SPAWN_RADIUS)
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
}

