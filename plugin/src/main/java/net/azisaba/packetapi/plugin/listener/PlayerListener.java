package net.azisaba.packetapi.plugin.listener;

import net.azisaba.packetapi.plugin.util.PlayerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        PlayerUtil.inject(e.getPlayer());
    }
}
