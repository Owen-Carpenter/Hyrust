package dev.carpentech.plugin;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class Factions extends JavaPlugin {
    public Factions(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    public void setup(){
        super.setup();
        this.getCommandRegistry().registerCommand(new TestCommand());
    }
}
