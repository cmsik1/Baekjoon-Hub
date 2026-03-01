import java.util.*;
import java.io.*;

public class Main {

    static int N, M, H;
    static int[][] map;
    static boolean found = false;

    static void getCombination(int idx, int count, int target) {
        if (found) return;
        if (count == target) {
            if (simulation()) found = true;
            return;
        }

        for (int i = idx; i < (N + 1) * (H + 1); i++) {
            int row = i / (N + 1);
            int col = i % (N + 1);

            if (row > 0 && col > 0 && col < N) {
                if (map[row][col] == 0 && map[row][col - 1] == 0 && map[row][col + 1] == 0) {
                    map[row][col] = 1;
                    getCombination(idx + 1, count + 1, target);
                    map[row][col] = 0;
                }
            }
        }
    }

    static boolean simulation() {
        for (int col = 1; col <= N; col++) {
            int cc = col;
            for (int row = 1; row <= H; row++) {
                if (map[row][cc] == 1) cc++;
                else if (map[row][cc - 1] == 1) cc--;
            }
            if (cc != col) return false;
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        map = new int[H + 1][N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 1;
        }

        for (int i = 0; i <= 3; i++) {
            getCombination(0, 0, i);
            if (found) {
                System.out.println(sb.append(i).toString());
                break;
            }
        }
        if (!found) System.out.println(-1);
    }

}
