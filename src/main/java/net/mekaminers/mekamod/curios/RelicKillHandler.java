package net.mekaminers.mekamod.curios;

import net.mekaminers.mekamod.Config;
import net.mekaminers.mekamod.MekaCompat;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Credits kills to the relic its killer is wearing.
 *
 * <p>Runs server side only, and writes straight into the worn stack's NBT. Curios
 * notices on its next tick, because it compares the slot against a copy taken
 * earlier, and swaps the relic's health modifier for the updated one.
 *
 * <p>Registered only when Curios is installed, since the relic does not exist
 * otherwise.
 */
public final class RelicKillHandler {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();

        if (dead.level().isClientSide) {
            return;
        }

        // The source's owner, not the projectile, so a bow kill counts too.
        if (!(event.getSource().getEntity() instanceof Player killer) || killer == dead) {
            return;
        }
        double points = dead instanceof Player
                ? Config.value(Config.RELIC.playerKillValue)
                : Config.value(Config.RELIC.mobKillValue);

        if (points <= 0.0D) {
            return;
        }
        ItemStack relic = MekaCompat.findMekaRelic(killer);

        if (!relic.isEmpty()) {
            MekaRelic.addKillPoints(relic, points);
        }
    }
}
