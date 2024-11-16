package ru.varep.ariadna.client.functions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AriadnaScannerHelper {

    static int[] calculateX(int[][][] array, Map<Integer, Integer> clusters, Map<Integer, Integer> lavas, boolean lavaCheck, int lavaSize) {
        int[] sumArray = new int[array.length];
        int[] yLen = new int[array.length];
        for (int i = 0; i < array.length; i++) yLen[i] = array[i].length;
        int zLen = array[0][0].length;

        for (int x = 0; x < array.length; x++) {
            Set<Integer> currentClusters = new HashSet<>();
            boolean lavaFound = false;
            for (int dx = -1; dx <= 1; dx++) {
                int currentX = x + dx;
                if (currentX < 0 || currentX >= array.length) continue;

                for (int y = 0; y < yLen[currentX]; y++) {
                    for (int z = 0; z < zLen; z++) {
                        int b = array[currentX][y][z];
                        if (b > 1000) {
                            Integer curlavaSize = lavas.get(b);
                            if (curlavaSize != null && curlavaSize >= lavaSize) {
                                lavaFound = true;
                                break;
                            }
                        } else if (b > 1) {
                            currentClusters.add(b);
                        }
                    }
                    if (lavaFound) {
                        break;
                    }
                }
                if (lavaFound) {
                    break;
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

    static int[] calculateZ(int[][][] array, Map<Integer, Integer> clusters, Map<Integer, Integer> lavas, boolean lavaCheck, int lavaSize) {
        int[] sumArray = new int[array[0][0].length];
        int xLen = array.length;
        int[] yLen = new int[xLen];
        for (int i = 0; i < xLen; i++) yLen[i] = array[i].length;
        for (int z = 0; z < array[0][0].length; z++) {
            Set<Integer> currentClusters = new HashSet<>();
            boolean lavaFound = false;
            for (int dz = -1; dz <= 1; dz++) {
                int currentZ = z + dz;
                if (currentZ < 0 || currentZ >= array[0][0].length) continue;

                for (int x = 0; x < xLen; x++) {
                    for (int y = 0; y < yLen[x]; y++) {
                        int b = array[x][y][currentZ];
                        if (b > 1000 && lavaCheck) {
                            Integer curlavaSize = lavas.get(b);
                            if (curlavaSize != null && curlavaSize >= lavaSize) {
                                lavaFound = true;
                                break;
                            }
                        } else if (b > 1) {
                            currentClusters.add(b);
                        }
                    }
                    if (lavaFound) {
                        break;
                    }
                }
                if (lavaFound) {
                    break;
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
