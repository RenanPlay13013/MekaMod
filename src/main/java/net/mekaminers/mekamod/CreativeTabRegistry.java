package net.mekaminers.mekamod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MekaMod.MODID);

    public static final RegistryObject<CreativeModeTab> MEKAMOD_TAB = CREATIVE_MODE_TABS.register("mekamod_tab",
            () -> CreativeModeTab.builder().title(Component.translatable("tabs.mekamod.mekamod_tab")).icon(() -> new ItemStack(ItemRegistry.MEKA_FOOD.get()))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ItemRegistry.MEKA_FOOD.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ItemRegistry.MEKA_SWORD.get());
                        output.accept(ItemRegistry.MEKA_PICKAXE.get());
                        output.accept(ItemRegistry.MEKA_AXE.get());
                        output.accept(ItemRegistry.MEKA_SHOVEL.get());
                        output.accept(ItemRegistry.MEKA_HOE.get());
                        output.accept(ItemRegistry.MEKA_PAXEL.get());
                        output.accept(ItemRegistry.MEKA_HELMET.get());
                        output.accept(ItemRegistry.MEKA_CHESTPLATE.get());
                        output.accept(ItemRegistry.MEKA_LEGGINGS.get());
                        output.accept(ItemRegistry.MEKA_BOOTS.get());
                        ItemRegistry.getMekaGlove().ifPresent(glove -> output.accept(glove));
                        ItemRegistry.getMekaCapacitor().ifPresent(capacitor -> output.accept(capacitor));
                        output.accept(ItemRegistry.MEKA_FOOD.get());
                        output.accept(BlockRegistry.MEKA_BLOCK.get());
                        output.accept(ItemRegistry.MEKA_INGOT.get());
                        output.accept(ItemRegistry.MEKA_NUGGET.get());
                        output.accept(ItemRegistry.RAW_MEKA.get());
                        output.accept(ItemRegistry.DIVIN_POTATO.get());
                        output.accept(ItemRegistry.DIVIN_BREAD.get());
                        output.accept(ItemRegistry.DIVIN_CAPUTINO.get());
                    }).build());
}
