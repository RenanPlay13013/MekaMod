package net.mekaminers.mekamod.registries.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.tiers.MekaAxeTier.MEKA_AXE_TIER;
import static net.mekaminers.mekamod.registries.tiers.MekaPickaxeTier.MEKA_PICKAXE_TIER;

public class MekaAxe {
    public static final DeferredRegister<Item> MEKA_AXE_REGISTRY = DeferredRegister.createItems(MODID);

    public static final Supplier<AxeItem> MEKA_AXE = MEKA_AXE_REGISTRY.register("meka_axe",
            () -> new AxeItem(
                    MEKA_AXE_TIER,
                    new Item.Properties().attributes(
                            AxeItem.createAttributes(
                                    MEKA_AXE_TIER,
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
