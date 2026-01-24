# Zone System Documentation

## Overview

The zone system provides protected areas throughout the world with different rules for PvP, building, and interactions.

## Zone Types

| Zone Type | Color | Description |
|-----------|-------|-------------|
| **SPAWN** | Green (0x00FF00) | Main spawn area - fully protected, no PvP or building |
| **PVP_BUFFER** | Yellow (0xFFFF00) | Buffer zone around spawn - no PvP but limited building |
| **WARP_SAFE** | Cyan (0x00FFFF) | Safe zones around warp points - no PvP |
| **WILDERNESS** | Red (0xFF0000) | Normal gameplay - PvP and building allowed |

## Configuration

### ZoneConfig.java
- `SPAWN_RADIUS_CHUNKS`: 6 chunks (radius of spawn safe zone)
- `PVP_BUFFER_RADIUS`: 12 chunks (radius of PvP buffer zone)

## Warps

Warps are predefined teleport locations with safe zones around them.

### Default Warps

1. **spawn** (0, 100, 0) - 6 chunk radius
   - Main spawn point
   
2. **market** (500, 100, 500) - 3 chunk radius
   - Trading marketplace
   
3. **arena** (-500, 100, -500) - 3 chunk radius
   - PvP arena (safe zone for preparation)
   
4. **mine** (1000, 50, 0) - 3 chunk radius
   - Mining area staging
   
5. **farm** (-1000, 100, 1000) - 3 chunk radius
   - Farming area

### Adding New Warps

Edit `src/main/resources/warps.json`:

```json
{
  "warps": [
    {
      "name": "myWarp",
      "x": 100,
      "y": 64,
      "z": 200,
      "radiusChunks": 3,
      "description": "My custom warp"
    }
  ]
}
```

Or programmatically in code:

```java
WarpRegistry.registerWarp(new Warp("myWarp", 100, 64, 200, 3));
```

## Zone Map Visualization

When the plugin builds and initializes, it generates a visual map of all zones:

- **Location**: `zone-maps/world-zones.png`
- **Format**: PNG image
- **Scale**: 10x10 pixels per chunk
- **Features**:
  - Color-coded zones
  - Grid overlay
  - Warp markers with labels

### Reading the Map

- Each colored square represents one chunk (16x16 blocks)
- White circles indicate warp locations
- Grid lines show chunk boundaries

## Protection Rules

### SPAWN Zone
- ❌ No PvP (player damage disabled)
- ❌ No block breaking
- ❌ No block placing
- ✅ Safe for new players

### PVP_BUFFER Zone
- ❌ No PvP (player damage disabled)
- ⚠️ Limited building
- ✅ Transition zone

### WARP_SAFE Zone
- ❌ No PvP (player damage disabled)
- ❌ No block breaking near warp
- ❌ No block placing near warp
- ✅ Safe teleport destinations

### WILDERNESS Zone
- ✅ PvP enabled
- ✅ Full building rights
- ✅ Normal gameplay

## Commands

### /warp
Lists all available warps

### /warp <name>
Teleports to the specified warp

Example:
```
/warp market
/warp spawn
/warp arena
```

## File Structure

```
dev.carpentech.plugin.zone/
├── ZoneManager.java          - Main zone management
├── ZoneListener.java         - Event handlers for protection
├── ZoneMapRenderer.java      - Visual map generation
├── command/
│   └── WarpCommand.java      - Warp teleport command
├── component/
│   ├── ZoneMapComponent.java - ECS component for zone data
│   └── WarpZoneComponent.java - Warp metadata component
├── data/
│   ├── ZoneType.java         - Zone type enum with colors
│   ├── ZoneConfig.java       - Zone configuration constants
│   ├── Warp.java             - Warp data class
│   └── WarpRegistry.java     - Warp storage and lookup
├── system/
│   ├── ZoneInitSystem.java   - ECS initialization system
│   └── ZoneQuerySystem.java  - ECS query system
└── util/
    ├── ZoneMath.java         - Distance calculations
    └── WarpLoader.java       - Load warps from JSON

resources/
└── warps.json                - Warp configuration file
```

## How It Works

1. **Initialization**: When the world loads, `ZoneManager.initialize()` is called
2. **Zone Generation**: 
   - Creates spawn safe zone (green)
   - Creates PvP buffer zone (yellow)
   - Creates warp safe zones around each warp (cyan)
   - Everything else is wilderness (red)
3. **Map Rendering**: Generates `world-zones.png` showing all zones
4. **Protection**: `ZoneListener` monitors events and cancels actions in protected zones
5. **Commands**: Players can use `/warp` to teleport to safe locations

## Customization

### Change Zone Sizes
Edit `ZoneConfig.java`:
```java
public static final int SPAWN_RADIUS_CHUNKS = 10; // Make spawn bigger
public static final int PVP_BUFFER_RADIUS = 20;   // Extend buffer
```

### Change Zone Colors
Edit `ZoneType.java`:
```java
SPAWN(0xFF00FF),     // Change to magenta
WILDERNESS(0x800000) // Change to dark red
```

### Add Dynamic Zones
```java
// In ZoneManager.initialize()
map.set(chunkX, chunkZ, ZoneType.WARP_SAFE);
```

## Future Enhancements

- [ ] Faction-claimed territories
- [ ] Dynamic zone creation via commands
- [ ] Per-zone custom rules
- [ ] Zone boundaries with particles
- [ ] Interactive map in-game
- [ ] Zone ownership and permissions
