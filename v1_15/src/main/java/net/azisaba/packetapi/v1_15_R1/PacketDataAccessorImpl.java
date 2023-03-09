package net.azisaba.packetapi.v1_15_R1;

import io.netty.channel.Channel;
import net.azisaba.packetapi.api.event.AsyncPlayerPreInteractEvent;
import net.azisaba.packetapi.common.PacketDataAccessor;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.EnumHand;
import net.minecraft.server.v1_15_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_15_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_15_R1.Vec3D;
import net.minecraft.server.v1_15_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PacketDataAccessorImpl implements PacketDataAccessor {
    @Override
    public @NotNull Channel getChannel(@NotNull Player player) {
        return ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
    }

    @Override
    public @NotNull Location getLocationInUpdateSign(@NotNull World world, @NotNull Object packetPlayInUpdateSign) {
        BlockPosition pos = ((PacketPlayInUpdateSign) packetPlayInUpdateSign).b();
        return new Location(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public @NotNull String @NotNull [] getLinesInUpdateSign(@NotNull Object packetPlayInUpdateSign) {
        return ((PacketPlayInUpdateSign) packetPlayInUpdateSign).c();
    }

    @Override
    public @NotNull AsyncPlayerPreInteractEvent.Action getActionInUseEntity(@NotNull Object packetPlayInUseEntity) {
        return AsyncPlayerPreInteractEvent.Action.valueOf(((PacketPlayInUseEntity) packetPlayInUseEntity).b().name());
    }

    @Override
    public @Nullable Entity getEntityInUseEntity(@NotNull World world, @NotNull Object packetPlayInUseEntity) {
        WorldServer worldServer = ((CraftWorld) world).getHandle();
        net.minecraft.server.v1_15_R1.Entity nmsEntity = ((PacketPlayInUseEntity) packetPlayInUseEntity).a(worldServer);
        return nmsEntity == null ? null : nmsEntity.getBukkitEntity();
    }

    @Override
    public @Nullable Location getLocationInUseEntity(@NotNull World world, @NotNull Object packetPlayInUseEntity) {
        Vec3D pos = ((PacketPlayInUseEntity) packetPlayInUseEntity).d();
        if (pos == null) {
            return null;
        }
        return new Location(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public @Nullable EquipmentSlot getHandInUseEntity(@NotNull Object packetPlayInUseEntity) {
        EnumHand hand = ((PacketPlayInUseEntity) packetPlayInUseEntity).c();
        if (hand == null) {
            return null;
        }
        return hand == EnumHand.MAIN_HAND ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
    }
}
