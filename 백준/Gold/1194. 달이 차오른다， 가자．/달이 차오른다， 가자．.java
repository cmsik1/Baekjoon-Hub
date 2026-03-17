import java.util.*;
import java.io.*;

public class Main {

    static int N, M;
    static int[] dr = { 1, 0, 0, -1 };
    static int[] dc = { 0, 1, -1, 0 };
    static char[][] map;

    static int bfs(int sr, int sc) {
        boolean[][][] visited = new boolean[N][M][64];
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[] { sr, sc, 0, 0 }); // 위치, 열쇠 보유 상태, 이동 거리
        visited[sr][sc][0] = true;

        while (!dq.isEmpty()) {
            int[] curr = dq.poll();
            int r = curr[0];
            int c = curr[1];
            int status = curr[2];
            int time = curr[3];

            if (map[r][c] == '1')
                return time;

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (0 > nr || nr >= N || 0 > nc || nc >= M || map[nr][nc] == '#') {
                    continue;
                }

                if ('A' <= map[nr][nc] && map[nr][nc] <= 'F') {
                    if ((status & (1 << (map[nr][nc] - 'A'))) == 0) continue;
                }

                int nextStatus = status;
                if ('a' <= map[nr][nc] && map[nr][nc] <= 'f') {
                    nextStatus |= (1 << (map[nr][nc] - 'a'));
                }

                if (!visited[nr][nc][nextStatus]) {
                    visited[nr][nc][nextStatus] = true;
                    dq.add(new int[]{nr, nc, nextStatus, time + 1});
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int sr = 0;
        int sc = 0;
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];

        for (int r = 0; r < N; r++) {
            String input = br.readLine();
            for (int c = 0; c < M; c++) {
                char value = input.charAt(c);
                map[r][c] = value;
                if (value == '0') {
                    sr = r;
                    sc = c;
                }
            }
        }

        map[sr][sc] = '.'; // 시작좌표 설정
        System.out.println(sb.append(bfs(sr, sc)).toString());
    }
}
