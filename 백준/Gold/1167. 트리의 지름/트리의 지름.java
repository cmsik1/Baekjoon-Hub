import java.util.*;
import java.io.*;

public class Main {

    static int V;
    static List<List<int[]>> adjList;

    static int[] bfs(int start) {
        boolean[] visited = new boolean[V + 1];
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[]{start, 0});
        visited[start] = true;

        int maxPath = 0;
        int target = 0;
        while (!dq.isEmpty()) {
            int[] curr = dq.poll();
            int node = curr[0];
            int weight = curr[1];

            if (maxPath < weight) {
                maxPath = weight;
                target = node;
            }

            for (int[] neighbor : adjList.get(node)) {
                if (!visited[neighbor[0]]) {
                    visited[neighbor[0]] = true;
                    dq.add(new int[]{neighbor[0], weight + neighbor[1]});
                }
            }
        }

        return new int[]{target, maxPath};
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        V = Integer.parseInt(br.readLine());
        adjList = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < V; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            List<int[]> node = adjList.get(Integer.parseInt(st.nextToken()));

            while (true) {
                int to = Integer.parseInt(st.nextToken());
                if (to == -1) break;
                int weight = Integer.parseInt(st.nextToken());
                node.add(new int[]{to, weight});
            }
        }

        int[] start = bfs(1);
        System.out.println(bfs(start[0])[1]);
    }
}
