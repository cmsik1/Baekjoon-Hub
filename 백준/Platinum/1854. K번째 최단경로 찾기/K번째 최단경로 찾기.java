import java.util.*;
import java.io.*;

public class Main {

    static int N, M, K;
    static List<List<Vertex>> adjList;
    static List<PriorityQueue<Integer>> nodePaths;

    static class Vertex {
        int to, weight;

        Vertex(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static void savePath() {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[1] - b[1]));
        pq.add(new int[]{1, 0});

        while (!pq.isEmpty()) {
            int[] info = pq.poll();
            int curr = info[0];
            int weight = info[1];

            if (nodePaths.get(curr).size() < K) {
                nodePaths.get(curr).add(weight);
            } else {
                if (nodePaths.get(curr).peek() > weight) {
                    nodePaths.get(curr).poll();
                    nodePaths.get(curr).add(weight);
                } else {
                    continue;
                }
            }

            for (Vertex vertex : adjList.get(curr)) {
                if (nodePaths.get(vertex.to).size() < K || nodePaths.get(vertex.to).peek() > vertex.weight + weight)
                    pq.add(new int[]{vertex.to, vertex.weight + weight});
            }
        }
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        adjList = new ArrayList<>();
        nodePaths = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adjList.add(new ArrayList<>());
            nodePaths.add(new PriorityQueue<>(Collections.reverseOrder()));
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            adjList.get(Integer.parseInt(st.nextToken()))
                .add(new Vertex(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        savePath();
        for (int i = 1; i <= N; i++) {
            if (nodePaths.get(i).size() < K) System.out.println(-1);
            else System.out.println(nodePaths.get(i).peek());
        }
    }

}