package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Blocks;

public class AriadnaBlock3D {
    public static int[][][] convert3D(PlayerEntity player, int radius) {

        BlockPos playerPos = player.getBlockPos();
        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
        int yLevel;
        int yRadius;
        yLevel = cfg.Y_LEVEL;
        yRadius = cfg.Y_RADIUS;

        int minX = playerPos.getX() - radius;
        int minY = yLevel - yRadius;
        int minZ = playerPos.getZ() - radius;
        int maxX = playerPos.getX() + radius;
        int maxY = yLevel + yRadius;
        int maxZ = playerPos.getZ() + radius;

        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        int depth = maxZ - minZ + 1;

        int[][][] result3D = new int[width][height][depth];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    BlockPos pos = new BlockPos(minX + x, minY + y, minZ + z);

                    if ( player.getWorld().getBlockState(pos).getBlock() == Blocks.DIAMOND_ORE ||
                         player.getWorld().getBlockState(pos).getBlock() == Blocks.DEEPSLATE_DIAMOND_ORE) {

                        result3D[x][y][z] = 1;
                        }
                    else if (player.getWorld().getBlockState(pos).getBlock() == Blocks.LAVA) {
                        result3D[x][y][z] = 2;
                        }
                    else {
                        result3D[x][y][z] = 0;
                        }
                }
            }
        }
        return result3D;
    }

}
