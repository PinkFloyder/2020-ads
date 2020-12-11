package ru.mail.polis.ads;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Task4 {



    private static int[] arr;
    private static LinkedList<Integer>[] arrStack;
    private static Set<Integer> lot = new HashSet<>();


    private static class Node {
        Map<Integer, Integer> map = new HashMap<>();
    }

    public static void main(String[] args) {
        FastScanner fastScanner = new FastScanner(System.in);
        int n = fastScanner.nextInt();
        int m = fastScanner.nextInt();

        int start = fastScanner.nextInt() - 1;
        int end = fastScanner.nextInt() - 1;

        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node();

        for (int i = 0; i < m; i++) {
            int in1 = fastScanner.nextInt() - 1;
            int in2 = fastScanner.nextInt() - 1;
            int value = fastScanner.nextInt();
            nodes[in1].map.put(in2, value);
            nodes[in2].map.put(in1, value);
        }

        arr = new int[n];
        arrStack = new LinkedList[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.MAX_VALUE;
            arrStack[i] = new LinkedList<>();
        }
        arr[start] = 0;


        FordBellman(nodes, start);
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

        System.out.println(arr[end]);
        for (int i : arrStack[end]) {
            System.out.print(++i + " ");
        }
        
    }

    private static void FordBellman(Node[] nodes, int index) {
        arrStack[index].add(index);
        for (Integer key : nodes[index].map.keySet()) {
            if (arr[key] > arr[index] + nodes[index].map.get(key)) {
                arr[key] = arr[index] + nodes[index].map.get(key);
                arrStack[key] = (LinkedList<Integer>) arrStack[index].clone();
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