package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;

import ru.varep.ariadna.client.config.AriadnaConfig;
import ru.varep.ariadna.client.functions.AriadnaAnalyze;
import ru.varep.ariadna.client.functions.AriadnaCount3D;
import ru.varep.ariadna.client.util.AriadnaLegal;

import ru.varep.ariadna.client.util.KeyBindings;

import java.io.IOException;
import java.util.List;


public class Ariadna implements ClientModInitializer {

    public static final String MOD_ID = "ariadna";
    { instance = this; }
    private static Ariadna instance;
    public static Ariadna getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {

        try {
            List<String> legal = AriadnaLegal.getServers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AutoConfig.register(AriadnaConfig.class, Toml4jConfigSerializer::new);
        KeyBindings.ALL.forEach(KeyBindingHelper::registerKeyBinding);


        System.out.println("[ARIADNA] Initialization finished");

        ClientTickEvents.END_WORLD_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.world == null) {
                return;
            }
            if (KeyBindings.RESULT_KEY.wasPressed()) {
                AriadnaAnalyze.process(mc);
            }
            if (KeyBindings.SUM_KEY.wasPressed()) {
                AriadnaCount3D.process(mc);
            }
        });
    }

}



