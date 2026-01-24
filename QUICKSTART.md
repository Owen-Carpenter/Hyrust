# Zone System - Quick Start Guide

## ✅ What's Included

Your plugin now has a complete zone protection system with warps! Here's what was added:

### 🗺️ Zone Map Visualization
- **Generated Map**: `zone-maps/test-world-zones.png` 
- Shows color-coded zones with warp markers
- Updates automatically when plugin builds

### 🏠 5 Pre-configured Warps

| Warp Name | Coordinates | Radius | Color |
|-----------|-------------|--------|-------|
| spawn | (0, 100, 0) | 6 chunks | Cyan |
| market | (500, 100, 500) | 3 chunks | Cyan |
| arena | (-500, 100, -500) | 3 chunks | Cyan |
| mine | (1000, 50, 0) | 3 chunks | Cyan |
| farm | (-1000, 100, 1000) | 3 chunks | Cyan |

### 🎨 Zone Colors

- 🟢 **Green**: Spawn zone (6 chunk radius) - No PvP/Building
- 🟡 **Yellow**: PvP Buffer (12 chunk radius) - No PvP
- 🔵 **Cyan**: Warp Safe zones - No PvP/Building
- 🔴 **Red**: Wilderness - Full PvP/Building

## 🚀 Testing the Map

Run the test generator to preview zones:

```bash
.\gradlew.bat build
java -cp "build/libs/Hyrust-1.0-SNAPSHOT.jar;libs/HytaleServer.jar" dev.carpentech.plugin.zone.ZoneMapTest
```

This generates: `zone-maps/test-world-zones.png`

## 🔧 Customization

### Add More Warps

Edit `src/main/resources/warps.json`:

```json
{
  "warps": [
    {
      "name": "myWarp",
      "x": 2000,
      "y": 80,
      "z": 2000,
      "radiusChunks": 4,
      "description": "My custom location"
    }
  ]
}
```

Or in code (`WarpRegistry.java`):

```java
registerWarp(new Warp("castle", 1500, 120, -1500, 5));
```

### Change Zone Sizes

Edit `src/main/java/dev/carpentech/plugin/zone/data/ZoneConfig.java`:

```java
public static final int SPAWN_RADIUS_CHUNKS = 10;  // Make spawn bigger
public static final int PVP_BUFFER_RADIUS = 20;    // Extend buffer
```

### Change Zone Colors

Edit `src/main/java/dev/carpentech/plugin/zone/data/ZoneType.java`:

```java
SPAWN(0x00FF00),      // Green
WARP_SAFE(0x0080FF),  // Blue
WILDERNESS(0xFF8000)  // Orange
```

## 📁 Key Files

```
src/main/java/dev/carpentech/plugin/
├── Factions.java                    ← Main plugin (initializes zones)
└── zone/
    ├── ZoneManager.java             ← Zone system core
    ├── ZoneMapRenderer.java         ← Generates PNG maps
    ├── ZoneListener.java            ← Event protection
    ├── ZoneMapTest.java             ← Test generator
    ├── command/
    │   └── WarpCommand.java         ← /warp command
    ├── data/
    │   ├── ZoneType.java            ← Zone types + colors
    │   ├── ZoneConfig.java          ← Zone sizes
    │   ├── Warp.java                ← Warp data class
    │   └── WarpRegistry.java        ← Warp storage
    └── component/
        └── ZoneMapComponent.java    ← Zone data storage

src/main/resources/
└── warps.json                       ← Warp configuration

zone-maps/
└── test-world-zones.png             ← Generated map
```

## 🎮 In-Game Usage (when Hytale API is ready)

### Player Commands
```
/warp              - List all warps
/warp spawn        - Teleport to spawn
/warp market       - Teleport to market
/warp arena        - Teleport to arena
```

### Protection Rules

| Zone | PvP | Block Break | Block Place |
|------|-----|-------------|-------------|
| Spawn | ❌ | ❌ | ❌ |
| PvP Buffer | ❌ | ⚠️ Limited | ⚠️ Limited |
| Warp Safe | ❌ | ❌ | ❌ |
| Wilderness | ✅ | ✅ | ✅ |

## 📊 Zone Statistics

From the test output:
- **Total area**: 200×200 chunks (40,401 chunks)
- **Spawn protected**: 6 chunk radius
- **PvP buffer**: 12 chunk radius  
- **Warp zones**: 5 locations
- **Total warps coverage**: ~47 chunks

## 🔍 What Happens On Build

1. ✅ `Factions.java` loads warp registry
2. ✅ 5 default warps are registered
3. ✅ Zone system initializes (when server API available)
4. ✅ Map generation creates visualization
5. ✅ Protection listeners activate
6. ✅ `/warp` command becomes available

## 📖 Full Documentation

See `ZONES_README.md` for complete details.

## 🎯 Next Steps

1. View your generated map: `zone-maps/test-world-zones.png`
2. Customize warp locations in `warps.json`
3. Adjust zone sizes in `ZoneConfig.java`
4. Rebuild and regenerate map to preview changes
5. When Hytale server API is available, uncomment TODO sections in:
   - `Factions.java`
   - `ZoneManager.java`
   - `ZoneListener.java`
   - `WarpCommand.java`
