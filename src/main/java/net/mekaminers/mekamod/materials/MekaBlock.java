package net.mekaminers.mekamod.materials;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;


public class MekaBlock extends Block {
    public MekaBlock(){
        super(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)
                .strength(5.0F,6.0F)
                .requiresCorrectToolForDrops());
    }

}
