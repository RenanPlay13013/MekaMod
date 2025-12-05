package net.mekaminers.mekamod.registries;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

import static net.mekaminers.mekamod.registries.tags.Tags.Blocks.MINEABLE_WITH_PAXEL;
import static net.mekaminers.mekamod.registries.tiers.MekaPaxelTier.MEKA_PAXEL_TIER;

public class MekaPaxelItem extends DiggerItem {
    private final float customSpeed;
    private final float attackDamageBonus;

    public MekaPaxelItem(float customSpeed, float attackDamageBonus, Properties properties) {
        super(MEKA_PAXEL_TIER, MINEABLE_WITH_PAXEL, properties);
        this.customSpeed = customSpeed;
        this.attackDamageBonus = attackDamageBonus;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_AXE) ||
                state.is(BlockTags.MINEABLE_WITH_PICKAXE) ||
                state.is(BlockTags.MINEABLE_WITH_HOE) ||
                state.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return super.useOn(context);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (
                state.is(BlockTags.MINEABLE_WITH_AXE) ||
                state.is(BlockTags.MINEABLE_WITH_PICKAXE) ||
                state.is(BlockTags.MINEABLE_WITH_HOE) ||
                state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            return customSpeed;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
