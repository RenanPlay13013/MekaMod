package net.mekaminers.mekamod.tools;

import net.mekaminers.mekamod.ItemRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class MekaSword extends SwordItem {

    public MekaSword() {
        super(new Tier() {

            public int getUses () {
                return 3000;
            }

            public float getSpeed () {
                return 6F;
            }

            @Override
            public float getAttackDamageBonus () {
                return 396.0F;
            }

            @Override
            public int getLevel () {
                return 2;
            }

            @Override
            public int getEnchantmentValue () {
                return 50;
            }

            @Override
            public Ingredient getRepairIngredient () {
                return Ingredient.of(ItemRegistry.MEKA_INGOT.get());
            }
        }, 3,11, new Item.Properties());
    }
}
