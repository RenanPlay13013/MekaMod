package net.mekaminers.mekamod.registries.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.tiers.MekaAxeTier.MEKA_AXE_TIER;
import static net.mekaminers.mekamod.registries.tiers.MekaHoeTier.MEKA_HOE_TIER;

public class MekaHoe {
    public static final DeferredRegister<Item> MEKA_HOE_REGISTRY = DeferredRegister.createItems(MODID);

    public static final Supplier<HoeItem> MEKA_HOE = MEKA_HOE_REGISTRY.register("meka_hoe",
            () -> new HoeItem(
                    MEKA_HOE_TIER,
                    new Item.Properties().attributes(
                            HoeItem.createAttributes(
                                    MEKA_HOE_TIER,
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
