import java.util.*;
import java.io.*;

public class Main {

    static int V, E;
    static int[] head, to, next, weight;

    static List<Integer> getMinPath(int start, int end) {
        int[] minDist = new int[V + 1];
        int[] prevCity = new int[V + 1];
        Arrays.fill(minDist, Integer.MAX_VALUE);
        minDist[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0];
            int w = curr[1];

            if (minDist[node] < w) continue;

            for (int n = head[node]; n != 0; n = next[n]) {
                if (minDist[to[n]] > w + weight[n]) {
                    minDist[to[n]] = w + weight[n];
                    prevCity[to[n]] = node;
                    pq.add(new int[]{to[n], w + weight[n]});
                }
            }
        }


        List<Integer> list = new ArrayList<>();
        for (int i = end; i != 0; i = prevCity[i]) {
            list.add(i);
        }
        System.out.println(minDist[end]);
        System.out.println(list.size());

        return list;
    }

    static void printPath(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i) + " ");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        V = Integer.parseInt(br.readLine());
        E = Integer.parseInt(br.readLine());
        head = new int[V + 1];
        to = new int[E + 1];
        next = new int[E + 1];
        weight = new int[E + 1];

        for (int i = 1; i <= E; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            next[i] = head[f];
            head[f] = i;
            to[i] = t;
            weight[i] = w;
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());

        printPath(getMinPath(start, end));
    }
}
