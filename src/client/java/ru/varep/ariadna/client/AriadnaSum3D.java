package ru.varep.ariadna.client;

public class AriadnaSum3D {
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
