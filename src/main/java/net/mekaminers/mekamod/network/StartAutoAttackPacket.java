package net.mekaminers.mekamod.network;

import net.mekaminers.mekamod.combat.AutoAttackHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sent by the client when the attack button starts being held on an entity, and
 * again whenever the crosshair moves onto a different entity while still held.
 * The id is only a request: the server re-validates the target every tick.
 */
public record StartAutoAttackPacket(int targetId) {

    public StartAutoAttackPacket(FriendlyByteBuf buf) {
        this(buf.readVarInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.targetId);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        if (player != null) {
            AutoAttackHandler.start(player, this.targetId);
        }
    }
}
