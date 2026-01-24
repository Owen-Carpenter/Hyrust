package dev.carpentech.plugin.zone;

import dev.carpentech.plugin.zone.component.ZoneMapComponent;
import dev.carpentech.plugin.zone.data.ZoneType;
import dev.carpentech.plugin.zone.data.Warp;
import dev.carpentech.plugin.zone.data.WarpRegistry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ZoneMapRenderer {

    public static void renderZoneMap(ZoneMapComponent zoneMap, int centerChunkX, int centerChunkZ, int radiusChunks, String outputPath) {
        int size = radiusChunks * 2 + 1;
        int pixelSize = 10; // Each chunk is 10x10 pixels
        
        BufferedImage image = new BufferedImage(size * pixelSize, size * pixelSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Count zones for legend
        int spawnCount = 0, pvpCount = 0, warpCount = 0, wildCount = 0;
        
        // Render zones
        for (int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                int chunkX = centerChunkX - radiusChunks + x;
                int chunkZ = centerChunkZ - radiusChunks + z;
                
                ZoneType type = zoneMap.get(chunkX, chunkZ);
                Color color = new Color(type.getRed(), type.getGreen(), type.getBlue());
                
                // Count zones
                switch (type) {
                    case SPAWN: spawnCount++; break;
                    case PVP_BUFFER: pvpCount++; break;
                    case WARP_SAFE: warpCount++; break;
                    case WILDERNESS: wildCount++; break;
                }
                
                g.setColor(color);
                g.fillRect(x * pixelSize, z * pixelSize, pixelSize, pixelSize);
                
                // Draw grid (lighter for better visibility)
                g.setColor(new Color(50, 50, 50, 100));
                g.drawRect(x * pixelSize, z * pixelSize, pixelSize, pixelSize);
            }
        }
        
        // Draw warps with labels
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        for (Warp warp : WarpRegistry.getAllWarps()) {
            int wx = warp.getChunkX() - (centerChunkX - radiusChunks);
            int wz = warp.getChunkZ() - (centerChunkZ - radiusChunks);
            
            if (wx >= 0 && wx < size && wz >= 0 && wz < size) {
                int px = wx * pixelSize + pixelSize / 2;
                int pz = wz * pixelSize + pixelSize / 2;
                
                // Draw warp marker (white circle with black border)
                g.setColor(Color.BLACK);
                g.fillOval(px - 5, pz - 5, 10, 10);
                g.setColor(Color.WHITE);
                g.fillOval(px - 4, pz - 4, 8, 8);
                
                // Draw warp name
                g.setColor(Color.WHITE);
                g.drawString(warp.getName(), px + 6, pz + 3);
            }
        }
        
        // Draw legend
        g.setFont(new Font("Arial", Font.BOLD, 12));
        int legendY = 20;
        int legendX = size * pixelSize - 200;
        
        // Background for legend
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(legendX - 10, 10, 190, 100);
        
        g.setColor(Color.WHITE);
        g.drawString("ZONE MAP LEGEND", legendX, legendY);
        legendY += 15;
        
        g.setColor(new Color(ZoneType.SPAWN.getColor()));
        g.fillRect(legendX, legendY, 10, 10);
        g.setColor(Color.WHITE);
        g.drawString("SAFE (Spawn) - " + spawnCount + " chunks", legendX + 15, legendY + 8);
        legendY += 15;
        
        g.setColor(new Color(ZoneType.PVP_BUFFER.getColor()));
        g.fillRect(legendX, legendY, 10, 10);
        g.setColor(Color.WHITE);
        g.drawString("PVP BUFFER - " + pvpCount + " chunks", legendX + 15, legendY + 8);
        legendY += 15;
        
        g.setColor(new Color(ZoneType.WARP_SAFE.getColor()));
        g.fillRect(legendX, legendY, 10, 10);
        g.setColor(Color.WHITE);
        g.drawString("WARP SAFE - " + warpCount + " chunks", legendX + 15, legendY + 8);
        legendY += 15;
        
        g.setColor(new Color(ZoneType.WILDERNESS.getColor()));
        g.fillRect(legendX, legendY, 10, 10);
        g.setColor(Color.WHITE);
        g.drawString("WILDERNESS - " + wildCount + " chunks", legendX + 15, legendY + 8);
        
        g.dispose();
        
        // Save image
        try {
            File outputFile = new File(outputPath);
            outputFile.getParentFile().mkdirs();
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Zone map rendered to: " + outputPath);
            System.out.println("  SAFE: " + spawnCount + ", PVP BUFFER: " + pvpCount + ", WARP SAFE: " + warpCount + ", WILDERNESS: " + wildCount);
        } catch (IOException e) {
            System.err.println("Failed to save zone map: " + e.getMessage());
        }
    }
    
    public static void renderFullMap(ZoneMapComponent zoneMap, String outputPath) {
        renderZoneMap(zoneMap, 0, 0, 100, outputPath);
    }
}
