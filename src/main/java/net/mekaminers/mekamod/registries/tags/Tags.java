package net.mekaminers.mekamod.registries.tags;

import net.mekaminers.mekamod.MekaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Tags {

   public static class Blocks {
      public static final TagKey<Block> NEEDS_MEKA_TOOL = createTag("needs_meka_tool");
      public static final TagKey<Block> INCORRECT_FOR_MEKA_TOOL = createTag("incorrect_for_meka_tool");
      public static final TagKey<Block> MINEABLE_WITH_PAXEL = createTag("paxel");

      private static TagKey<Block> createTag(String name) {
         return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MekaMod.MODID, name));
      }
   }

   public static class Items {

      private static TagKey<Item> createTag(String name) {
         return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MekaMod.MODID, name));
      }
   }
}
