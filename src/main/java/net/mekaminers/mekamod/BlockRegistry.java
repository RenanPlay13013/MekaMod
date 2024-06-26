package net.mekaminers.mekamod;

import net.mekaminers.mekamod.materials.MekaBlock;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MekaMod.MODID);

    public static final RegistryObject<Block> MEKA_BLOCK = BLOCKS.register("meka_block",
            MekaBlock::new);
}
