package dev.carpentech.plugin.zones.system;

import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zones.component.ZoneMapComponent;
import dev.carpentech.plugin.zones.data.ZoneType;

public class ZoneQuerySystem extends GameSystem {

    public ZoneType getZone(World world, FastNoiseLite.Vector3 pos) {
        ZoneMapComponent map = world.getComponent(ZoneMapComponent.class);

        int chunkX = ((int) pos.x) >> 4;
        int chunkZ = ((int) pos.z) >> 4;

        return map.get(chunkX, chunkZ);
    }
}

