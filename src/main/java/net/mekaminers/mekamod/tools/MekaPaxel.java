package net.mekaminers.mekamod.tools;

import com.google.common.collect.Multimap;
import net.mekaminers.mekamod.Config;
import net.mekaminers.mekamod.MekaMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

/**
 * A true multi-tool: it digs everything a pickaxe, axe, shovel or hoe can dig,
 * and its right-click behaviour is vanilla's own (stripping logs, scraping and
 * de-waxing copper, flattening paths, extinguishing campfires, tilling soil),
 * so it keeps working with blocks added by other mods.
 *
 * <p>Its stats come from {@code tools.paxel} in the config. Vanilla bakes a tool's
 * stats into fields in the constructor, so the overrides below answer from the
 * config instead, through the stack-sensitive accessors the game calls at runtime.
 */
public class MekaPaxel extends DiggerItem {

    /** Union of the four vanilla mineable tags; see data/mekamod/tags/blocks/mineable/paxel.json. */
    public static final TagKey<Block> MINEABLE_WITH_PAXEL =
            TagKey.create(Registries.BLOCK, new ResourceLocation(MekaMod.MODID, "mineable/paxel"));

    /**
     * Vanilla items whose {@code useOn} holds the block-interaction logic we reuse,
     * in the order vanilla itself would try them. They are only used as behaviour
     * providers: each one reads the stack, player and block out of the context, so
     * the paxel stack is what gets damaged and the paxel is what gets the advancement.
     */
    private static final Item[] USE_ON_BEHAVIOURS = {
            Items.NETHERITE_AXE,    // strip, scrape, wax off
            Items.NETHERITE_SHOVEL, // flatten, extinguish campfire
            Items.NETHERITE_HOE     // till
    };

    private final LiveToolStats stats = new LiveToolStats(Config.PAXEL);

    public MekaPaxel() {
        super(Config.floatValue(Config.PAXEL.attackDamage),
                Config.floatValue(Config.PAXEL.attackSpeed),
                new ConfigurableTier(Config.PAXEL),
                MINEABLE_WITH_PAXEL,
                new Item.Properties());
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_PICKAXE_ACTIONS.contains(toolAction)
                || ToolActions.DEFAULT_AXE_ACTIONS.contains(toolAction)
                || ToolActions.DEFAULT_SHOVEL_ACTIONS.contains(toolAction)
                || ToolActions.DEFAULT_HOE_ACTIONS.contains(toolAction)
                || ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        for (Item behaviour : USE_ON_BEHAVIOURS) {
            InteractionResult result = behaviour.useOn(context);

            if (result != InteractionResult.PASS) {
                return result;
            }
        }
        return super.useOn(context);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND
                ? this.stats.modifiers(BASE_ATTACK_DAMAGE_UUID, BASE_ATTACK_SPEED_UUID)
                : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.stats.durability();
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.stats.enchantmentValue();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(MINEABLE_WITH_PAXEL) ? this.stats.miningSpeed() : 1.0F;
    }
}
