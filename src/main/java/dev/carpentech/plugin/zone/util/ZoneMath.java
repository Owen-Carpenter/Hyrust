package dev.carpentech.plugin.zone.util;

public class ZoneMath {

    public static int chunkDistance(int x1, int z1, int x2, int z2) {
        return Math.max(
                Math.abs(x1 - x2),
                Math.abs(z1 - z2)
        );
    }
}