package dev.carpentech.plugin.zone.system;

import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zone.component.ZoneMapComponent;
import dev.carpentech.plugin.zone.data.ZoneType;

// TODO: Add correct import for Hytale ECS GameSystem base class
// import com.hypixel.hytale.server.core.ecs.GameSystem;

public class ZoneQuerySystem { // extends GameSystem

    // TODO: Uncomment when Hytale API is available
    /*
    public ZoneType getZone(World world, FastNoiseLite.Vector3 pos) {
        ZoneMapComponent map = world.getComponent(ZoneMapComponent.class);

        int chunkX = ((int) pos.x) >> 4;
        int chunkZ = ((int) pos.z) >> 4;

        return map.get(chunkX, chunkZ);
    }
    */
}

