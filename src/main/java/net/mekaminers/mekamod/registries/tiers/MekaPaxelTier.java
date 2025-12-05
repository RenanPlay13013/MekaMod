package net.mekaminers.mekamod.registries.tiers;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class MekaPaxelTier {
    public static final Tier MEKA_PAXEL_TIER = new SimpleTier(
            BlockTags.AIR,
            15000,
            150f,
            250,
            50,
            () -> Ingredient.of(ItemStack.EMPTY)
    );
}
