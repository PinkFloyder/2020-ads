package ru.mail.polis.ads.hash;


import java.io.*;
import java.util.*;

public class Task1 {

    static boolean[] used;
    static int[] tin, fup;
    static ArrayList<Node>[] g;
    static int timer;
    static int n;
    static int count;
    static ArrayList<Integer> res = new ArrayList<>();


    static class Node {

        int i;
        int where;

        public Node(int where, int i) {
            this.i = i;
            this.where = where;
        }

    }


    public static void main(String[] args) {
        FastScanner fastScanner = new FastScanner(System.in);
        n = fastScanner.nextInt();
        int m = fastScanner.nextInt();
        g = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        used = new boolean[n + 1];
        tin = new int[n + 1];
        fup = new int[n + 1];


        for (int i = 1; i <= m; i++) {
            int a1 = fastScanner.nextInt() ;
            int a2 = fastScanner.nextInt() ;

            g[a1].add(new Node(a2, i));
            g[a2].add(new Node(a1, i));
        }

        timer = 0;
        for (int i = 1; i < g.length; ++i) {
            if (!used[i]) {
                DFS(i, -1);
            }
        }

        System.out.println(count);
        if (res.size() != 0) {
            res.stream().sorted().forEach(x -> System.out.print(x + " "));
        }
    }

    static void DFS(int v, int p) {
        used[v] = true;
        tin[v] = fup[v] = timer++;
        for (int i = 0; i < g[v].size(); ++i) {
            int to = g[v].get(i).where;
            if (to == p) {
                continue;
            }
            if (used[to]) {
                fup[v] = Math.min(fup[v], tin[to]);
            } else {
                DFS(to, v);
                fup[v] = Math.min(fup[v], fup[to]);
                if (fup[to] > tin[v]) {
                    count++;
                    res.add(g[v].get(i).i);
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
