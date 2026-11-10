package net.mekaminers.mekamod.network;

import net.mekaminers.mekamod.client.ClientConfigSync;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Pushes the server's config file to connected clients after it is edited.
 *
 * <p>Forge already sends SERVER configs to a client during the connection
 * handshake, but only then: a later edit reaches the server through its file
 * watcher and never reaches anyone already playing, so their tooltips and
 * attribute displays would keep the values from the moment they joined. This
 * carries the same raw TOML bytes Forge's own handshake carries, and the client
 * hands them to the very same {@code ModConfig.acceptSyncedConfig} entry point.
 */
public record ConfigSyncPacket(String fileName, byte[] data) {

    public ConfigSyncPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readByteArray());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(this.fileName);
        buf.writeByteArray(this.data);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientConfigSync.accept(this.fileName, this.data));
    }
}
