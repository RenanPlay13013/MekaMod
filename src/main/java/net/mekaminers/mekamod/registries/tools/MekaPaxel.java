package net.mekaminers.mekamod.registries.tools;

import net.mekaminers.mekamod.registries.MekaPaxelItem;
import net.mekaminers.mekamod.registries.tiers.MekaPaxelTier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.mekaminers.mekamod.MekaMod.MODID;
import static net.mekaminers.mekamod.registries.tags.Tags.Blocks.MINEABLE_WITH_PAXEL;
import static net.mekaminers.mekamod.registries.tiers.MekaPaxelTier.MEKA_PAXEL_TIER;

public class MekaPaxel {
    public static final DeferredRegister.Items MEKA_PAXEL_REGISTRY = DeferredRegister.createItems(MODID);


    public static final Supplier<MekaPaxelItem> MEKA_PAXEL = MEKA_PAXEL_REGISTRY.register("meka_paxel" ,
            () -> new MekaPaxelItem(150F,250, new Item.Properties()
                    .stacksTo(1)
                    .durability(4000)
                    .fireResistant()
                    .attributes(
                            MekaPaxelItem.createAttributes(MEKA_PAXEL_TIER,
                                    350,
                                    -3)
                    )
            ) {
                @Override
                public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
                    return 0;
                }
            }
    );
}
