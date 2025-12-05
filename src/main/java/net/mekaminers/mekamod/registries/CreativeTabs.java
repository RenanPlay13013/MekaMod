package net.mekaminers.mekamod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.Items.*;
import static net.mekaminers.mekamod.registries.tools.MekaAxe.MEKA_AXE;
import static net.mekaminers.mekamod.registries.tools.MekaHoe.MEKA_HOE;
import static net.mekaminers.mekamod.registries.tools.MekaPaxel.MEKA_PAXEL;
import static net.mekaminers.mekamod.registries.tools.MekaPickaxe.MEKA_PICKAXE;
import static net.mekaminers.mekamod.registries.tools.MekaShovel.MEKA_SHOVEL;
import static net.mekaminers.mekamod.registries.tools.MekaSword.MEKA_SWORD;


public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MEKA_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("tabs.mekamod.mekamod_tab"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> MEKA_FOOD.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(MEKA_SWORD.get());
                output.accept(MEKA_PICKAXE.get());
                output.accept(MEKA_AXE.get());
                output.accept(MEKA_SHOVEL.get());
                output.accept(MEKA_HOE.get());
                output.accept(MEKA_PAXEL.get());
                output.accept(MEKA_SHIELD.get());
                output.accept(MEKA_HELMET.get());
                output.accept(MEKA_CHESTPLATE.get());
                output.accept(MEKA_LEGGINGS.get());
                output.accept(MEKA_BOOTS.get());
                output.accept(MEKA_FOOD.get());
                output.accept(MEKA_INGOT.get());
                output.accept(MEKA_NUGGET.get());
                output.accept(RAW_MEKA.get());
                output.accept(BlockItems.MEKA_BLOCK_ITEM);

            }).build());
}
