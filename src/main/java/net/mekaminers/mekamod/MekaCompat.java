package net.mekaminers.mekamod;

import net.minecraft.world.entity.player.Player;
import net.mekaminers.mekamod.curios.CuriosCompat;
import net.minecraftforge.fml.ModList;

/**
 * Soft-dependency gate. Nothing in this class may mention a type from an optional
 * mod, so it is always safe to load. The calls into {@link CuriosCompat} are
 * guarded by short-circuit evaluation: the JVM only resolves (and therefore only
 * loads) that class the first time such a call actually runs, which cannot happen
 * while Curios is absent.
 */
public final class MekaCompat {

    public static final String CURIOS_MODID = "curios";
    public static final String ENDERIO_MODID = "enderio";

    private static final boolean CURIOS_LOADED = ModList.get().isLoaded(CURIOS_MODID);
    private static final boolean ENDERIO_LOADED = ModList.get().isLoaded(ENDERIO_MODID);

    private MekaCompat() {
    }

    public static boolean isCuriosLoaded() {
        return CURIOS_LOADED;
    }

    public static boolean isEnderioLoaded() {
        return ENDERIO_LOADED;
    }

    /**
     * True when the player is wearing the Meka Glove in a Curios slot.
     */
    public static boolean hasMekaGlove(Player player) {
        return CURIOS_LOADED && CuriosCompat.hasGlove(player);
    }
}
