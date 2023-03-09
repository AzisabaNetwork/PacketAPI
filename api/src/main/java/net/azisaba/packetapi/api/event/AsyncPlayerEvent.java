package net.azisaba.packetapi.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AsyncPlayerEvent extends Event {
    private final Player player;

    public AsyncPlayerEvent(@NotNull Player player) {
        super(true);
        this.player = Objects.requireNonNull(player, "player");
    }

    public final @NotNull Player getPlayer() {
        return player;
    }
}
