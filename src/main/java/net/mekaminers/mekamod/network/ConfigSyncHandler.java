package net.mekaminers.mekamod.network;

import com.mojang.logging.LogUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

import static net.mekaminers.mekamod.MekaMod.MODID;

/**
 * Re-sends the server config to everyone online whenever it is edited on disk.
 *
 * <p>Forge's own sync happens once, during the connection handshake, so without
 * this a reload would change what the server does while every connected client
 * kept showing the numbers it was given when it joined.
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ConfigSyncHandler {

    private static final Logger LOGGER = LogUtils.getLogger();

    private ConfigSyncHandler() {
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        ModConfig config = event.getConfig();

        if (!MODID.equals(config.getModId()) || config.getType() != ModConfig.Type.SERVER) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) {
            return;
        }
        byte[] data;

        try {
            data = Files.readAllBytes(config.getFullPath());
        } catch (IOException e) {
            LOGGER.error("Could not read {} to push it to clients", config.getFullPath(), e);
            return;
        }
        String fileName = config.getFileName();

        // The watcher fires this on its own thread; hop onto the server thread
        // before touching the player list.
        server.execute(() -> {
            if (!server.getPlayerList().getPlayers().isEmpty()) {
                MekaNetwork.sendToAll(new ConfigSyncPacket(fileName, data));
            }
        });
    }
}
