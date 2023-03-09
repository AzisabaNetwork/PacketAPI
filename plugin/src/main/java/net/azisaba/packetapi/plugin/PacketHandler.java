package net.azisaba.packetapi.plugin;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import net.azisaba.packetapi.api.event.AsyncPlayerPreInteractEvent;
import net.azisaba.packetapi.api.event.AsyncPlayerPreSignChangeEvent;
import net.azisaba.packetapi.common.PacketDataAccessor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class PacketHandler extends ChannelDuplexHandler {
    private final PacketDataAccessor packetDataAccessor = PacketDataAccessor.getInstance();
    private final Player player;

    public PacketHandler(@NotNull Player player) {
        this.player = Objects.requireNonNull(player, "player");
    }

    @Override
    public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object msg) throws Exception {
        if (msg.getClass().getSimpleName().equals("PacketPlayInUpdateSign")) {
            Location location = packetDataAccessor.getLocationInUpdateSign(player.getWorld(), msg);
            String[] lines = packetDataAccessor.getLinesInUpdateSign(msg);
            AsyncPlayerPreSignChangeEvent event = new AsyncPlayerPreSignChangeEvent(player, location, Arrays.asList(lines));
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
        } else if (msg.getClass().getSimpleName().equals("PacketPlayInUseEntity")) {
            AsyncPlayerPreInteractEvent.Action action = packetDataAccessor.getActionInUseEntity(msg);
            Entity entity = packetDataAccessor.getEntityInUseEntity(player.getWorld(), msg);
            Location location = packetDataAccessor.getLocationInUseEntity(player.getWorld(), msg);
            EquipmentSlot hand = packetDataAccessor.getHandInUseEntity(msg);
            AsyncPlayerPreInteractEvent event = new AsyncPlayerPreInteractEvent(player, action, entity, location, hand);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
        }
        super.channelRead(ctx, msg);
    }
}
