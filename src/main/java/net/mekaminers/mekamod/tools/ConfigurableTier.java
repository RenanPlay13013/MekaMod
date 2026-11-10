package net.mekaminers.mekamod.tools;

import net.mekaminers.mekamod.Config;
import net.mekaminers.mekamod.ItemRegistry;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * A {@link Tier} that answers from the config on every call, so edits take effect
 * without a restart.
 *
 * <p>Vanilla copies part of a tier into the item when it is built: durability,
 * mining speed and the attack modifiers are read once in the constructor. The
 * items therefore also override the stack-sensitive accessors the game uses at
 * runtime (see {@link LiveToolStats}). What this class keeps live by itself is
 * the mining level, which {@code DiggerItem.isCorrectToolForDrops} reads straight
 * off the tier, and the repair ingredient.
 */
public class ConfigurableTier implements Tier {

    private final Config.ToolValues values;

    public ConfigurableTier(Config.ToolValues values) {
        this.values = values;
    }

    @Override
    public int getUses() {
        return Config.value(this.values.durability);
    }

    @Override
    public float getSpeed() {
        return Config.floatValue(this.values.miningSpeed);
    }

    @Override
    public float getAttackDamageBonus() {
        return Config.floatValue(this.values.attackDamageBonus);
    }

    @Override
    public int getLevel() {
        return Config.value(this.values.miningLevel);
    }

    @Override
    public int getEnchantmentValue() {
        return Config.value(this.values.enchantmentValue);
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ItemRegistry.MEKA_INGOT.get());
    }
}
