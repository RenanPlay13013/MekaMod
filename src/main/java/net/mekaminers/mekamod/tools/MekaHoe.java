package net.mekaminers.mekamod.tools;

import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The Meka hoe.
 *
 * <p>Its stats come from {@code tools.hoe} in the config. Vanilla bakes a tool's
 * stats into fields in the constructor, so the overrides below answer from the
 * config instead, through the stack-sensitive accessors the game calls at runtime.
 */
public class MekaHoe extends HoeItem {

    private final LiveToolStats stats = new LiveToolStats(Config.HOE);

    public MekaHoe() {
        super(new ConfigurableTier(Config.HOE),
                (int) Config.floatValue(Config.HOE.attackDamage),
                Config.floatValue(Config.HOE.attackSpeed),
                new Item.Properties());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND
                ? this.stats.modifiers(BASE_ATTACK_DAMAGE_UUID, BASE_ATTACK_SPEED_UUID)
                : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.stats.durability();
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.stats.enchantmentValue();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_HOE) ? this.stats.miningSpeed() : 1.0F;
    }
}
