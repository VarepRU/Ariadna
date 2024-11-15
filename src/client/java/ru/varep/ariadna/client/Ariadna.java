package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

import net.minecraft.text.Text;
import ru.varep.ariadna.client.config.AriadnaConfig;
import ru.varep.ariadna.client.functions.AriadnaAnalyze;
import ru.varep.ariadna.client.functions.AriadnaCount3D;
import ru.varep.ariadna.client.util.AriadnaLegal;

import ru.varep.ariadna.client.util.KeyBindings;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static net.minecraft.client.network.ServerInfo.ServerType.LAN;


public class Ariadna implements ClientModInitializer {

    public static final String MOD_ID = "ariadna";

    {
        instance = this;
    }

    private static Ariadna instance;

    public static Ariadna getInstance() {
        return instance;
    }

    List<String> blacklist = null;
    boolean legal = true;

    @Override
    public void onInitializeClient() {

        blacklist = AriadnaLegal.getOffline();

        System.out.println("[ARIADNA] Blacklisted servers: " + blacklist.size());

        for (int l = 0; l < blacklist.size(); l++) {
            System.out.println(l + ": " + blacklist.get(l));
        }

        AutoConfig.register(AriadnaConfig.class, Toml4jConfigSerializer::new);
        KeyBindings.ALL.forEach(KeyBindingHelper::registerKeyBinding);


        System.out.println("[ARIADNA] Initialization finished");

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            try {
                blacklist.addAll(AriadnaLegal.getServers());
                System.out.println("[ARIADNA] Blacklist updated: " + blacklist.size() + " servers");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String ip = "local";
            if (client.getCurrentServerEntry() != null ) {
                ip = client.getCurrentServerEntry().address;
                System.out.println("[ARIADNA] Server IP: " + ip);
            }
            if (blacklist.contains(ip)) {
                legal = false;
                System.out.println("[ARIADNA] You logged on illegal server: " + ip);

            }
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            legal = true;
        });

        ClientTickEvents.END_WORLD_TICK.register(client -> {

            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.world == null) {
                return;
            }
            if (KeyBindings.RESULT_KEY.wasPressed()) {
                if (legal) {
                    AriadnaAnalyze.process(mc);
                } else {
                    mc.player.sendMessage(Text.translatable("msg.ariadna.unlegal"));
                }
            }
            if (KeyBindings.SUM_KEY.wasPressed()) {
                if (legal) {
                    AriadnaCount3D.process(mc);
                } else {
                    mc.player.sendMessage(Text.translatable("msg.ariadna.unlegal"));
                }
            }
        });
    }

}



