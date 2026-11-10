package net.mekaminers.mekamod.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.ModConfig;

/**
 * Applies a config update pushed by the server, the same way Forge applies the
 * one it sends during the handshake.
 */
public final class ClientConfigSync {

    private ClientConfigSync() {
    }

    public static void accept(String fileName, byte[] data) {

        // On an integrated server the client and the server share one ModConfig
        // instance, which the file watcher has already refreshed. Forge skips the
        // handshake copy for the same reason.
        if (Minecraft.getInstance().isLocalServer()) {
            return;
        }
        ModConfig config = ConfigTracker.INSTANCE.fileMap().get(fileName);

        if (config != null) {
            // Parses the TOML and fires Reloading, which clears the value caches
            // and moves the config generation the items compare against.
            config.acceptSyncedConfig(data);
        }
    }
}
