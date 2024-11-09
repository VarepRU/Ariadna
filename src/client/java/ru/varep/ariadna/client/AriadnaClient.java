package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import org.lwjgl.glfw.GLFW;

import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;

public class AriadnaClient implements ClientModInitializer {

    private static int dRadius;
    private static final String MOD_ID = "ariadna";
    private static KeyBinding AriadnaResultKey;
    private static KeyBinding AriadnaSumKey;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(AriadnaConfig.class, Toml4jConfigSerializer::new);

        AriadnaResultKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ariadna.result",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_HOME,
                "category.ariadna.main"));
        AriadnaSumKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ariadna.sum",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_END,
                "category.ariadna.main"));
        System.out.println("[ARIADNA] Initialization finished");

        ClientTickEvents.END_WORLD_TICK.register(client -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.world == null) {
                return;
            }

            if (AriadnaResultKey.wasPressed()) {
                AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
                dRadius = cfg.RADIUS;
                System.out.println("Scanning in radius: " + dRadius);
                int[][][] a = Ariadna3D.convert3D(mc.player, dRadius);
                if (a != null) {
                    System.out.println("BlockArray created, calculating...");
                    List<Integer> res = AriadnaCluster.calc(a);
                    int maxLine = res.getFirst() + mc.player.getBlockPos().getX() - dRadius + 1;
                    int maxCount = res.getLast();
                    MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.result", maxLine, maxCount), false);
                } else {
                    return;
                }
            }

            if (AriadnaSumKey.wasPressed()) {
                AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
                dRadius = cfg.RADIUS;
                int[][][] a = Ariadna3D.convert3D(mc.player, dRadius);
                if (a != null) {
                    System.out.println("BlockArray created, calculating...");
                    int res = AriadnaSum3D.sumArray(a);
                    MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.sum", res), false);
                }
            }
        });
    }


}


