package ru.varep.ariadna.client.functions;

import java.util.*;

public class AriadnaCluster {
    final Map<Integer, Integer> clusters;
    final Map<Integer, Integer> lavas;
    final int[][][] array;

    public AriadnaCluster(Map<Integer, Integer> clusters, Map<Integer, Integer> lavas, int[][][] array) {
        this.clusters = clusters;
        this.lavas = lavas;
        this.array = array;
    }

    static AriadnaCluster calc(int[][][] array) {
        int cluster = 2;
        int lava = 1001;
        Map<Integer, Integer> clusters = new HashMap<>();
        Map<Integer, Integer> lavas = new HashMap<>();

        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                for (int z = 0; z < array[x][y].length; z++) {
                    if (array[x][y][z] == 1) {
                        clusters.put(cluster, MarkNearby(array, x, y, z, cluster));
                        cluster++;

                    }
                    if (array[x][y][z] == 1000) {
                        lavas.put(lava, MarkNearby(array, x, y, z, lava));
                        lava++;

                    }
                }
            }
        }

        return new AriadnaCluster(clusters, lavas, array);

    }

    //я не уверен что понимаю что я делаю, но оно пока работает
    private static int MarkNearby(int[][][] array, int x, int y, int z, int cluster) {
        array[x][y][z] = cluster;
        int s = 1;

        if (x < array.length - 2 && (array[x + 1][y][z] == 1 ||  array[x + 1][y][z] == 1000)) {
            s += MarkNearby(array, x + 1, y, z, cluster);
        }
        if (x > 1 && (array[x - 1][y][z] == 1 ||  array[x - 1][y][z] == 1000)) {
            s += MarkNearby(array, x - 1, y, z, cluster);
        }


        if (y < array[x].length - 2 && (array[x][y + 1][z] == 1 ||  array[x][y + 1][z] == 1000)) {
            s += MarkNearby(array, x, y + 1, z, cluster);
        }
        if (y > 1 && (array[x][y - 1][z] == 1 ||  array[x][y - 1][z] == 1000)) {
            s += MarkNearby(array, x, y - 1, z, cluster);
        }


        if (z < array[x][y].length - 2 && (array[x][y][z + 1] == 1 ||  array[x][y][z + 1] == 1000)) {
            s += MarkNearby(array, x, y, z + 1, cluster);
        }
        if (z > 1 && (array[x][y][z - 1] == 1 ||  array[x][y][z - 1] == 1000)) {
            s += MarkNearby(array, x, y, z - 1, cluster);
        }
        return s;
    }


}