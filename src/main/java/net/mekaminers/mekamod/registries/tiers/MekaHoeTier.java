package net.mekaminers.mekamod.registries.tiers;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class MekaHoeTier {
    public static final Tier MEKA_HOE_TIER = new SimpleTier(
            Tags.Blocks.GLASS_BLOCKS,
            15000,
            150f,
            150,
            50,
            () -> Ingredient.of(ItemStack.EMPTY)
    );
}
