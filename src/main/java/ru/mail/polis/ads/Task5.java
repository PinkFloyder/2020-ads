package ru.mail.polis.ads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Task5 {



    private static final boolean BLACK = true;
    private static final boolean WHITE = false;
    private static Queue<Integer> queue = new LinkedList<>();

    private static class Node {
        ArrayList<Integer> list = new ArrayList<>();
        boolean color = WHITE;
        int count = Integer.MAX_VALUE;
        LinkedList<Integer> linkedList = new LinkedList<>();
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
            nodes[in1].list.add(in2);
            nodes[in2].list.add(in1);
        }

        nodes[start].count = 0;

        FordBellman(nodes, start);

        if (nodes[end].count == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(nodes[end].count);
            for (int i = nodes[end].linkedList.size() - 1; i >= 0; i--)
                System.out.print((nodes[end].linkedList.get(i) + 1) + " ");
        }
    }



    private static void FordBellman(Node[] nodes, int index) {
        nodes[index].linkedList.push(index);
        nodes[index].color = BLACK;
        for (int i : nodes[index].list) {
            if (nodes[i].count > nodes[index].count + 1) {
                queue.add(i);
                nodes[i].count = nodes[index].count + 1;
                nodes[i].linkedList.add(index);
            }
        }
        if (!queue.isEmpty())
            FordBellman(nodes, queue.poll());
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