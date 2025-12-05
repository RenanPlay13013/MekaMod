package net.mekaminers.mekamod.registries.armor;

import net.mekaminers.mekamod.MekaMod;
import net.mekaminers.mekamod.registries.Items;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class MekaArmor {
    public static final Holder<ArmorMaterial> MEKA_ARMOR_MATERIAL = register("meka",
            Util.make(new EnumMap<>(ArmorItem.Type.class), attribute -> {
                attribute.put(ArmorItem.Type.HELMET, 150);
                attribute.put(ArmorItem.Type.CHESTPLATE, 550);
                attribute.put(ArmorItem.Type.LEGGINGS, 450);
                attribute.put(ArmorItem.Type.BOOTS, 300);
                attribute.put(ArmorItem.Type.BODY, 550);
            }), 50, 100f, 1.0f, () -> Items.MEKA_INGOT.get());


    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchantability, float toughness, float knockbackResistance,
                                                  Supplier<Item> ingredientItem) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(MekaMod.MODID, name);
        Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_DIAMOND;
        Supplier<Ingredient> ingredient = () -> Ingredient.of(ingredientItem.get());
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            typeMap.put(type, typeProtection.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
    }
}
