package ru.varep.ariadna.client.functions;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import ru.varep.ariadna.client.config.AriadnaConfig;
import ru.varep.ariadna.client.util.DirectionHelper;

public class Ariadna3D {

    public static int[][][] convert3D(PlayerEntity player, int radius) {

        BlockPos playerPos = player.getBlockPos();
        AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
        int yLevel;
        int yRadius;
        yLevel = cfg.Y_LEVEL;
        yRadius = cfg.Y_RADIUS;
        int pX = playerPos.getX();
        int pZ = playerPos.getZ();

        int minZ = pZ - radius;
        int maxZ = pZ + radius;
        int minX = pX - radius;
        int maxX = pX + radius;
        int minY = yLevel - yRadius;
        int maxY = yLevel + yRadius;

        int dir = DirectionHelper.getDirection(player); //1 = north //2 = south //3 = east //4 -west

        switch (dir) {
            case 1: //North
                minZ = pZ - radius;
                maxZ = pZ;
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.3dnorth"));
                break;
            case 2: //South
                minZ = pZ;
                maxZ = pZ + radius;
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.3dsouth"));
                break;
            case 3: // East
                minX = pX;
                maxX = pX + radius;
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.3deast"));
                break;
            case 4: // West
                minX = pX - radius;
                maxX = pX;
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.3dwest"));
                break;
            default:
                MinecraftClient.getInstance().player.sendMessage(Text.translatable("msg.ariadna.wrongdir"));
                break;
        }


        int depth = maxZ - minZ + 1;
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        int[][][] result3D = new int[width][height][depth];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < depth; z++) {
                        BlockPos pos = new BlockPos(minX + x, minY + y, minZ + z);
                        if (player.getWorld().getBlockState(pos).getBlock() == cfg.getter()) {
                            result3D[x][y][z] = 1;
                        } else if (player.getWorld().getBlockState(pos).getBlock() == Blocks.LAVA) { //will be in future updates
                            result3D[x][y][z] = -1;
                        } else {
                            result3D[x][y][z] = 0;
                        }
                    }
                }
            }
        return result3D;
    }
}


