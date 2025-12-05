package net.mekaminers.mekamod;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import static net.mekaminers.mekamod.registries.BlockItems.BLOCK_ITEMS;
import static net.mekaminers.mekamod.registries.Blocks.BLOCKS;
import static net.mekaminers.mekamod.registries.CreativeTabs.CREATIVE_MODE_TABS;
import static net.mekaminers.mekamod.registries.Items.ITEMS;
import static net.mekaminers.mekamod.registries.tools.MekaAxe.MEKA_AXE_REGISTRY;
import static net.mekaminers.mekamod.registries.tools.MekaHoe.MEKA_HOE_REGISTRY;
import static net.mekaminers.mekamod.registries.tools.MekaPaxel.MEKA_PAXEL_REGISTRY;
import static net.mekaminers.mekamod.registries.tools.MekaPickaxe.MEKA_PICKAXE_REGISTRY;
import static net.mekaminers.mekamod.registries.tools.MekaShovel.MEKA_SHOVEL_REGISTRY;
import static net.mekaminers.mekamod.registries.tools.MekaSword.MEKA_SWORD_REGISTRY;

@Mod(MekaMod.MODID)
public class MekaMod
{
    public static final String MODID = "mekamod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MekaMod(IEventBus modEventBus, ModContainer modContainer)
    {
        NeoForge.EVENT_BUS.register(this);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
        MEKA_AXE_REGISTRY.register(modEventBus);
        MEKA_SHOVEL_REGISTRY.register(modEventBus);
        MEKA_HOE_REGISTRY.register(modEventBus);
        MEKA_PICKAXE_REGISTRY.register(modEventBus);
        MEKA_PAXEL_REGISTRY.register(modEventBus);
        MEKA_SWORD_REGISTRY.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }
}
