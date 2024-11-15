package ru.varep.ariadna.client.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;

public class DirectionHelper {

    public static int getDirection(PlayerEntity player) {
        Direction facing = player.getHorizontalFacing();
        return switch (facing) {
            case NORTH -> 1;
            case SOUTH -> 2;
            case EAST -> 3;
            case WEST -> 4;
            default -> 0;
        };
    }
}