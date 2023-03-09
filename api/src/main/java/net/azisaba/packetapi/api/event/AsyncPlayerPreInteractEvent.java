package net.azisaba.packetapi.api.event;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AsyncPlayerPreInteractEvent extends AsyncPlayerEvent implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Action action;
    private final Entity entity;
    private final Location location;
    private final EquipmentSlot hand;
    private boolean cancelled = false;

    public AsyncPlayerPreInteractEvent(
            @NotNull Player player,
            @NotNull Action action,
            @Nullable Entity entity,
            @Nullable Location location,
            @Nullable EquipmentSlot hand
    ) {
        super(player);
        this.action = Objects.requireNonNull(action, "action");
        this.entity = entity;
        this.location = location;
        this.hand = hand;
    }

    /**
     * Returns the action of the interaction.
     * @return action
     */
    public @NotNull Action getAction() {
        return action;
    }

    /**
     * Returns the interacted entity. May be null if the entity is not registered in the world.
     * @return entity
     */
    public @Nullable Entity getEntity() {
        return entity;
    }

    /**
     * Returns the location of the interaction. May be null for {@link Action#ATTACK} and {@link Action#INTERACT}.
     * @return location
     */
    public @Nullable Location getLocation() {
        return location;
    }

    /**
     * Returns the hand used for the interaction. May be null for {@link Action#ATTACK}.
     * @return hand
     */
    public @Nullable EquipmentSlot getHand() {
        return hand;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public enum Action {
        INTERACT,
        ATTACK,
        INTERACT_AT,
    }
}
