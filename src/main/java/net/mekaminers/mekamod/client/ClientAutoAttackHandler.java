package net.mekaminers.mekamod.client;

import net.mekaminers.mekamod.MekaCompat;
import net.mekaminers.mekamod.network.MekaNetwork;
import net.mekaminers.mekamod.network.StartAutoAttackPacket;
import net.mekaminers.mekamod.network.StopAutoAttackPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;

/**
 * Client side of the Meka Glove's auto-attack.
 *
 * <p>Holding the attack button and "being on target" are both things only the
 * client knows, so it watches them and reports the two transitions: one packet
 * when a target is acquired and one when it is lost. No packet per swing.
 */
public final class ClientAutoAttackHandler {

    private static final int NO_TARGET = -1;

    /** Last value reported to the server, so we only speak up when it changes. */
    private int sentTarget = NO_TARGET;

    /**
     * The player the report above belongs to. Respawning, changing dimension and
     * reconnecting all replace the local player and wipe the server's state, so
     * the comparison has to be re-established against the new one.
     */
    @Nullable
    private LocalPlayer reportedFor;

    private ClientAutoAttackHandler() {
    }

    public static void register() {
        MinecraftForge.EVENT_BUS.register(new ClientAutoAttackHandler());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null || minecraft.level == null) {
            // Nothing to talk to; the server has forgotten us anyway.
            this.forget(null);
            return;
        }

        if (minecraft.player != this.reportedFor) {
            this.forget(minecraft.player);
        }
        int target = currentTarget(minecraft);

        if (target == this.sentTarget) {
            return;
        }

        if (target == NO_TARGET) {
            MekaNetwork.sendToServer(new StopAutoAttackPacket());
        } else {
            MekaNetwork.sendToServer(new StartAutoAttackPacket(target));
        }
        this.sentTarget = target;
    }

    /**
     * Mirrors the server's attack-strength reset, so the crosshair cooldown
     * indicator recharges in step with the swings the server is making.
     */
    public static void resetAttackCooldown() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null) {
            player.resetAttackStrengthTicker();
        }
    }

    private void forget(@Nullable LocalPlayer player) {
        this.reportedFor = player;
        this.sentTarget = NO_TARGET;
    }

    /** The entity the player is currently holding the attack button on, if any. */
    private static int currentTarget(Minecraft minecraft) {
        LocalPlayer player = minecraft.player;

        if (player == null || minecraft.screen != null || !minecraft.mouseHandler.isMouseGrabbed()) {
            return NO_TARGET;
        }

        if (!minecraft.options.keyAttack.isDown() || !player.isAlive() || player.isSpectator()) {
            return NO_TARGET;
        }

        if (!MekaCompat.hasMekaGlove(player)) {
            return NO_TARGET;
        }

        if (!(minecraft.hitResult instanceof EntityHitResult entityHit)) {
            return NO_TARGET;
        }
        Entity target = entityHit.getEntity();

        if (target == player || !target.isAlive() || !target.isAttackable()) {
            return NO_TARGET;
        }
        return target.getId();
    }
}
