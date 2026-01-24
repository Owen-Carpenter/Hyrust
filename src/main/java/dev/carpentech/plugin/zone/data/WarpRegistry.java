package dev.carpentech.plugin.zone.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarpRegistry {
    private static final Map<String, Warp> warps = new HashMap<>();
    private static final List<Warp> warpList = new ArrayList<>();

    static {
        // Initialize warps with random locations
        java.util.Random random = new java.util.Random(42); // Fixed seed for consistency
        
        // Spawn at origin
        registerWarp(new Warp("spawn", 0, 100, 0, 6));
        
        // Random locations for other warps (between -2000 and 2000, avoiding spawn)
        registerWarp(new Warp("market", random.nextInt(1500) + 500, 100, random.nextInt(1500) + 500, 3));
        registerWarp(new Warp("arena", -(random.nextInt(1500) + 500), 100, -(random.nextInt(1500) + 500), 3));
        registerWarp(new Warp("mine", random.nextInt(2000) + 1000, 50, random.nextInt(2000) - 1000, 3));
        registerWarp(new Warp("farm", -(random.nextInt(2000) + 1000), 100, random.nextInt(2000) + 1000, 3));
    }

    public static void registerWarp(Warp warp) {
        warps.put(warp.getName().toLowerCase(), warp);
        warpList.add(warp);
    }

    public static Warp getWarp(String name) {
        return warps.get(name.toLowerCase());
    }

    public static List<Warp> getAllWarps() {
        return new ArrayList<>(warpList);
    }

    public static boolean hasWarp(String name) {
        return warps.containsKey(name.toLowerCase());
    }
}
