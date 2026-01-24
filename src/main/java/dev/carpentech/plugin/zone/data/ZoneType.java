package dev.carpentech.plugin.zone.data;

public enum ZoneType {
    SPAWN(0x00FF00),        // Green
    PVP_BUFFER(0xFFFF00),   // Yellow
    WARP_SAFE(0x00FFFF),    // Cyan
    WILDERNESS(0xFF0000);   // Red

    private final int color;

    ZoneType(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getRed() {
        return (color >> 16) & 0xFF;
    }

    public int getGreen() {
        return (color >> 8) & 0xFF;
    }

    public int getBlue() {
        return color & 0xFF;
    }
}