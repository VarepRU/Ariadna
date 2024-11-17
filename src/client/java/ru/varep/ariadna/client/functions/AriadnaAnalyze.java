package ru.varep.ariadna.client.functions;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import ru.varep.ariadna.client.config.AriadnaConfig;

import java.util.List;

import static ru.varep.ariadna.client.util.DirectionHelper.getDirection;

public class AriadnaAnalyze {

    public static void process(MinecraftClient mc) {

        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();

        int dRadius = cfg.RADIUS;
        String dir = "";
        int[][][] a = Ariadna3D.convert3D(mc.player, dRadius);
        if (a != null) {
            AriadnaCluster clusters = AriadnaCluster.calc(a);
            int[] sums = null;
            int pos = 0;
            switch (getDirection(mc.player)) {
                case 1,2:
                    sums = AriadnaScannerHelper.calculateX(clusters.array, clusters.clusters, clusters.lavas, cfg.LAVA_CHECK, cfg.LAVA_SIZE);
                    pos = mc.player.getBlockPos().getX();
                    dir = "X";
                    break;
                case 3,4:
                    sums = AriadnaScannerHelper.calculateZ(clusters.array, clusters.clusters, clusters.lavas, cfg.LAVA_CHECK, cfg.LAVA_SIZE);
                    pos = mc.player.getBlockPos().getZ();
                    dir = "Z";
                    break;
            }
            int maxLine = AriadnaScannerHelper.maxLine(sums);
            int maxCount = AriadnaScannerHelper.maxCount(sums);
            int maxLineDim = maxLine + pos  - dRadius ;
            maxLineDim = maxLineDim < 0 ? maxLineDim + 1 : maxLineDim;

            MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.result", dir, maxLineDim, maxCount), false);
        }
    }
}
