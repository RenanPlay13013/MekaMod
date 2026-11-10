package net.mekaminers.mekamod.combat;

import net.mekaminers.mekamod.MekaMod;
import net.mekaminers.mekamod.registries.MekaArmor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Makes Meka armour soak magic damage.
 *
 * <p>Magic damage carries {@code minecraft:bypasses_armor}, so the armour's
 * defence points never apply to it and this cannot be done with an attribute.
 * The reduction happens on {@link LivingHurtEvent} instead, before vanilla's own
 * armour, enchantment and resistance maths, so Protection and the Resistance
 * effect still compose on top the way a player would expect.
 *
 * <p>Each worn piece contributes an equal share of
 * {@link MekaArmor#magicResistanceFullSet()}: a full set reaches it, a partial
 * set protects proportionally. Both come from the config and are read per hit, so
 * retuning them takes effect without a restart.
 */
public final class MagicResistanceHandler {

    /**
     * Which damage counts as magic. This is a tag so a datapack can extend it
     * with types the mod does not know about -- another mod's spells, or the
     * vanilla types you may consider magical, such as {@code wither},
     * {@code dragon_breath} or {@code sonic_boom}.
     */
    public static final TagKey<DamageType> IS_MAGIC =
            TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MekaMod.MODID, "is_magic"));

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {

        if (!event.getSource().is(IS_MAGIC)) {
            return;
        }
        int worn = 0;

        for (ItemStack stack : event.getEntity().getArmorSlots()) {
            if (stack.getItem() instanceof MekaArmor) {
                worn++;
            }
        }

        if (worn == 0) {
            return;
        }
        float resistance = Mth.clamp(MekaArmor.magicResistancePerPiece() * worn, 0.0F, 1.0F);
        event.setAmount(event.getAmount() * (1.0F - resistance));
    }
}
