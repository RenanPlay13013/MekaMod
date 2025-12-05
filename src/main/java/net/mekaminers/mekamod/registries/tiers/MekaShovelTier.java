package net.mekaminers.mekamod.registries.tiers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class MekaShovelTier {
    public static final Tier MEKA_SHOVEL_TIER = new SimpleTier(
            Tags.Blocks.GLASS_BLOCKS,
            15000,
            150f,
            250,
            50,
            () -> Ingredient.of(ItemStack.EMPTY)
    );
}
