package dev.carpentech.plugin.zone;

import com.hypixel.hytale.builtin.hytalegenerator.fields.FastNoiseLite;
import com.hypixel.hytale.server.core.universe.world.World;
import dev.carpentech.plugin.zone.data.ZoneType;

/**
 * Zone protection listener - prevents block breaking/placing and PvP in protected zones.
 * This class should be registered with the event manager to receive events.
 */
public class ZoneListener {

    /**
     * Check if block placement should be cancelled based on zone
     */
    public boolean shouldCancelBlockPlace(World world, FastNoiseLite.Vector3 position) {
        ZoneType zone = ZoneManager.getZone(world, position);
        return zone == ZoneType.SPAWN || zone == ZoneType.WARP_SAFE || zone == ZoneType.PVP_BUFFER;
    }

    /**
     * Check if block breaking should be cancelled based on zone
     */
    public boolean shouldCancelBlockBreak(World world, FastNoiseLite.Vector3 position) {
        ZoneType zone = ZoneManager.getZone(world, position);
        return zone == ZoneType.SPAWN || zone == ZoneType.WARP_SAFE || zone == ZoneType.PVP_BUFFER;
    }

    /**
     * Check if PvP damage should be cancelled based on zone
     */
    public boolean shouldCancelDamage(World world, FastNoiseLite.Vector3 victimPosition) {
        ZoneType zone = ZoneManager.getZone(world, victimPosition);
        return zone == ZoneType.SPAWN || zone == ZoneType.WARP_SAFE || zone == ZoneType.PVP_BUFFER;
    }
}
