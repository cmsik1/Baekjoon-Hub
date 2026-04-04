import java.util.*;
import java.io.*;

public class Main {

    static int N, E;
    static int[] head, next, to, weight;
    static int[][] minDist;

    static void dijkstra(int start) {
        minDist[start][start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();

            if (minDist[start][curr[0]] < curr[1]) continue;

            for (int i = head[curr[0]]; i != 0; i = next[i]) {
                if (minDist[start][to[i]] > curr[1] + weight[i]) {
                    minDist[start][to[i]] = curr[1] + weight[i];
                    pq.add(new int[]{to[i], curr[1] + weight[i]});
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        head = new int[N + 1];
        next = new int[2 * E + 1];
        to = new int[2 * E + 1];
        weight = new int[2 * E + 1];
        minDist = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(minDist[i], Integer.MAX_VALUE);
        }

        for (int i = 1; i <= 2 * E; i += 2) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            next[i] = head[f];
            head[f] = i;
            to[i] = t;
            weight[i] = w;

            next[i + 1] = head[t];
            head[t] = i + 1;
            to[i + 1] = f;
            weight[i + 1] = w;
        }

        st = new StringTokenizer(br.readLine());
        int v1 = Integer.parseInt(st.nextToken());
        int v2 = Integer.parseInt(st.nextToken());

        dijkstra(1);
        dijkstra(v1);
        dijkstra(v2);
        dijkstra(N);

        if ((minDist[1][v1] == Integer.MAX_VALUE || minDist[v1][v2] == Integer.MAX_VALUE || minDist[v2][N] == Integer.MAX_VALUE)
            && (minDist[1][v2] == Integer.MAX_VALUE || minDist[v2][v1] == Integer.MAX_VALUE || minDist[v1][N] == Integer.MAX_VALUE)) {
            System.out.println(-1);
        } else {
            System.out.println(Math.min(minDist[1][v1] + minDist[v1][v2] + minDist[v2][N], minDist[1][v2] + minDist[v2][v1] + minDist[v1][N]));
        }
    }
}
