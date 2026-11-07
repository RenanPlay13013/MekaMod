package net.mekaminers.mekamod.network;

import net.mekaminers.mekamod.client.ClientAutoAttackHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Sent after every auto-attack swing that actually landed.
 *
 * <p>{@code Player#attack} zeroes the attack-strength ticker, but that counter is
 * kept separately on each side and vanilla never syncs it: the client only resets
 * its own when the player clicks. Without this packet the crosshair cooldown
 * indicator would sit permanently full while the glove is swinging.
 *
 * <p>The client work is reached through {@link DistExecutor} so that nothing in
 * {@code net.minecraft.client} is resolved on a dedicated server, where this class
 * is still loaded to register the channel.
 */
public record ResetAttackCooldownPacket() {

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientAutoAttackHandler::resetAttackCooldown);
    }
}
