package ru.varep.ariadna.client.functions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AriadnaScannerHelper {

    static int[] calculateX(int[][][] array, Map<Integer, Integer> clusters) {
        int[] sumArray = new int[array.length];
        for (int x = 0; x < array.length; x++) {

            Set<Integer> currentClusters = new HashSet<>();
            for (int y = 0; y < array[x].length; y++) {
                for (int z = 0; z < array[x][y].length; z++) {
                    if (array[x][y][z] > 1) {
                        currentClusters.add(array[x][y][z]);
                    }
                }
            }
            if (x > 0) {
                for (int y = 0; y < array[x - 1].length; y++) {
                    for (int z = 0; z < array[x - 1][y].length; z++) {
                        if (array[x - 1][y][z] > 1) {
                            currentClusters.add(array[x - 1][y][z]);
                        }
                    }
                }
            }
            if (x < array.length - 1) {
                for (int y = 0; y < array[x + 1].length; y++) {
                    for (int z = 0; z < array[x + 1][y].length; z++) {
                        if (array[x + 1][y][z] > 1) {
                            currentClusters.add(array[x + 1][y][z]);
                        }
                    }
                }
            }
            int sum = 0;
            for (int cluster : currentClusters) {
                sum += clusters.get(cluster);
            }
            sumArray[x] = sum;
        }
        return sumArray;
    }

    static int[] calculateZ(int[][][] array, Map<Integer, Integer> clusters) {
        int[] sumArray = new int[array[0][0].length];
        for (int z = 0; z < array[0][0].length; z++) {

            Set<Integer> currentClusters = new HashSet<>();
            for (int x = 0; x < array.length; x++) {
                for (int y = 0; y < array[x].length; y++) {
                    if (array[x][y][z] > 1) {
                        currentClusters.add(array[x][y][z]);
                    }
                }
            }
            if (z > 0) {
                for (int x = 0; x < array.length; x++) {
                    for (int y = 0; y < array[x].length; y++) {
                        if (array[x][y][z - 1] > 1) {
                            currentClusters.add(array[x][y][z - 1]);
                        }
                    }
                }
            }
            if (z < array[0][0].length - 1) {
                for (int x = 0; x < array.length; x++) {
                    for (int y = 0; y < array[x].length; y++) {
                        if (array[x][y][z + 1] > 1) {
                            currentClusters.add(array[x][y][z + 1]);
                        }
                    }
                }
            }
            int sum = 0;
            for (int cluster : currentClusters) {
                sum += clusters.get(cluster);
            }
            sumArray[z] = sum;
        }
        return sumArray;
    }

    static int maxCount(int[] sumArray) {
        int maxCount = 0;
        for (int x = 0; x < sumArray.length; x++) {
            if (sumArray[x] > maxCount) {
                maxCount = sumArray[x];
            }
        }
        return maxCount;
    }

    static int maxLine(int[] sumArray) {
        int maxCount = 0;
        int maxLine = 0;
        for (int x = 0; x < sumArray.length; x++) {
            if (sumArray[x] > maxCount) {
                maxCount = sumArray[x];
                maxLine = x;
            }
        }
        return maxLine;
    }



}
