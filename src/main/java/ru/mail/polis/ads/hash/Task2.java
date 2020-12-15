package ru.mail.polis.ads.hash;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


public class Task2 {

    static class Node implements Comparable {
        int left, right, weight;

        public Node(int left, int right, int weight) {
            this.left = left;
            this.right = right;
            this.weight = weight;
        }

        @Override
        public int compareTo(Object o) {
            Node node = (Node)o;
            if (node.weight == weight) return 0;
            return weight > node.weight ? 1 : -1;
        }
    }

    static int find_set(int v) {
        if (arr[v] == v) {
            return v;
        } else {
            return arr[v] = find_set(arr[v]);
        }
    }


    private static int[] arr;

    public static void main(String[] args) {
        FastScanner fastScanner = new FastScanner(System.in);
        int n = fastScanner.nextInt();
        int m = fastScanner.nextInt();
        Node[] nodes = new Node[m];
        for (int i = 0; i < m; i++)
            nodes[i] = new Node(fastScanner.nextInt() - 1, fastScanner.nextInt() - 1, fastScanner.nextInt());
        Arrays.sort(nodes);
        arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = i;

        int res = 0;

        for (int i = 0; i < m; i++) {
            if (sets(nodes[i].left, nodes[i].right)) {
                res += nodes[i].weight;
            }
        }
        System.out.println(res);

    }

    static boolean sets(int a1, int a2) {
        a1 = find_set(a1);
        a2 = find_set(a2);
        if (a1 == a2) return false;

        if (a1 > a2) {
            arr[a1] = a2;
        } else {
            arr[a2] = a1;
        }
        return true;
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
