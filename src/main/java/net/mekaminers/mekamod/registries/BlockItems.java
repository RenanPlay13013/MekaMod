package net.mekaminers.mekamod.registries;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.Blocks.MEKA_BLOCK;


public class BlockItems {
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<BlockItem> MEKA_BLOCK_ITEM = BLOCK_ITEMS.registerSimpleBlockItem("meka_block", MEKA_BLOCK);

}
