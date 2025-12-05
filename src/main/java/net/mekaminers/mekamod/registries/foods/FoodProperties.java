package net.mekaminers.mekamod.registries.foods;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FoodProperties {
    public static final net.minecraft.world.food.FoodProperties MEKA_FOOD = new net.minecraft.world.food.FoodProperties.Builder()
            .nutrition(100)
            .saturationModifier(1000f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 50), 1.0f).build();
}
