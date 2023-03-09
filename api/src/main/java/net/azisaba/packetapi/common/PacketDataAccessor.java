package net.azisaba.packetapi.common;

import io.netty.channel.Channel;
import net.azisaba.packetapi.api.event.AsyncPlayerPreInteractEvent;
import net.azisaba.packetapi.api.event.AsyncPlayerPreSignChangeEvent;
import net.azisaba.packetapi.api.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PacketDataAccessor {
    /**
     * Returns the instance of PacketDataAccessor.
     * @return the packet data accessor
     */
    static @NotNull PacketDataAccessor getInstance() {
        try {
            Class<?> clazz = Class.forName("net.azisaba.packetapi." + ReflectionUtil.getServerVersion() + ".PacketDataAccessorImpl");
            return (PacketDataAccessor) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the channel of the player.
     * @param player player
     * @return channel
     */
    @NotNull Channel getChannel(@NotNull Player player);

    /**
     * Get location from PacketPlayInUpdateSign.
     * @param world the world
     * @param packetPlayInUpdateSign packet
     * @return location
     * @see AsyncPlayerPreSignChangeEvent#getLocation()
     */
    @NotNull Location getLocationInUpdateSign(@NotNull World world, @NotNull Object packetPlayInUpdateSign);

    /**
     * Get lines from PacketPlayInUpdateSign.
     * @param packetPlayInUpdateSign packet
     * @return lines
     * @see AsyncPlayerPreSignChangeEvent#getLines()
     */
    @NotNull String @NotNull [] getLinesInUpdateSign(@NotNull Object packetPlayInUpdateSign);

    /**
     * Get action from PacketPlayInUseEntity.
     * @param packetPlayInUseEntity packet
     * @return action
     * @see AsyncPlayerPreInteractEvent#getAction()
     */
    @NotNull AsyncPlayerPreInteractEvent.Action getActionInUseEntity(@NotNull Object packetPlayInUseEntity);

    /**
     * Get entity from PacketPlayInUseEntity.
     * @param packetPlayInUseEntity packet
     * @return entity
     * @see AsyncPlayerPreInteractEvent#getEntity()
     */
    @Nullable Entity getEntityInUseEntity(@NotNull World world, @NotNull Object packetPlayInUseEntity);

    /**
     * Get location from PacketPlayInUseEntity.
     * @param world the world
     * @param packetPlayInUseEntity packet
     * @return location
     * @see AsyncPlayerPreInteractEvent#getLocation()
     */
    @Nullable Location getLocationInUseEntity(@NotNull World world, @NotNull Object packetPlayInUseEntity);

    /**
     * Get equipment slot (hand) from PacketPlayInUseEntity.
     * @param packetPlayInUseEntity packet
     * @return equipment slot
     * @see AsyncPlayerPreInteractEvent#getHand()
     */
    @Nullable EquipmentSlot getHandInUseEntity(@NotNull Object packetPlayInUseEntity);
}
