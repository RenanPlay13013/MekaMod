package net.mekaminers.mekamod.tools;

import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

/**
 * The Meka sword. Note that mining_speed does nothing here: SwordItem has its
 * own digging rules (cobwebs and sword-efficient blocks) and ignores the tier.
 *
 * <p>Its stats come from {@code tools.sword} in the config. Vanilla bakes a tool's
 * stats into fields in the constructor, so the overrides below answer from the
 * config instead, through the stack-sensitive accessors the game calls at runtime.
 */
public class MekaSword extends SwordItem {

    private final LiveToolStats stats = new LiveToolStats(Config.SWORD);

    public MekaSword() {
        super(new ConfigurableTier(Config.SWORD),
                (int) Config.floatValue(Config.SWORD.attackDamage),
                Config.floatValue(Config.SWORD.attackSpeed),
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
}
