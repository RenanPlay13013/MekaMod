package net.mekaminers.mekamod.registries;

import net.mekaminers.mekamod.ItemRegistry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

public abstract class MekaArmor extends ArmorItem {
    public MekaArmor(ArmorItem.Type type, Item.Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 15;
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{150, 450, 550, 300}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 50;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_iron"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ItemRegistry.MEKA_INGOT.get()));
            }

            @Override
            public String getName() {
                return "meka_armor";
            }

            @Override
            public float getToughness() {
                return 10f;
            }

            @Override
            public float getKnockbackResistance() {
                return 1f;
            }
        }, type, properties);
    }
}
