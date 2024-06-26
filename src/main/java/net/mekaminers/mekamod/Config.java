package net.mekaminers.mekamod;

import net.minecraftforge.fml.common.Mod;

import static net.mekaminers.mekamod.MekaMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final net.minecraftforge.common.ForgeConfigSpec SPEC;
    public static final Config CONFIG;

    static {
        final var builder = new net.minecraftforge.common.ForgeConfigSpec.Builder();
        CONFIG = new Config(builder);
        SPEC = builder.build();
    }

    Config(net.minecraftforge.common.ForgeConfigSpec.Builder builder) {
        // Configuração aqui
    }
}
