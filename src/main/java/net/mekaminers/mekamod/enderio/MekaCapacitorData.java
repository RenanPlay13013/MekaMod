package net.mekaminers.mekamod.enderio;

import com.enderio.api.capacitor.CapacitorModifier;
import com.enderio.api.capacitor.ICapacitorData;
import net.mekaminers.mekamod.Config;

import java.util.Map;

/**
 * Capacitor data for the Meka Capacitor.
 *
 * <p>EnderIO's own scale, from {@code DefaultCapacitorData}, is NONE 0, BASIC 1,
 * DOUBLE_LAYER 2 and OCTADIC 3; the base acts as a multiplier on whatever a
 * machine scales with its capacitor. The value lives in {@code enderio.capacitor_base}
 * in the config and is read on every call, so it retunes without a restart: a
 * machine caches the data object, not the number, and EnderIO's scalables ask it
 * for the modifier each time they are evaluated.
 *
 * <p>Like {@code DefaultCapacitorData}, this applies its base to every modifier
 * and declares no per-modifier overrides: the API asks implementations not to
 * return unchanged modifiers, because those pollute the item tooltip.
 */
public enum MekaCapacitorData implements ICapacitorData {

    MEKA;

    @Override
    public float getBase() {
        return Config.floatValue(Config.CAPACITOR_BASE);
    }

    @Override
    public float getModifier(CapacitorModifier modifier) {
        return this.getBase();
    }

    @Override
    public Map<CapacitorModifier, Float> getAllModifiers() {
        return Map.of();
    }
}
