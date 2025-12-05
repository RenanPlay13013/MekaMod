package net.mekaminers.mekamod.registries.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.tiers.MekaAxeTier.MEKA_AXE_TIER;
import static net.mekaminers.mekamod.registries.tiers.MekaShovelTier.MEKA_SHOVEL_TIER;

public class MekaShovel {
    public static final DeferredRegister<Item> MEKA_SHOVEL_REGISTRY = DeferredRegister.createItems(MODID);

    public static final Supplier<ShovelItem> MEKA_SHOVEL = MEKA_SHOVEL_REGISTRY.register("meka_shovel",
            () -> new ShovelItem(
                    MEKA_SHOVEL_TIER,
                    new Item.Properties().attributes(
                            ShovelItem.createAttributes(
                                    MEKA_SHOVEL_TIER,
                                    -1,
                                    -3
                            ))
                    ) {
                @Override
                public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
                    return 0;
                }
            }
            );
}
