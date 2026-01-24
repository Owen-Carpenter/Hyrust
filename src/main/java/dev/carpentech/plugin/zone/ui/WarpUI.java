package dev.carpentech.plugin.zone.ui;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import dev.carpentech.plugin.zone.data.Warp;
import dev.carpentech.plugin.zone.data.WarpRegistry;
import dev.carpentech.plugin.zone.data.ZoneType;

import java.util.List;

/**
 * UI system for displaying warps with icons
 */
public class WarpUI {

    // Icon characters/emojis for each warp type
    private static final String SPAWN_ICON = "🏠";
    private static final String MARKET_ICON = "🏪";
    private static final String ARENA_ICON = "⚔️";
    private static final String MINE_ICON = "⛏️";
    private static final String FARM_ICON = "🌾";
    private static final String DEFAULT_ICON = "📍";

    public static String getWarpIcon(String warpName) {
        switch (warpName.toLowerCase()) {
            case "spawn": return SPAWN_ICON;
            case "market": return MARKET_ICON;
            case "arena": return ARENA_ICON;
            case "mine": return MINE_ICON;
            case "farm": return FARM_ICON;
            default: return DEFAULT_ICON;
        }
    }

    public static String getZoneTypeIcon(ZoneType type) {
        switch (type) {
            case SPAWN: return "🟢";
            case PVP_BUFFER: return "🟡";
            case WARP_SAFE: return "🔵";
            case WILDERNESS: return "🔴";
            default: return "⚪";
        }
    }

    /**
     * Show warp selection UI to player
     */
    public static void showWarpMenu(PlayerRef player) {
        List<Warp> warps = WarpRegistry.getAllWarps();
        
        Message header = Message.raw("╔════════════════════════════════╗");
        sendMessage(player, header);
        
        Message title = Message.raw("║     Warp Locations " + "📍".repeat(3) + "     ║");
        sendMessage(player, title);
        
        Message separator = Message.raw("╠════════════════════════════════╣");
        sendMessage(player, separator);
        
        for (Warp warp : warps) {
            String icon = getWarpIcon(warp.getName());
            String line = String.format("║ %s %-20s ║", icon, capitalize(warp.getName()));
            sendMessage(player, Message.raw(line));
            
            String coords = String.format("║   (%d, %d, %d)              ║", 
                warp.getX(), warp.getY(), warp.getZ());
            sendMessage(player, Message.raw(coords));
        }
        
        Message footer = Message.raw("╚════════════════════════════════╝");
        sendMessage(player, footer);
        
        Message hint = Message.raw("Use /warp <name> to teleport");
        sendMessage(player, hint);
    }

    /**
     * Show warp list in a nice format
     */
    public static void showWarpList(PlayerRef player) {
        List<Warp> warps = WarpRegistry.getAllWarps();
        
        sendMessage(player, Message.raw("§6════════════════════════════════"));
        sendMessage(player, Message.raw("§6      Available Warps"));
        sendMessage(player, Message.raw("§6════════════════════════════════"));
        
        for (Warp warp : warps) {
            String icon = getWarpIcon(warp.getName());
            String zoneIcon = getZoneTypeIcon(ZoneType.WARP_SAFE);
            String line = String.format("%s %s §7%s §8- §7(%d, %d, %d)", 
                icon, zoneIcon, capitalize(warp.getName()), 
                warp.getX(), warp.getY(), warp.getZ());
            sendMessage(player, Message.raw(line));
        }
        
        sendMessage(player, Message.raw("§6════════════════════════════════"));
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private static void sendMessage(PlayerRef player, Message message) {
        try {
            java.lang.reflect.Method sendMethod = player.getClass().getMethod("sendMessage", Message.class);
            sendMethod.invoke(player, message);
        } catch (Exception e) {
            // Fallback - try alternative methods
            try {
                java.lang.reflect.Method sendMethod = player.getClass().getMethod("sendMessage", String.class);
                sendMethod.invoke(player, message.getRawText());
            } catch (Exception e2) {
                System.err.println("Could not send message to player: " + e2.getMessage());
            }
        }
    }
}
