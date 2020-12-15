package ru.mail.polis.ads.hash;


import java.io.*;
import java.util.*;

public class Task1 {

    static boolean[] used;
    static int[] tin, fup;
    static ArrayList<Integer>[] g;
    static int timer;
    static int n;
    static int count;
    static ArrayList<Integer> res = new ArrayList<>();

    static Node[] nodes;

    static class Node {
        Map<Integer, Integer> map = new HashMap<>();
    }


    public static void main(String[] args) {
        FastScanner fastScanner = new FastScanner(System.in);
        n = fastScanner.nextInt();
        int m = fastScanner.nextInt();
        g = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        nodes = new Node[n];
        for (int i = 0; i < n; i++)
            nodes[i] = new Node();

        used = new boolean[n];
        tin = new int[n];
        fup = new int[n];


        for (int i = 1; i <= m; i++) {
            int a1 = fastScanner.nextInt() - 1;
            int a2 = fastScanner.nextInt() - 1;

            g[a1].add(a2);
            g[a2].add(a1);
            nodes[a1].map.put(a2, i);
            nodes[a2].map.put(a1, i);
        }

        timer = 0;
        for (int i = 0; i < n; i++)
            used[i] = false;

        for (int i = 0; i < n; i++)
            if (!used[i])
                DFS (i, -1);

        System.out.println(count);
        if (res.size() != 0) {
            res.stream().sorted().forEach(x -> System.out.print(x + " "));
        }
    }

    static void DFS(int v, int p) {
        used[v] = true;
        tin[v] = fup[v] = timer++;
        for (int i = 0; i < g[v].size(); i++) {
            int to = g[v].get(i);
            if (to == p) continue;
            if (used[to]) {
                fup[v] = Math.min(fup[v], tin[to]);
            }
            else {
                DFS(to, v);
                fup[v] = Math.min(fup[v], fup[to]);
                if (fup[to] > tin[v]) {
                    count++;
                    res.add(nodes[tin[v]].map.get(fup[to]));
                }
            }
        }
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
