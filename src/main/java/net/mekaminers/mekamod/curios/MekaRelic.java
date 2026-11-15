package net.mekaminers.mekamod.curios;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

/**
 * Worn in the Curios "charm" slot. Counts what its bearer kills and turns that
 * into extra max health.
 *
 * <p>The total is kept in the stack's NBT, not on the player, so the relic can
 * change hands and the progress goes with it. That also means Curios refreshes
 * the health bonus on its own: it compares the slot's stack with the previous one
 * using {@code ItemStack.matches}, which includes NBT, so every time the count
 * changes it swaps the old attribute modifier for the new one on the next tick.
 *
 * <p>The bonus is linear in the kill total and flattens at the top:
 * {@code points * (max_health_bonus / max_kill_points)}, clamped at
 * {@code max_health_bonus}. In other words each point is worth a fixed slice of
 * the maximum, so the health keeps climbing as the kills come in. Points past the
 * maximum are still stored, so lowering the cap and raising it again costs
 * nothing.
 */
public class MekaRelic extends Item implements ICurioItem {

    /** NBT key holding the accumulated kill points. */
    private static final String KILL_POINTS_KEY = "MekaKillPoints";

    private static final DecimalFormat POINTS = new DecimalFormat("0.##");

    public MekaRelic() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    /** The kill points stored on this relic. */
    public static double killPoints(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag == null ? 0.0D : tag.getDouble(KILL_POINTS_KEY);
    }

    /** Adds to the relic's kill points. Server side only; this writes to the item. */
    public static void addKillPoints(ItemStack stack, double amount) {

        if (amount <= 0.0D) {
            return;
        }
        stack.getOrCreateTag().putDouble(KILL_POINTS_KEY, killPoints(stack) + amount);
    }

    /** Extra max health this relic currently grants, in half hearts. */
    public static double healthBonus(ItemStack stack) {
        double maxPoints = Config.value(Config.RELIC.maxKillPoints);

        if (maxPoints <= 0.0D) {
            return 0.0D;
        }
        double perPoint = Config.value(Config.RELIC.maxHealthBonus) / maxPoints;
        return Math.min(killPoints(stack), maxPoints) * perPoint;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        double bonus = healthBonus(stack);

        if (bonus > 0.0D) {
            // Curios hands us the UUID of the slot the relic sits in, which is what
            // lets it find and remove this modifier again later.
            modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, "Meka Relic",
                    bonus, AttributeModifier.Operation.ADDITION));
        }
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item.mekamod.meka_relic.kills",
                        POINTS.format(killPoints(stack)))
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("item.mekamod.meka_relic.health",
                        POINTS.format(healthBonus(stack)),
                        POINTS.format(healthBonus(stack) / 2.0D))
                .withStyle(ChatFormatting.GRAY));
    }
}
