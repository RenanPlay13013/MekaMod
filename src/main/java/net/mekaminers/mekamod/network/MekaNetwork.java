package net.mekaminers.mekamod.network;

import net.mekaminers.mekamod.MekaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

/** The mod's single play-phase channel. */
public final class MekaNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MekaMod.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private static int nextId = 0;

    private MekaNetwork() {
    }

    public static void register() {
        CHANNEL.messageBuilder(StartAutoAttackPacket.class, nextId++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(StartAutoAttackPacket::encode)
                .decoder(StartAutoAttackPacket::new)
                .consumerMainThread(StartAutoAttackPacket::handle)
                .add();

        CHANNEL.messageBuilder(StopAutoAttackPacket.class, nextId++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(StopAutoAttackPacket::encode)
                .decoder(buf -> new StopAutoAttackPacket())
                .consumerMainThread(StopAutoAttackPacket::handle)
                .add();

        CHANNEL.messageBuilder(ResetAttackCooldownPacket.class, nextId++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ResetAttackCooldownPacket::encode)
                .decoder(buf -> new ResetAttackCooldownPacket())
                .consumerMainThread(ResetAttackCooldownPacket::handle)
                .add();
    }

    public static void sendToServer(Object packet) {
        CHANNEL.sendToServer(packet);
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}
