package net.mekaminers.mekamod.tools;

import net.mekaminers.mekamod.ItemRegistry;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public class MekaPickaxe extends PickaxeItem {
    public MekaPickaxe() {
        super(new Tier() {
            public int getUses() {
                return 3000;
            }

            public float getSpeed() {
                return 200f;
            }

            public float getAttackDamageBonus() {
                return 11f;
            }
            @Override
            public int getLevel() {
                return 4;
            }

            public int getEnchantmentValue() {
                return 50;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ItemRegistry.MEKA_INGOT.get()));
            }
        }, 0, -3f, new Item.Properties());
    };
}
