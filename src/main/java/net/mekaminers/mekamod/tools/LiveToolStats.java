package net.mekaminers.mekamod.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

/**
 * Serves a tool's runtime stats from the config.
 *
 * <p>Plain numbers are read on every call, which is what makes them live. The
 * attribute multimap is the one thing cached, because the game asks for it very
 * often; it is rebuilt whenever {@link Config#generation()} moves, which happens
 * on every load and reload.
 */
public final class LiveToolStats {

    private final Config.ToolValues values;

    /** Generation and multimap kept in one object so a reader never sees a mix. */
    private volatile Cached cached;

    public LiveToolStats(Config.ToolValues values) {
        this.values = values;
    }

    public int durability() {
        return Config.value(this.values.durability);
    }

    public int enchantmentValue() {
        return Config.value(this.values.enchantmentValue);
    }

    public float miningSpeed() {
        return Config.floatValue(this.values.miningSpeed);
    }

    /**
     * The main-hand attribute modifiers.
     *
     * <p>The two UUIDs are vanilla's, and {@code Item} only exposes them to its
     * own subclasses, so the calling item hands them over instead of this class
     * repeating the constants.
     */
    public Multimap<Attribute, AttributeModifier> modifiers(UUID damageUuid, UUID speedUuid) {
        int generation = Config.generation();
        Cached current = this.cached;

        if (current != null && current.generation == generation) {
            return current.modifiers;
        }
        double damage = Config.value(this.values.attackDamage) + Config.value(this.values.attackDamageBonus);
        Multimap<Attribute, AttributeModifier> built = ImmutableMultimap.<Attribute, AttributeModifier>builder()
                .put(Attributes.ATTACK_DAMAGE, new AttributeModifier(damageUuid, "Tool modifier",
                        damage, AttributeModifier.Operation.ADDITION))
                .put(Attributes.ATTACK_SPEED, new AttributeModifier(speedUuid, "Tool modifier",
                        Config.value(this.values.attackSpeed), AttributeModifier.Operation.ADDITION))
                .build();
        this.cached = new Cached(generation, built);
        return built;
    }

    private record Cached(int generation, Multimap<Attribute, AttributeModifier> modifiers) {
    }
}
