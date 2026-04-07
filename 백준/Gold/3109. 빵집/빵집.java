import java.io.*;
import java.util.*;

public class Main {

    static int R, C;
    static int[] dr = {-1, 0, 1};
    static char[][] map;

    static int getMaxPipeline() {
        int count = 0;

        for (int r = 0; r < R; r++) {
            if (setPipeline(r, 0)) {
                count++;
            }
        }

        return count;
    }

    static boolean setPipeline(int r, int c) {
        map[r][c] = 'X';

        if (c == C - 1) {
            return true;
        }

        for (int i = 0; i < 3; i++) {
            int nr = r + dr[i];
            int nc = c + 1;

            if (0 <= nr && nr < R && map[nr][nc] == '.') {
                if (setPipeline(nr, nc)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];

        for (int r = 0; r < R; r++) {
            String input = br.readLine();
            for (int c = 0; c < C; c++) {
                map[r][c] = input.charAt(c);
            }
        }

        System.out.println(getMaxPipeline());
    }
}