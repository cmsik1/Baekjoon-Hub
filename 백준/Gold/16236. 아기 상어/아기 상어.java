import java.util.*;
import java.io.*;

public class Main {

    static int N, cr, cc, size, caughtFish;
    static int[] dr = {-1, 0, 0, 1};
    static int[] dc = {0, -1, 1, 0};
    static int[][] map;
    static boolean[][] visited;

    static int simulation() {
        int totalTime = 0;
        caughtFish = 0;
        size = 2;

        while (true) {
            int time = bfs();
            if (time == 0) {
                return totalTime;
            }

            totalTime += time;
            caughtFish++;

            if (caughtFish == size) {
                size++;
                caughtFish = 0;
            }
        }
    }

    static int bfs() {
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[]{cr, cc, 0});
        visited = new boolean[N][N];
        visited[cr][cc] = true;
        List<int[]> target = new ArrayList<>();
        int flag = 0;

        while (!dq.isEmpty()) {
            int[] pos = dq.poll();
            int r = pos[0];
            int c = pos[1];
            int time = pos[2];

            if (flag == 1 && target.get(0)[2] != time) {
                Collections.sort(target, (a, b) -> {
                    if (a[0] == b[0]) {
                        return a[1] - b[1];
                    } else {
                        return a[0] - b[0];
                    }
                });

                int[] next = target.get(0);
                cr = next[0];
                cc = next[1];
                map[cr][cc] = 0;
                return next[2] + 1;
            }

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (0 <= nr && nr < N && 0 <= nc && nc < N && !visited[nr][nc] && map[nr][nc] <= size) {
                    if (map[nr][nc] > 0 && map[nr][nc] < size) {
                        target.add(new int[]{nr, nc, time});
                        flag = 1;
                    }
                    visited[nr][nc] = true;
                    dq.add(new int[]{nr, nc, time + 1});
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];

        for (int r = 0; r < N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                int value = Integer.parseInt(st.nextToken());
                map[r][c] = value;
                if (value == 9) {
                    cr = r;
                    cc = c;
                    map[r][c] = 0;
                }
            }
        }

        System.out.println(new StringBuffer().append(simulation()).toString());
    }

}