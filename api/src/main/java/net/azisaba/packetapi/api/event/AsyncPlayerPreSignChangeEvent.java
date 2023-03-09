package net.azisaba.packetapi.api.event;

import com.avaje.ebean.validation.NotEmpty;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Fired when the player attempts to change the text on a sign.
 */
public class AsyncPlayerPreSignChangeEvent extends AsyncPlayerEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Location location;
    private final List<String> lines;
    private boolean cancelled = false;

    public AsyncPlayerPreSignChangeEvent(@NotNull Player player, @NotNull Location location, @NotNull List<String> lines) {
        super(player);
        this.location = Objects.requireNonNull(location, "location");
        this.lines = Objects.requireNonNull(lines, "lines");
        if (lines.size() != 4) {
            throw new IllegalArgumentException("lines must contain 4 elements");
        }
    }

    /**
     * Returns the location of the sign.
     * @return the location
     */
    public @NotNull Location getLocation() {
        return location;
    }

    /**
     * Returns the lines of the sign. The list always contains 4 elements.
     * @return the lines
     */
    public @NotNull List<@NotNull String> getLines() {
        return lines;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static @NotEmpty HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
