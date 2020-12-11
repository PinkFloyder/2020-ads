package ru.mail.polis.ads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Task3 {



    private static final boolean BLACK = true;
    private static final boolean WHITE = false;
    private static int[] arr;
    private static Set<Integer> lot = new HashSet<>();



    private static class Node {
        Map<Integer, Integer> map = new HashMap<>();
        boolean color = WHITE;
    }



    public static void main(String[] args) {
        FastScanner fastScanner = new FastScanner(System.in);
        int n = fastScanner.nextInt();
        int m = fastScanner.nextInt();
        Node[] nodes = new Node[n];
        arr = new int[n];

        for (int i = 1; i < n; i++)
            arr[i] = Integer.MAX_VALUE;
        arr[0] = 0;

        for (int i = 0; i < n; i++)
            nodes[i] = new Node();

        for (int i = 0; i < m; i++) {
            int index = fastScanner.nextInt() - 1;
            int way = fastScanner.nextInt() - 1;
            int value = fastScanner.nextInt();
            if (nodes[index].map.containsKey(way))
                if (nodes[index].map.get(way) < value)
                    continue;
            nodes[index].map.put(way, value);
        }


        while (true) {
            int temp = Integer.MAX_VALUE;
            int index = -1;
            for (int j = 0; j < n; j++) {
                if (!lot.contains(j) && arr[j] < temp) {
                    index = j;
                    temp = arr[j];
                }
            }
            if (index == -1) break;
            FordBellman(nodes, index);
        }

        for (int i = 0; i < n; i++)
            System.out.print((arr[i] == Integer.MAX_VALUE ? 30000 : arr[i]) + " ");

    }

    private static void FordBellman(Node[] nodes, int index) {
        for (Integer key : nodes[index].map.keySet()) {
            if (arr[key] > arr[index] + nodes[index].map.get(key)) {
                arr[key] = arr[index] + nodes[index].map.get(key);
            }
        }
        lot.add(index);
    }



    private static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(final InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
