package net.mekaminers.mekamod.registries.tiers;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class MekaSwordTier {
    public static final Tier MEKA_SWORD_TIER = new SimpleTier(
            Tags.Blocks.NEEDS_NETHERITE_TOOL,
            1,
            15f,
            400f,
            50,
            () -> Ingredient.of(Tags.Items.INGOTS_COPPER)
    );
}
