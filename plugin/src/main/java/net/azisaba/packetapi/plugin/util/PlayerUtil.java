package net.azisaba.packetapi.plugin.util;

import io.netty.channel.ChannelPipeline;
import net.azisaba.packetapi.common.PacketDataAccessor;
import net.azisaba.packetapi.plugin.PacketHandler;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

public class PlayerUtil {
    private static final String PIPELINE_NAME = "azisaba_packetapi";

    public static void inject(@NotNull Player player) {
        if (!player.isOnline()) return;
        ChannelPipeline pipeline = PacketDataAccessor.getInstance().getChannel(player).pipeline();
        if (pipeline.get(PIPELINE_NAME) != null) return;
        try {
            pipeline.addBefore("packet_handler", PIPELINE_NAME, new PacketHandler(player));
        } catch (IllegalArgumentException ignore) {}
    }

    public static void eject(@NotNull Player player) {
        if (!player.isOnline()) return;
        ChannelPipeline pipeline = PacketDataAccessor.getInstance().getChannel(player).pipeline();
        if (pipeline.get(PIPELINE_NAME) == null) return;
        try {
            pipeline.remove(PIPELINE_NAME);
        } catch (NoSuchElementException ignore) {}
    }
}
