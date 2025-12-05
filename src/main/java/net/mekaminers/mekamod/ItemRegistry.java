package net.mekaminers.mekamod;

import net.mekaminers.mekamod.materials.MekaIngot;
import net.mekaminers.mekamod.materials.MekaNugget;
import net.mekaminers.mekamod.materials.RawMeka;
import net.mekaminers.mekamod.tools.*;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MekaMod.MODID);

    public static final RegistryObject<Item> MEKA_HELMET = ITEMS.register("meka_helmet",
            () -> new MekaHelmet());

    public static final RegistryObject<Item> MEKA_CHESTPLATE = ITEMS.register("meka_chestplate",
            () -> new MekaChestplate());

    public static final RegistryObject<Item> MEKA_LEGGINGS = ITEMS.register("meka_leggings",
            () -> new MekaLeggings());

    public static final RegistryObject<Item> MEKA_BOOTS = ITEMS.register("meka_boots",
            () -> new MekaBoots());

    public static final RegistryObject<Item> MEKA_PICKAXE = ITEMS.register("meka_pickaxe",
            () -> new MekaPickaxe());

    public static final RegistryObject<Item> MEKA_PAXEL = ITEMS.register("meka_paxel",
            () -> new MekaPaxel());

    public static final RegistryObject<Item> MEKA_SHOVEL = ITEMS.register("meka_shovel",
            () -> new MekaShovel());

    public static final RegistryObject<Item> MEKA_HOE = ITEMS.register("meka_hoe",
            () -> new MekaHoe());

    public static final RegistryObject<Item> MEKA_AXE = ITEMS.register("meka_axe",
            () -> new MekaAxe());

    public static final RegistryObject<Item> MEKA_SWORD = ITEMS.register("meka_sword",
            () -> new MekaSword());

    public static final RegistryObject<Item> MEKA_BLOCK = ITEMS.register("meka_block",
            () -> new BlockItem(BlockRegistry.MEKA_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> MEKA_FOOD = ITEMS.register("meka_food",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(100).saturationMod(1000f)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 50),  1.0F)
                    .build())));

    public static final RegistryObject<Item> MEKA_INGOT = ITEMS.register("meka_ingot",
            () -> new MekaIngot());

    public static final RegistryObject<Item> MEKA_NUGGET = ITEMS.register("meka_nugget",
            () -> new MekaNugget());

    public static final RegistryObject<Item> RAW_MEKA = ITEMS.register("raw_meka",
            () -> new RawMeka());

    public static final RegistryObject<Item> DIVIN_BREAD = ITEMS.register("divin_bread" ,
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(50)
                .saturationMod(500f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 50), 1f)
                .build())));

    public static final RegistryObject<Item> DIVIN_POTATO = ITEMS.register("divin_potato" ,
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(50)
                .saturationMod(500f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 50), 1f)
                .build())));

    public static final RegistryObject<Item> DIVIN_CAPUTINO = ITEMS.register("divin_caputino" ,
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(50)
                .saturationMod(500f)
                .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 50), 1f)
                .build())));

}

