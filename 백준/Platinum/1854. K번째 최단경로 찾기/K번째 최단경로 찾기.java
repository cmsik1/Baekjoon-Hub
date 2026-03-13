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
        PriorityQueue<Vertex> pq = new PriorityQueue<>((a, b) -> (a.weight - b.weight));
        pq.add(new Vertex(1, 0));

        while (!pq.isEmpty()) {
            Vertex curr = pq.poll();

            if (nodePaths.get(curr.to).size() < K) {
                nodePaths.get(curr.to).add(curr.weight);
            } else {
                if (nodePaths.get(curr.to).peek() > curr.weight) {
                    nodePaths.get(curr.to).poll();
                    nodePaths.get(curr.to).add(curr.weight);
                } else {
                    continue;
                }
            }

            for (Vertex vertex : adjList.get(curr.to)) {
                if (nodePaths.get(vertex.to).size() < K || nodePaths.get(vertex.to).peek() > vertex.weight + curr.weight)
                    pq.add(new Vertex(vertex.to, vertex.weight + curr.weight));
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
            if (nodePaths.get(i).size() < K) sb.append(-1 + "\n");
            else sb.append(nodePaths.get(i).peek() + "\n");
        }
        System.out.println(sb.toString());
    }

}