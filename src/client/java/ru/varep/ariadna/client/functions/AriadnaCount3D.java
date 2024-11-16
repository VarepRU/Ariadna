package ru.varep.ariadna.client.functions;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import ru.varep.ariadna.client.config.AriadnaConfig;

public class AriadnaCount3D {

    public static void process(MinecraftClient mc) {
        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
        int dRadius = cfg.RADIUS;
        int[][][] a = Ariadna3D.convert3D(mc.player, dRadius);
        if (a != null) {
            int res = AriadnaSum3D.sumArray(a);
            MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.sum", res), false);
        }
    }
}
