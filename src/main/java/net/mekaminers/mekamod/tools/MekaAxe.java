package net.mekaminers.mekamod.tools;

import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The Meka axe.
 *
 * <p>Its stats come from {@code tools.axe} in the config. Vanilla bakes a tool's
 * stats into fields in the constructor, so the overrides below answer from the
 * config instead, through the stack-sensitive accessors the game calls at runtime.
 */
public class MekaAxe extends AxeItem {

    private final LiveToolStats stats = new LiveToolStats(Config.AXE);

    public MekaAxe() {
        super(new ConfigurableTier(Config.AXE),
                Config.floatValue(Config.AXE.attackDamage),
                Config.floatValue(Config.AXE.attackSpeed),
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
        return state.is(BlockTags.MINEABLE_WITH_AXE) ? this.stats.miningSpeed() : 1.0F;
    }
}
