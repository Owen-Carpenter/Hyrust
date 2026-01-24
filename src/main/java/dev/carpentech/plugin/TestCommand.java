package dev.carpentech.plugin;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class TestCommand extends AbstractPlayerCommand {

    private static final Random RANDOM = new Random();

    private static final List<String[]> TITLES = List.of(
            new String[]{"⚔ WARRIOR ⚔", "You feel unstoppable"},
            new String[]{"🔥 POWER SURGE 🔥", "Energy flows through you"},
            new String[]{"✨ LEGEND ✨", "Your story continues"},
            new String[]{"👑 CHOSEN ONE 👑", "Destiny awaits"},
            new String[]{"💀 DANGER 💀", "Stay sharp out there"}
    );

    public TestCommand() {
        super("hype", "Feel legendary for a moment", false);
    }

    @Override
    protected void execute(
            @Nonnull CommandContext commandContext,
            @Nonnull Store<EntityStore> store,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull PlayerRef playerRef,
            @Nonnull World world
    ) {
        String[] pick = TITLES.get(RANDOM.nextInt(TITLES.size()));

        EventTitleUtil.showEventTitleToPlayer(
                playerRef,
                Message.raw(pick[0]),
                Message.raw(pick[1]),
                true
        );
    }
}
