package net.mekaminers.mekamod.enderio;

import com.enderio.base.common.item.capacitors.FixedCapacitorItem;
import net.minecraft.world.item.Item;

/**
 * The Meka Capacitor.
 *
 * <p>Extending EnderIO's {@code FixedCapacitorItem} is what makes machines take
 * it: the item hands out the {@code EIOCapabilities.CAPACITOR} capability, and
 * that capability is the only thing EnderIO's capacitor slot filter checks
 * ({@code CapacitorUtil.isCapacitor}), so no item tag is needed. Being a
 * {@code BaseCapacitorItem} underneath also gives it right-click auto-install
 * into a machine.
 *
 * <p>This class exists so {@link net.mekaminers.mekamod.ItemRegistry} never names
 * an EnderIO type itself, which is what keeps those classes off the classpath
 * when EnderIO is not installed.
 */
public class MekaCapacitor extends FixedCapacitorItem {

    public MekaCapacitor() {
        super(MekaCapacitorData.MEKA, new Item.Properties());
    }
}
