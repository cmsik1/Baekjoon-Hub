import java.util.*;
import java.io.*;

public class Main {

    static int N;
    static int[][] map;

    static int combination() {
        int minDiff = Integer.MAX_VALUE;

        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                for (int d1 = 1; d1 <= y - 1; d1++) {
                    for (int d2 = 1; d2 <= N - y; d2++) {
                        if (d1 + d2 > N - x) continue;
                        minDiff = Math.min(getMinDiff(x, y, d1, d2), minDiff);
                    }
                }
            }
        }

        return minDiff;
    }

    static List<int[]> getBoundary(int x, int y, int d1, int d2) {
        List<int[]> boundary = new ArrayList<>();
        boundary.add(new int[]{y, y});

        int head = y;
        int tail = y;
        int hIdx = -1; // 앞 부분 y좌표에 더해질 인덱스
        int tIdx = 1; // 뒷 부분 y좌표에 더해질 인덱스
        for (int r = x + 1; r <= x + d1 + d2; r++) {
            head += hIdx;
            tail += tIdx;
            boundary.add(new int[]{head, tail});

            if (r == x + d1) hIdx = 1;
            if (r == x + d2) tIdx = -1;
        }

        return boundary;
    }

    static int getMinDiff(int x, int y, int d1, int d2) {
        int[] population = new int[5];

        // 1번 선거구
        for (int r = 1, c = y; r < x; r++) {
            population[0] += map[r][c];
        }
        for (int r = x, c = y - 1; r < x + d1; r++, c--) {
            population[0] += map[r][c];
        }

        // 2번 선거구
        for (int r = 1, c = y; r < x; r++) {
            population[1] += map[r][N] - map[r][c];
        }
        for (int r = x, c = y; r <= x + d2; r++, c++) {
            population[1] += map[r][N] - map[r][c];
        }

        // 3번 선거구
        for (int r = x + d1, c = y - d1 - 1; r <= x + d1 + d2; r++, c++) {
            population[2] += map[r][c];
        }
        for (int r = x + d1 + d2 + 1, c = y - d1 + d2 - 1; r <= N; r++) {
            population[2] += map[r][c];
        }

        // 4번 선거구
        for (int r = x + d2 + 1, c = y + d2 - 1; r <= x + d1 + d2; r++, c--) {
            population[3] += map[r][N] - map[r][c];
        }
        for (int r = x + d1 + d2 + 1, c = y - d1 + d2 - 1; r <= N; r++) {
            population[3] += map[r][N] - map[r][c];
        }

        // 5번 선거구
        List<int[]> boundary = getBoundary(x, y, d1, d2);
        for (int r = x, idx = 0; r <= x + d1 + d2; r++, idx++) {
            if (boundary.get(idx)[0] > 0) {
                population[4] += map[r][boundary.get(idx)[1]] - map[r][boundary.get(idx)[0] - 1];
            } else {
                population[4] = map[r][boundary.get(idx)[1]];
            }
        }

        Arrays.sort(population);
        return population[4] - population[0];
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N + 1][N + 1];

        for (int r = 1; r <= N; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 1; c <= N; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        for (int r = 1; r <= N; r++) {
            for (int c = 2; c <= N; c++) {
                map[r][c] += map[r][c - 1];
            }
        }

        System.out.println(sb.append(combination()).toString());
    }

}