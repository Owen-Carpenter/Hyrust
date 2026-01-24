package dev.carpentech.plugin.zone.component;

import java.util.HashMap;
import java.util.Map;
import dev.carpentech.plugin.zone.data.ZoneType;

public class ZoneMapComponent {

    private final Map<Long, ZoneType> zones = new HashMap<>();

    public void set(int cx, int cz, ZoneType type) {
        long key = (((long) cx) << 32) | (cz & 0xffffffffL);
        zones.put(key, type);
    }

    public ZoneType get(int cx, int cz) {
        long key = (((long) cx) << 32) | (cz & 0xffffffffL);
        return zones.getOrDefault(key, ZoneType.WILDERNESS);
    }
}
