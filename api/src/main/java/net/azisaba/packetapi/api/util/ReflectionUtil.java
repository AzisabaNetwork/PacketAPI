package net.azisaba.packetapi.api.util;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ReflectionUtil {
    /**
     * Returns the server version like "v1_15_R1".
     * @return the server version
     */
    public static @NotNull String getServerVersion() {
        // org.bukkit.craftbukkit.v1_15_R1.CraftServer
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
