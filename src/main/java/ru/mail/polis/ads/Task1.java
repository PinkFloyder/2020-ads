package ru.mail.polis.ads;

import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Task1 {

    private static final boolean BLACK = true;
    private static final boolean WHITE = false;


    private static class Node {
        ArrayList<Integer> list = new ArrayList<>();
        boolean color = WHITE;
    }

    private static Stack<Integer> stack = new Stack<>();

    public static void main(String[] args) {
        FastScanner fastScanner = new FastScanner(System.in);
        int n = fastScanner.nextInt();
        int m = fastScanner.nextInt();
        Node[] nodes = new Node[n];

        for (int i = 0; i < n; i++)
            nodes[i] = new Node();

        for (int i = 0; i < m; i++) {
            nodes[fastScanner.nextInt() - 1].list.add(fastScanner.nextInt() - 1);
        }


        for (int i = 0; i < n; i++) {
            if (!check(nodes, i)) {
                System.out.println(-1);
                return;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nodes[i].color == WHITE)
                DFS(nodes, i);
        }


        int r = stack.size();
        for (int i = 0; i < r; i++)
            System.out.print(stack.pop() + 1 + " ");

    }

    private static boolean check(Node[] nodes, int index) {
        int n = nodes[index].list.size();
        nodes[index].color = BLACK;
        try {
            for (int i = 0; i < n; i++) {
                if (nodes[nodes[index].list.get(i)].color == BLACK) {
                    return false;
                } else {
                    if (!check(nodes, nodes[index].list.get(i))) {
                        return false;
                    }
                }
            }
            return true;
        } finally {
            nodes[index].color = WHITE;
        }
    }

    private static void DFS(Node[] nodes, int index) {
        int n = nodes[index].list.size();
        nodes[index].color = BLACK;
        for (int i = 0; i < n; i++) {
            if (nodes[nodes[index].list.get(i)].color == WHITE) {
                DFS(nodes, nodes[index].list.get(i));
            }
        }
        stack.add(index);
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
