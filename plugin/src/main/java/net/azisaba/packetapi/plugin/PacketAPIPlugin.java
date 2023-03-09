package net.azisaba.packetapi.plugin;

import net.azisaba.packetapi.plugin.listener.PlayerListener;
import net.azisaba.packetapi.plugin.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketAPIPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        // inject channel handler
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerUtil.inject(player);
        }
    }

    @Override
    public void onDisable() {
        // eject channel handler
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerUtil.eject(player);
        }
    }
}
