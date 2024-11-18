package ru.varep.ariadna.client.functions;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import ru.varep.ariadna.client.config.AriadnaConfig;

public class AriadnaCount3D {

    public static void BlockCount(MinecraftClient mc) {
        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
        int dRadius = cfg.RADIUS;
        int depth = cfg.DEPTH;
        int[][][] a = Ariadna3D.convert3D(mc.player, dRadius, depth);
        if (a != null) {
            int res = AriadnaSum3D.sumArray(a);
            MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.sum", res), false);
        }
    }

    public static void VeinCount(MinecraftClient mc) {
        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
        int dRadius = cfg.RADIUS;
        int depth = cfg.DEPTH;
        int[][][] a = Ariadna3D.convert3D(mc.player, dRadius, depth);
        if (a != null) {
            AriadnaCluster clusters = AriadnaCluster.calc(a);
            int res = clusters.clusters.size();
            MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.vein", res), false);

        }
    }

}
