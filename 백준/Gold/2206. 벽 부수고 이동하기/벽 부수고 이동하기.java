import java.io.*;
import java.util.*;

public class Main {

    static int N, M, minDist;
    static int[] dr = {1, 0, 0, -1};
    static int[] dc = {0, 1, -1, 0};
    static int[][] map;
    static boolean[][] visited;

    static void sBfs() {
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        visited[0][0] = true;
        dq.add(new int[]{0, 0, 1});

        while (!dq.isEmpty()) {
            int[] curr = dq.poll();

            if (curr[0] == N - 1 && curr[1] == M - 1) {
                minDist = curr[2];
            }

            for (int i = 0; i < 4; i++) {
                int nr = curr[0] + dr[i];
                int nc = curr[1] + dc[i];

                if (0 <= nr && nr < N && 0 <= nc && nc < M && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    if (map[nr][nc] == 0) {
                        map[nr][nc] = curr[2] + 1;
                    } else {
                        dq.add(new int[]{nr, nc, curr[2] + 1});
                    }
                }
            }
        }
    }

    static void eBfs() {
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        visited[N - 1][M - 1] = true;
        dq.add(new int[]{N - 1, M - 1, 1});

        while (!dq.isEmpty()) {
            int[] curr = dq.poll();

            for (int i = 0; i < 4; i++) {
                int nr = curr[0] + dr[i];
                int nc = curr[1] + dc[i];

                if (0 <= nr && nr < N && 0 <= nc && nc < M && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    if (map[nr][nc] != -1) {
                        if (map[nr][nc] > 0) minDist = Math.min(minDist, map[nr][nc] + curr[2]);
                    } else {
                        dq.add(new int[]{nr, nc, curr[2] + 1});
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];
        for (int r = 0; r < N; r++) {
            String input = br.readLine();
            for (int c = 0; c < M; c++) {
                map[r][c] = input.charAt(c) - '0' - 1;
            }
        }

        minDist = Integer.MAX_VALUE;
        sBfs();
        visited = new boolean[N][M];
        eBfs();

        if (minDist == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minDist);
        }
    }
}