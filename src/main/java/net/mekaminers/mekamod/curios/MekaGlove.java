package net.mekaminers.mekamod.curios;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Worn in the Curios "hands" slot. Implementing {@link ICurioItem} is all Curios
 * needs to treat the item as a curio, so there is nothing to register by hand.
 *
 * <p>The glove holds no state: while it is equipped,
 * {@link net.mekaminers.mekamod.combat.AutoAttackHandler} keeps swinging at
 * whatever the player holds the attack button on.
 */
public class MekaGlove extends Item implements ICurioItem {

    public MekaGlove() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item.mekamod.meka_glove.desc")
                .withStyle(ChatFormatting.GRAY));
    }
}
