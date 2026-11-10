package net.mekaminers.mekamod.registries;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.mekaminers.mekamod.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Base of the Meka armour set. Every stat comes from the {@code armor} section of
 * the config and is read at the moment it is used, so edits apply without a
 * restart.
 *
 * <p>{@link ArmorItem} copies defence, toughness and knockback resistance out of
 * the material in its constructor and builds its attribute multimap there, so
 * none of that can be changed through the material afterwards. The live values
 * come from overriding the stack-sensitive accessors the game calls at runtime.
 */
public abstract class MekaArmor extends ArmorItem {

    /**
     * Vanilla's per-slot durability factors, indexed by {@code slot.getIndex()}
     * (feet, legs, chest, head). Vanilla keeps the same numbers private in
     * {@code ArmorMaterials}, so they are repeated here; the config multiplies
     * them.
     */
    private static final int[] DURABILITY_PER_SLOT = {13, 15, 16, 11};

    /** How many armour slots the game has, rather than assuming four. */
    private static final long ARMOR_SLOTS =
            Arrays.stream(EquipmentSlot.values()).filter(EquipmentSlot::isArmor).count();

    private static final DecimalFormat PERCENT = new DecimalFormat("0.##");

    /** Vanilla's modifier UUID for this item's slot, read off its own baked multimap. */
    @Nullable
    private UUID slotUuid;

    /** Generation and multimap in one object, so a reader never sees a mix of the two. */
    @Nullable
    private volatile Cached cached;

    public MekaArmor(ArmorItem.Type type, Item.Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return durabilityFor(type);
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return defenseFor(type);
            }

            @Override
            public int getEnchantmentValue() {
                return Config.value(Config.ARMOR.enchantmentValue);
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_iron"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(ItemRegistry.MEKA_INGOT.get());
            }

            @Override
            public String getName() {
                return "meka_armor";
            }

            @Override
            public float getToughness() {
                return Config.floatValue(Config.ARMOR.toughness);
            }

            @Override
            public float getKnockbackResistance() {
                return Config.floatValue(Config.ARMOR.knockbackResistance);
            }
        }, type, properties);
    }

    /** Fraction of magic damage a full set absorbs. */
    public static float magicResistanceFullSet() {
        return Config.floatValue(Config.ARMOR.magicResistance);
    }

    /** The share of {@link #magicResistanceFullSet()} that one piece provides. */
    public static float magicResistancePerPiece() {
        return magicResistanceFullSet() / ARMOR_SLOTS;
    }

    private static int durabilityFor(ArmorItem.Type type) {
        return DURABILITY_PER_SLOT[type.getSlot().getIndex()]
                * Config.value(Config.ARMOR.durabilityMultiplier);
    }

    private static int defenseFor(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> Config.value(Config.ARMOR.helmetDefense);
            case CHESTPLATE -> Config.value(Config.ARMOR.chestplateDefense);
            case LEGGINGS -> Config.value(Config.ARMOR.leggingsDefense);
            case BOOTS -> Config.value(Config.ARMOR.bootsDefense);
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {

        if (slot != this.type.getSlot()) {
            return super.getAttributeModifiers(slot, stack);
        }
        int generation = Config.generation();
        Cached current = this.cached;

        if (current != null && current.generation == generation) {
            return current.modifiers;
        }
        Multimap<Attribute, AttributeModifier> built = buildModifiers(slot);
        this.cached = new Cached(generation, built);
        return built;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return durabilityFor(this.type);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return Config.value(Config.ARMOR.enchantmentValue);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item.mekamod.meka_armor.magic_resistance",
                        PERCENT.format(magicResistancePerPiece() * 100.0F),
                        PERCENT.format(magicResistanceFullSet() * 100.0F))
                .withStyle(ChatFormatting.GRAY));
    }

    private Multimap<Attribute, AttributeModifier> buildModifiers(EquipmentSlot slot) {
        UUID uuid = slotUuid(slot);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier",
                defenseFor(this.type), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness",
                Config.value(Config.ARMOR.toughness), AttributeModifier.Operation.ADDITION));
        double knockback = Config.value(Config.ARMOR.knockbackResistance);

        // Vanilla leaves the entry out entirely at zero, so match that rather than
        // publishing a modifier that does nothing.
        if (knockback > 0.0D) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid,
                    "Armor knockback resistance", knockback, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    /**
     * Vanilla picks a different modifier UUID per armour slot and keeps the table
     * private, so the UUID is lifted out of the multimap {@link ArmorItem} built
     * for this item. The ARMOR entry is always present there.
     */
    private UUID slotUuid(EquipmentSlot slot) {
        UUID known = this.slotUuid;

        if (known != null) {
            return known;
        }

        for (AttributeModifier modifier : super.getDefaultAttributeModifiers(slot).get(Attributes.ARMOR)) {
            this.slotUuid = modifier.getId();
            return modifier.getId();
        }
        throw new IllegalStateException("ArmorItem published no armor modifier for " + slot);
    }

    private record Cached(int generation, Multimap<Attribute, AttributeModifier> modifiers) {
    }
}
