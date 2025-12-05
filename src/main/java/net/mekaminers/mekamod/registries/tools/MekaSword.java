package net.mekaminers.mekamod.registries.tools;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.tiers.MekaSwordTier.MEKA_SWORD_TIER;

public class MekaSword {
    public static final DeferredRegister.Items MEKA_SWORD_REGISTRY = DeferredRegister.createItems(MODID);

    public static final Supplier <SwordItem> MEKA_SWORD = MEKA_SWORD_REGISTRY.register("meka_sword",
            () -> new SwordItem(MEKA_SWORD_TIER,
                    new Item.Properties().attributes(
                            SwordItem.createAttributes(
                                    MEKA_SWORD_TIER,
                                    -1,
                                    0
                            )
                    )) {
                @Override
                public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
                    return 0;
                }
            }
    );
}
