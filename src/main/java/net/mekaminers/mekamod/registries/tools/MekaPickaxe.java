package net.mekaminers.mekamod.registries.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.tiers.MekaPickaxeTier.MEKA_PICKAXE_TIER;

public class MekaPickaxe extends PickaxeItem {
    public static final DeferredRegister<Item> MEKA_PICKAXE_REGISTRY = DeferredRegister.createItems(MODID);

    public static final Supplier<PickaxeItem> MEKA_PICKAXE = MEKA_PICKAXE_REGISTRY.register("meka_pickaxe",
            () -> new PickaxeItem(
                    MEKA_PICKAXE_TIER,
                    new Item.Properties().attributes(
                            PickaxeItem.createAttributes(
                                    MEKA_PICKAXE_TIER,
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

    public MekaPickaxe(Tier p_42961_, Properties p_42964_) {
        super(p_42961_, p_42964_);
    }
}
