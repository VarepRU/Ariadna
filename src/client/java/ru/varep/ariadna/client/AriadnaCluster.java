package ru.varep.ariadna.client;

import java.util.*;

public class AriadnaCluster {

    public static List<Integer> calc(int[][][] array) {
        int cluster = 2;

        Map<Integer, Integer> clusters = new HashMap<>();

        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                for (int z = 0; z < array[x][y].length; z++) {
                    if (array[x][y][z] == 1) {
                        clusters.put(cluster, MarkNearby(array, x, y, z, cluster));
                        cluster++;

                    }
                }
            }
        }
        int[] sumArray = calculateClusterSums(array, clusters);
        int maxCount = maxCount(sumArray);
        int maxLine = maxLine(sumArray);

        //test only
        /*if (clusters.isEmpty()) {
            System.out.println("no clusters.");
        }

        System.out.println("Clusters::");
        for (Map.Entry<Integer, Integer> entry : clusters.entrySet()) {
            System.out.println("Cluster " + entry.getKey() + ": " + entry.getValue() + " blocks");
        }*/

        return List.of(maxLine, maxCount);
    }

    //я не уверен что понимаю что я делаю, но оно пока работает
    private static int MarkNearby(int[][][] array, int x, int y, int z, int cluster) {
        array[x][y][z] = cluster;
        int s = 1;

        if (x < array.length - 2 && array[x + 1][y][z] == 1) {
            s += MarkNearby(array, x + 1, y, z, cluster);
        }
        if (x > 1 && array[x - 1][y][z] == 1) {
            s += MarkNearby(array, x - 1, y, z, cluster);
        }


        if (y < array[x].length - 2 && array[x][y + 1][z] == 1) {
            s += MarkNearby(array, x, y + 1, z, cluster);
        }
        if (y > 1 && array[x][y - 1][z] == 1) {
            s += MarkNearby(array, x, y - 1, z, cluster);
        }


        if (z < array[x][y].length - 2 && array[x][y][z + 1] == 1) {
            s += MarkNearby(array, x, y, z + 1, cluster);
        }
        if (z > 1 && array[x][y][z - 1] == 1) {
            s += MarkNearby(array, x, y, z - 1, cluster);
        }
        return s;
    }

    private static int[] calculateClusterSums(int[][][] array, Map<Integer, Integer> clusters) {
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

    private static int maxCount(int[] sumArray) {
        int maxCount = 0;
        for (int x = 0; x < sumArray.length; x++) {
            if (sumArray[x] > maxCount) {
                maxCount = sumArray[x];
            }
        }
        return maxCount;
    }

    private static int maxLine(int[] sumArray) {
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