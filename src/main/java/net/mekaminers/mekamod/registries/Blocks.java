package net.mekaminers.mekamod.registries;

import net.mekaminers.mekamod.registries.blocks.MekaBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.mekaminers.mekamod.MekaMod.MODID;

public class Blocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredHolder<Block, Block> MEKA_BLOCK = BLOCKS.register("meka_block", MekaBlock::new);

}
