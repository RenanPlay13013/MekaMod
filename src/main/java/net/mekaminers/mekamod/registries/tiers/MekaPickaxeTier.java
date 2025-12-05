package net.mekaminers.mekamod.registries.tiers;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class MekaPickaxeTier {
    public static final Tier MEKA_PICKAXE_TIER = new SimpleTier(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            15000,
            150f,
            250,
            10,
            () -> Ingredient.of(ItemStack.EMPTY)
    );
}
