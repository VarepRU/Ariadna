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
                int [][][] result3d = AriadnaBlock3D.convert3D(mc.player, dRadius);
                System.out.println("BlockArray created< calculating...");
                result3d.clone();
                AriadnaRecursSummator summator = new AriadnaRecursSummator(result3d);
                int maxLine = summator.maxLine(result3d) + mc.player.getBlockPos().getX() - dRadius;
                int maxCount = summator.maxCount(result3d);
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.result", maxLine, maxCount), false);
            }

            if (AriadnaSumKey.wasPressed()) {
                AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
                dRadius = cfg.RADIUS;
               sumArray(mc, dRadius);
            }

        });
    }

    public void sumArray (MinecraftClient client, int radius) {
        System.out.println("[ARIADNA] Start calculation");
        int[][][] sumA = AriadnaBlock3D.convert3D(client.player, radius);
        System.out.println("[ARIADNA] Area scanned");
        int sumInt = 0;
            for (int x = 0; x < sumA.length; x++) {
                for (int y = 0; y < sumA[x].length; y++) {
                    for (int z = 0; z < sumA[x][y].length; z++) {
                        if (sumA[x][y][z] == 1) {
                            sumInt++;
                        }
                    }
                }
            }
        MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.sum", sumInt), false);

    }

}
