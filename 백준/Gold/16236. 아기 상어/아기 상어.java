import java.util.*;
import java.io.*;

public class Main {
    static int N, cr, cc, size, caughtFish;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, -1, 0, 1};
    static int[][] map;

    // 물고기/상어 위치 정보를 담는 클래스 (가독성 핵심)
    static class Node implements Comparable<Node> {
        int r, c, dist;
        Node(int r, int c, int dist) {
            this.r = r; this.c = c; this.dist = dist;
        }

        // 문제의 우선순위 조건을 여기에 정의 (거리 -> 행 -> 열)
        @Override
        public int compareTo(Node o) {
            if (this.dist != o.dist) return this.dist - o.dist;
            if (this.r != o.r) return this.r - o.r;
            return this.c - o.c;
        }
    }

    static int simulation() {
        int totalTime = 0;
        size = 2; caughtFish = 0;

        while (true) {
            Node target = findTarget(); // BFS 로직 분리
            if (target == null) return totalTime;

            // 상어 상태 갱신
            totalTime += target.dist;
            map[target.r][target.c] = 0;
            cr = target.r;
            cc = target.c;

            if (++caughtFish == size) {
                size++;
                caughtFish = 0;
            }
        }
    }

    static Node findTarget() {
        PriorityQueue<Node> pq = new PriorityQueue<>(); // 거리와 위치를 동시에 고려
        pq.add(new Node(cr, cc, 0));
        boolean[][] visited = new boolean[N][N];
        visited[cr][cc] = true;

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            // 먹을 수 있는 물고기를 찾았다면?
            // PriorityQueue이므로 가장 가까운 거리 중 상단/좌측 물고기가 먼저 나옴
            if (map[curr.r][curr.c] > 0 && map[curr.r][curr.c] < size) {
                return curr;
            }

            for (int i = 0; i < 4; i++) {
                int nr = curr.r + dr[i];
                int nc = curr.c + dc[i];

                if (nr < 0 || nr >= N || nc < 0 || nc >= N || visited[nr][nc]) continue;

                if (map[nr][nc] <= size) {
                    visited[nr][nc] = true;
                    pq.add(new Node(nr, nc, curr.dist + 1));
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
                if (map[r][c] == 9) {
                    cr = r; cc = c;
                    map[r][c] = 0;
                }
            }
        }
        System.out.println(simulation());
    }
}