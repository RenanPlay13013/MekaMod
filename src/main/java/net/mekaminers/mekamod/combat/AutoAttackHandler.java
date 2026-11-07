package net.mekaminers.mekamod.combat;

import net.mekaminers.mekamod.MekaCompat;
import net.mekaminers.mekamod.network.MekaNetwork;
import net.mekaminers.mekamod.network.ResetAttackCooldownPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Server side of the Meka Glove's auto-attack.
 *
 * <p>The client only reports when the player starts and stops aiming at an entity
 * with the attack button held; the swings themselves happen here, so the server
 * stays in charge of the cadence and of every check vanilla would make for a
 * hand-sent attack. A client that lies about its target, or keeps claiming one it
 * can no longer reach, gets dropped on the next tick.
 *
 * <p>Registered only when Curios is installed, since the glove does not exist
 * otherwise.
 */
public final class AutoAttackHandler {

    /**
     * Slack added on top of the player's reach, to absorb movement and lag. This
     * is only the tolerance: the reach itself comes from
     * {@link net.minecraftforge.common.extensions.IForgePlayer#getEntityReach()},
     * which reads the {@code forge:entity_reach} attribute, so any modifier a mod,
     * enchantment or effect puts on the player is honoured. The value matches what
     * Forge's own patched attack handler passes for hand-sent attacks.
     */
    private static final double REACH_PADDING = 3.0D;

    /** Target entity id per player. Only ever touched on the server thread. */
    private static final Map<UUID, Integer> TARGETS = new HashMap<>();

    public static void start(ServerPlayer player, int targetId) {
        TARGETS.put(player.getUUID(), targetId);
    }

    public static void stop(ServerPlayer player) {
        TARGETS.remove(player.getUUID());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {

        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        Integer targetId = TARGETS.get(player.getUUID());

        if (targetId == null) {
            return;
        }
        Entity target = player.serverLevel().getEntity(targetId);

        if (!canKeepAttacking(player, target)) {
            TARGETS.remove(player.getUUID());
            return;
        }

        // One hit per full attack-strength recharge. The length of that recharge
        // is read from the player's ATTACK_SPEED attribute every time, via
        // getCurrentItemAttackStrengthDelay(), so held item, enchantments,
        // potions and any modifier a mod applies all take effect immediately.
        if (player.getAttackStrengthScale(0.0F) < 1.0F) {
            return;
        }
        player.swing(InteractionHand.MAIN_HAND, true);
        player.attack(target);

        // Player#attack only resets the ticker when the swing actually landed as a
        // melee attack. If it did not -- an event cancelled it, or the entity
        // handles being hit itself -- the ticker is still full and we would swing
        // again next tick, so drop the target instead of spinning on it.
        if (player.getAttackStrengthScale(0.0F) >= 1.0F) {
            TARGETS.remove(player.getUUID());
            return;
        }
        MekaNetwork.sendToPlayer(player, new ResetAttackCooldownPacket());
    }

    @SubscribeEvent
    public void onLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        TARGETS.remove(event.getEntity().getUUID());
    }

    private static boolean canKeepAttacking(ServerPlayer player, @Nullable Entity target) {

        if (target == null || target == player || !target.isAlive() || !target.isAttackable()) {
            return false;
        }

        // Vanilla disconnects players who send these as attack targets.
        if (target instanceof ItemEntity || target instanceof ExperienceOrb
                || target instanceof AbstractArrow) {
            return false;
        }

        if (!player.isAlive() || player.isSpectator() || !MekaCompat.hasMekaGlove(player)) {
            return false;
        }
        ServerLevel level = player.serverLevel();

        if (!level.getWorldBorder().isWithinBounds(target.blockPosition())) {
            return false;
        }

        if (!player.getMainHandItem().isItemEnabled(level.enabledFeatures())) {
            return false;
        }
        // A reach of exactly zero is Forge's documented way of disabling attacks
        // and entity interactions, so honour it instead of letting the padding
        // above sneak a hit through.
        return player.getEntityReach() > 0.0D && player.canReach(target, REACH_PADDING);
    }
}
