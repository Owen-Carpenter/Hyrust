package dev.carpentech.plugin.zone.data;

public class Warp {
    private final String name;
    private final int x;
    private final int y;
    private final int z;
    private final int radiusChunks;

    public Warp(String name, int x, int y, int z, int radiusChunks) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.radiusChunks = radiusChunks;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getRadiusChunks() {
        return radiusChunks;
    }

    public int getChunkX() {
        return x >> 4;
    }

    public int getChunkZ() {
        return z >> 4;
    }
}
