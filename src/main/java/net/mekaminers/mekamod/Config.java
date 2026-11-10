package net.mekaminers.mekamod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import static net.mekaminers.mekamod.MekaMod.MODID;

/**
 * Every tunable number in the mod, in a SERVER config that reloads while the game
 * is running and is shared by everyone connected.
 *
 * <p>Forge puts a file watcher on every config it opens, SERVER included, so
 * editing {@code <world>/serverconfig/mekamod-server.toml} fires
 * {@link ModConfigEvent.Reloading} and clears every
 * {@link ForgeConfigSpec.ConfigValue}'s cache. Nothing else is needed for live
 * reload <em>as long as values are read at the moment they are used</em>, which is
 * why none of them are copied into fields at startup.
 *
 * <p>Minecraft items normally bake their stats in the constructor, so the items
 * here override the stack-sensitive accessors the game actually calls at runtime
 * instead. See {@code LiveToolStats}.
 *
 * <p>Being a SERVER config, there is a single authoritative copy: it lives with
 * the world, and Forge sends it to each client during the connection handshake,
 * so nobody can change their own numbers. That handshake is the only sync Forge
 * does, so {@code ConfigSyncHandler} pushes later edits to the players already
 * connected; otherwise the server would act on new values while their tooltips
 * still showed the old ones.
 *
 * <p>Items are built long before any world loads, so the config is not available
 * then. {@link #value} falls back to the declared default until the file has
 * actually been read, which is also what makes the values safe to read from an
 * item constructor.
 */
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config {

    public static final ForgeConfigSpec SPEC;

    public static final ToolValues SWORD;
    public static final ToolValues PICKAXE;
    public static final ToolValues AXE;
    public static final ToolValues SHOVEL;
    public static final ToolValues HOE;
    public static final ToolValues PAXEL;
    public static final ArmorValues ARMOR;
    public static final ForgeConfigSpec.DoubleValue CAPACITOR_BASE;

    /**
     * Bumped on every load and reload. Anything caching a value derived from the
     * config compares against this to know when its cache went stale.
     */
    private static volatile int generation;

    /**
     * Whether the file has actually been read yet. Items are constructed before
     * that happens, and {@link ForgeConfigSpec.ConfigValue#get()} throws in a dev
     * environment when the config is not loaded, so every read goes through
     * {@link #value} and falls back to the declared default until then.
     */
    private static volatile boolean loaded;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Tool stats. Changes apply as soon as the file is saved.",
                "Durability changes do not shrink the damage already on an existing item.").push("tools");
        SWORD = ToolValues.define(builder, "sword", 3000, 6.0D, 496.0D, 2, 50, 3.0D, 11.0D);
        PICKAXE = ToolValues.define(builder, "pickaxe", 3000, 200.0D, 299.0D, 4, 50, 0.0D, -3.0D);
        AXE = ToolValues.define(builder, "axe", 3000, 200.0D, 299.0D, 4, 50, 0.0D, -3.0D);
        SHOVEL = ToolValues.define(builder, "shovel", 3000, 200.0D, 299.0D, 4, 50, 0.0D, -3.0D);
        HOE = ToolValues.define(builder, "hoe", 3000, 200.0D, 299.0D, 4, 50, 0.0D, -3.0D);
        PAXEL = ToolValues.define(builder, "paxel", 3000, 200.0D, 449.0D, 4, 50, 0.0D, -1.0D);
        builder.pop();

        ARMOR = ArmorValues.define(builder);

        builder.comment("Ender IO integration. Ignored when Ender IO is not installed.").push("enderio");
        CAPACITOR_BASE = builder
                .comment("Capacitor base multiplier. Ender IO's own scale is 1 basic, 2 double layer, 3 octadic.")
                .defineInRange("capacitor_base", 6.0D, 0.0D, 1000.0D);
        builder.pop();

        SPEC = builder.build();
    }

    private Config() {
    }

    /**
     * Reads a config value, or its default while the file has not been read yet.
     * Every runtime read of a tunable goes through here.
     */
    public static <T> T value(ForgeConfigSpec.ConfigValue<T> config) {
        return loaded ? config.get() : config.getDefault();
    }

    /** Convenience for the many {@code double} entries consumed as floats. */
    public static float floatValue(ForgeConfigSpec.ConfigValue<Double> config) {
        return value(config).floatValue();
    }

    /** The current config generation; see {@link #generation}. */
    public static int generation() {
        return generation;
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        markChanged(event);
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        markChanged(event);
    }

    private static void markChanged(ModConfigEvent event) {

        if (MODID.equals(event.getConfig().getModId())) {
            loaded = true;
            generation++;
        }
    }

    /** The tunables of a single tool. */
    public static final class ToolValues {

        public final ForgeConfigSpec.IntValue durability;
        public final ForgeConfigSpec.DoubleValue miningSpeed;
        public final ForgeConfigSpec.DoubleValue attackDamageBonus;
        public final ForgeConfigSpec.IntValue miningLevel;
        public final ForgeConfigSpec.IntValue enchantmentValue;
        public final ForgeConfigSpec.DoubleValue attackDamage;
        public final ForgeConfigSpec.DoubleValue attackSpeed;

        private ToolValues(ForgeConfigSpec.IntValue durability, ForgeConfigSpec.DoubleValue miningSpeed,
                           ForgeConfigSpec.DoubleValue attackDamageBonus, ForgeConfigSpec.IntValue miningLevel,
                           ForgeConfigSpec.IntValue enchantmentValue, ForgeConfigSpec.DoubleValue attackDamage,
                           ForgeConfigSpec.DoubleValue attackSpeed) {
            this.durability = durability;
            this.miningSpeed = miningSpeed;
            this.attackDamageBonus = attackDamageBonus;
            this.miningLevel = miningLevel;
            this.enchantmentValue = enchantmentValue;
            this.attackDamage = attackDamage;
            this.attackSpeed = attackSpeed;
        }

        private static ToolValues define(ForgeConfigSpec.Builder builder, String name, int durability,
                                         double miningSpeed, double attackDamageBonus, int miningLevel,
                                         int enchantmentValue, double attackDamage, double attackSpeed) {
            builder.push(name);
            ToolValues values = new ToolValues(
                    builder.comment("Total uses before the tool breaks.")
                            .defineInRange("durability", durability, 1, Integer.MAX_VALUE),
                    builder.comment("How fast it breaks the blocks it is meant for.")
                            .defineInRange("mining_speed", miningSpeed, 0.0D, 10000.0D),
                    builder.comment("Attack damage contributed by the material, added to attack_damage below.")
                            .defineInRange("attack_damage_bonus", attackDamageBonus, 0.0D, 10000.0D),
                    builder.comment("Harvest tier: 0 wood, 1 stone, 2 iron, 3 diamond, 4 netherite and above.")
                            .defineInRange("mining_level", miningLevel, 0, 10),
                    builder.comment("How well it takes enchantments at the table.")
                            .defineInRange("enchantment_value", enchantmentValue, 0, 100),
                    builder.comment("Attack damage contributed by the item itself.")
                            .defineInRange("attack_damage", attackDamage, -1000.0D, 10000.0D),
                    builder.comment("Added to the player's base attack speed of 4.",
                                    "Must stay above -4: at exactly -4 the attack speed would reach zero and",
                                    "the attack cooldown would never finish.")
                            .defineInRange("attack_speed", attackSpeed, -3.9D, 100.0D));
            builder.pop();
            return values;
        }
    }

    /** The tunables of the armour set. */
    public static final class ArmorValues {

        public final ForgeConfigSpec.IntValue helmetDefense;
        public final ForgeConfigSpec.IntValue chestplateDefense;
        public final ForgeConfigSpec.IntValue leggingsDefense;
        public final ForgeConfigSpec.IntValue bootsDefense;
        public final ForgeConfigSpec.DoubleValue toughness;
        public final ForgeConfigSpec.DoubleValue knockbackResistance;
        public final ForgeConfigSpec.IntValue enchantmentValue;
        public final ForgeConfigSpec.IntValue durabilityMultiplier;
        public final ForgeConfigSpec.DoubleValue magicResistance;

        private ArmorValues(ForgeConfigSpec.Builder builder) {
            builder.comment("Armour stats. Changes apply as soon as the file is saved.").push("armor");
            this.helmetDefense = builder.comment("Armour points from the helmet.")
                    .defineInRange("helmet_defense", 300, 0, 10000);
            this.chestplateDefense = builder.comment("Armour points from the chestplate.")
                    .defineInRange("chestplate_defense", 550, 0, 10000);
            this.leggingsDefense = builder.comment("Armour points from the leggings.")
                    .defineInRange("leggings_defense", 450, 0, 10000);
            this.bootsDefense = builder.comment("Armour points from the boots.")
                    .defineInRange("boots_defense", 150, 0, 10000);
            this.toughness = builder.comment("Armour toughness, per piece.")
                    .defineInRange("toughness", 10.0D, 0.0D, 10000.0D);
            this.knockbackResistance = builder
                    .comment("Knockback resistance per piece, as a fraction. 1 is full immunity.")
                    .defineInRange("knockback_resistance", 1.0D, 0.0D, 1.0D);
            this.enchantmentValue = builder.comment("How well the armour takes enchantments at the table.")
                    .defineInRange("enchantment_value", 50, 0, 100);
            this.durabilityMultiplier = builder
                    .comment("Multiplies vanilla's per-slot durability factors (13 boots, 15 leggings,",
                            "16 chestplate, 11 helmet) to get each piece's durability.")
                    .defineInRange("durability_multiplier", 15, 1, 10000);
            this.magicResistance = builder
                    .comment("Fraction of magic damage absorbed by a full set, split evenly across the",
                            "pieces, so each one gives a quarter of this. Which damage counts as magic is",
                            "the damage type tag mekamod:is_magic, editable by datapack.")
                    .defineInRange("magic_resistance", 0.75D, 0.0D, 1.0D);
            builder.pop();
        }

        private static ArmorValues define(ForgeConfigSpec.Builder builder) {
            return new ArmorValues(builder);
        }
    }
}
