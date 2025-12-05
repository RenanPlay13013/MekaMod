package net.mekaminers.mekamod.registries;

import net.mekaminers.mekamod.registries.armor.MekaArmor;
import net.mekaminers.mekamod.registries.armor.IndestructibleArmorItem;
import net.mekaminers.mekamod.registries.foods.FoodProperties;
import net.mekaminers.mekamod.registries.tools.MekaShield;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

import static net.mekaminers.mekamod.MekaMod.MODID;

public class Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> MEKA_FOOD = ITEMS.register("meka_food",
            () -> new Item(new Item.Properties().food(FoodProperties.MEKA_FOOD)));
    public static final DeferredItem<Item> MEKA_INGOT = ITEMS.register("meka_ingot",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> MEKA_NUGGET = ITEMS.register("meka_nugget",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> RAW_MEKA = ITEMS.register("raw_meka",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<ShieldItem> MEKA_SHIELD = ITEMS.register("meka_shield",
            () -> new MekaShield(new Item.Properties()
                    .durability(5)
                    .fireResistant()
                    .stacksTo(1)
                    .component(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
            ));

    public static final DeferredItem<ArmorItem> MEKA_HELMET = ITEMS.register("meka_helmet",
            () -> new IndestructibleArmorItem(MekaArmor.MEKA_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().stacksTo(1)));

    public static final DeferredItem<ArmorItem> MEKA_CHESTPLATE = ITEMS.register("meka_chestplate",
            () -> new IndestructibleArmorItem(MekaArmor.MEKA_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<ArmorItem> MEKA_LEGGINGS = ITEMS.register("meka_leggings",
            () -> new IndestructibleArmorItem(MekaArmor.MEKA_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<ArmorItem> MEKA_BOOTS = ITEMS.register("meka_boots",
            () -> new IndestructibleArmorItem(MekaArmor.MEKA_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1)));
}
