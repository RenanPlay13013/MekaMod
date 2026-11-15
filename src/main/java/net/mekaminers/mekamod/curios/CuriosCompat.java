package net.mekaminers.mekamod.curios;

import net.mekaminers.mekamod.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

/**
 * Every direct reference to the Curios API lives in this class, so it is only
 * ever loaded when Curios is installed. Reach it through
 * {@link net.mekaminers.mekamod.MekaCompat}, never directly.
 */
public final class CuriosCompat {

    private CuriosCompat() {
    }

    public static boolean hasGlove(Player player) {
        Item glove = ItemRegistry.getMekaGlove().orElse(null);

        if (glove == null) {
            return false;
        }
        return CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.isEquipped(glove))
                .orElse(false);
    }

    /**
     * The relic the player is wearing, or an empty stack. This is the live stack
     * out of the Curios inventory, not a copy, so writing to its NBT writes to the
     * worn relic.
     */
    public static ItemStack findRelic(Player player) {
        Item relic = ItemRegistry.getMekaRelic().orElse(null);

        if (relic == null) {
            return ItemStack.EMPTY;
        }
        return CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.findFirstCurio(relic)
                        .map(SlotResult::stack)
                        .orElse(ItemStack.EMPTY))
                .orElse(ItemStack.EMPTY);
    }
}
