import java.util.*;
import java.io.*;

public class Main {

	static int R, C, T, cleanUp, cleanDown;
	static int[][] map, update;

	static int simulation() {
		for (int i = 0; i < T; i++) {
			spread();
			wind();
		}
		
		int dust = 0;
		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				if (map[r][c] > 0) {
					dust += map[r][c];
				}
			}
		}
		return dust;
	}

	static void spread() {
		int[] dr = { 1, 0, 0, -1 };
		int[] dc = { 0, 1, -1, 0 };
		update = new int[R][C];

		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				if (map[r][c] > 0) {
					int count = 0;
					for (int i = 0; i < 4; i++) {
						int nr = r + dr[i];
						int nc = c + dc[i];

						if (0 <= nr && nr < R && 0 <= nc && nc < C && map[nr][nc] > -1) {
							update[nr][nc] += map[r][c] / 5;
							count++;
						}
					}
					update[r][c] -= map[r][c] / 5 * count;
				}
			}
		}
		for (int r = 0; r < R; r++) {
			for (int c = 0; c < C; c++) {
				map[r][c] += update[r][c];
			}
		}
	}

	static void wind() {
		// 공기청정기 위쪽
		for (int r = cleanUp - 2; r >= 0; r--) map[r + 1][0] = map[r][0];
		for (int c = 1; c < C; c++) map[0][c - 1] = map[0][c];
		for (int r = 1; r <= cleanUp; r++) map[r - 1][C-1] = map[r][C-1];
		for (int c = C - 2; c > 0; c--) map[cleanUp][c + 1] = map[cleanUp][c];
		
		// 공기청정기 아래쪽
		for (int r = cleanDown + 2; r < R; r++) map[r - 1][0] = map[r][0];
		for (int c = 1; c < C; c++) map[R - 1][c - 1] = map[R - 1][c];
		for (int r = R - 2; r >= cleanDown; r--) map[r + 1][C - 1] = map[r][C - 1];
		for (int c = C - 2; c > 0; c--) map[cleanDown][c + 1] = map[cleanDown][c];
		
		map[cleanUp][1] = 0;
		map[cleanDown][1] = 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		map = new int[R][C];

		for (int r = 0; r < R; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < C; c++) {
				int value = Integer.parseInt(st.nextToken());
				map[r][c] = value;
				if (value == -1) {
					if (cleanUp == 0)
						cleanUp = r;
					else
						cleanDown = r;
				}
			}
		}
		
		System.out.println(new StringBuffer().append(simulation()).toString());
	}

}