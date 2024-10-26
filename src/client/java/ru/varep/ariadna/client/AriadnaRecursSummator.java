package ru.varep.ariadna.client;

import me.shedaniel.autoconfig.AutoConfig;

public class AriadnaRecursSummator {
    static AriadnaConfig cfg = AutoConfig.getConfigHolder(AriadnaConfig.class).getConfig();
    private int[][][] array;
    private static int VeinRadius = cfg.VEIN_RADIUS;
    private static int lavaMax = cfg.LAVA;
    public AriadnaRecursSummator(int[][][] array) {
        this.array = array;
    }

    public int maxCount(int[][][] array) {
        int[] sumArray = Summator(array);
        int maxCount = 0;

        for (int x = 0; x < array.length; x++) {
            if (sumArray[x] > maxCount) {
                maxCount = sumArray[x];
            }
        }
        return maxCount;
    }

    public int maxLine(int[][][] array) {
        int[] sumArray = Summator(array);
        int maxCount = 0;
        int maxLine = 0;

        for (int x = 0; x < array.length; x++) {
            if (sumArray[x] > maxCount) {
                maxCount = sumArray[x];
                maxLine = x;
            }
        }
        return maxLine;
    }

    public int[] Summator(int[][][] array) {
        int[] sumArray = new int[array.length];
        for (int x = 0; x < array.length; x++) {

            for (int y = 0; y < array[x].length; y++) {
                for (int z = 0; z < array[x][y].length; z++) {
                    setRecursive(x, y, z);
                    setRecursive(x, y, z);
                }
            }
        sumArray[x] = countArea(x);
        }
        return sumArray;
    }

    private void setRecursive(int x, int y, int z) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    if ( Math.abs(i) + Math.abs(j) + Math.abs(k) > 1) {
                        continue;
                    }

                    int newX = x + i;
                    int newY = y + j;
                    int newZ = z + k;

                    if (newX >= 0 && newX < array.length &&
                            newY >= 0 && newY < array[0].length &&
                            newZ >= 0 && newZ < array[0][0].length) {

                        if (array[newX][newY][newZ] == 1) {
                            array[newX][newY][newZ] = -1;
                            setRecursive(newX, newY, newZ);
                        }
                    }
                }
            }
        }
    }

    public int countArea(int x)
    {
        int countE = 0;
        int lava = 0;

        for (int i = x-VeinRadius; i <= x+VeinRadius; i++) {
            for (int j = 0; j <= array.length; j++) {
                for (int k = 0; k <= array.length; k++) {
                    if (i >= 0 && i < array.length &&
                            j >= 0 && j < array[0].length &&
                            k >= 0 && k < array[0][0].length) {

                        if (array[i][j][k] == -1) {
                            countE++;
                            array[i][j][k] = 1;
                        }
                        if (array[i][j][k] == 2) {
                            lava++;
                        }
                        if (lava >= lavaMax) {
                            break;
                        }
                    }
                }
            }
        }
        return countE;
    }
}


