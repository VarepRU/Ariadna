package ru.varep.ariadna.client.functions;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import ru.varep.ariadna.client.config.AriadnaConfig;

import java.util.List;

public class AriadnaAnalyze {

    public static void process(MinecraftClient mc) {

        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();

        int dRadius = cfg.RADIUS;

        System.out.println("Scanning in radius: " + dRadius);
        int[][][] a = Ariadna3D.convert3D(mc.player, dRadius);
        if (a != null) {
            System.out.println("BlockArray created, calculating...");
            AriadnaCluster clusters = AriadnaCluster.calc(a);
            int[] sums = null;
            int pos = 0;
            switch (cfg.DIR) {
                case X:
                    sums = AriadnaScannerHelper.calculateX(clusters.array, clusters.clusters);
                    pos = mc.player.getBlockPos().getX();
                    break;
                case Z:
                    sums = AriadnaScannerHelper.calculateZ(clusters.array, clusters.clusters);
                    pos = mc.player.getBlockPos().getZ();
                    break;
            }
            int maxLine = AriadnaScannerHelper.maxLine(sums) + pos - dRadius + 1;
            int maxCount = AriadnaScannerHelper.maxCount(sums);

            MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.result",cfg.DIR, maxLine, maxCount), false);
        }
    }
}
