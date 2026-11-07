package net.mekaminers.mekamod.curios;

import net.mekaminers.mekamod.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;

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
}
