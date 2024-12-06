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
            int res = sumArray(a);
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

    public static int sumArray(int[][][] array) {
        int sumInt = 0;
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                for (int z = 0; z < array[x][y].length; z++) {
                    if (array[x][y][z] == 1) {
                        sumInt++;
                    }
                }
            }
        }
        return sumInt;
    }

}
