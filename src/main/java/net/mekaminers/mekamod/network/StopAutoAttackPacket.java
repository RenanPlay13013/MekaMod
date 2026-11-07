package net.mekaminers.mekamod.network;

import net.mekaminers.mekamod.combat.AutoAttackHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sent by the client when the attack button is released, the entity leaves the
 * crosshair, or anything else makes the player stop aiming at it.
 */
public record StopAutoAttackPacket() {

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();

        if (player != null) {
            AutoAttackHandler.stop(player);
        }
    }
}
