package dev.carpentech.plugin.zones.component;

import dev.carpentech.plugin.zones.data.ZoneType;

import java.util.HashMap;
import java.util.Map;

public class ZoneMapComponent {

    // Key = chunkX << 32 | chunkZ
    public final Map<Long, ZoneType> zones = new HashMap<>();

    public void set(int chunkX, int chunkZ, ZoneType type) {
        long key = (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
        zones.put(key, type);
    }

    public ZoneType get(int chunkX, int chunkZ) {
        long key = (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
        return zones.getOrDefault(key, ZoneType.WILDERNESS);
    }
}
