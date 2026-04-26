import java.io.*;
import java.util.*;

public class Main {

	static int N, M;
	static int[][] rSnow, cSnow, map;

	static int getCapability() {
		int c = Math.min(rSnow[0][1], cSnow[0][1]);
		int rIdx = 0, cIdx = 0;

		while (rIdx < N && cIdx < M) {
			if (rSnow[rIdx][1] < cSnow[cIdx][1]) {
				c = Math.max(c, rSnow[rIdx][1]);
				int row = rSnow[rIdx++][0];
				for (int i = cIdx; i < M; i++) {
					cSnow[i][1] -= map[row][cSnow[i][0]];
					map[row][cSnow[i][0]] = 0;
				}
				Arrays.sort(cSnow, cIdx, M, (a, b) -> a[1] - b[1]);
			} else {
				c = Math.max(c, cSnow[cIdx][1]);
				int col = cSnow[cIdx++][0];
				for (int i = rIdx; i < N; i++) {
					rSnow[i][1] -= map[rSnow[i][0]][col];
					map[rSnow[i][0]][col] = 0;
				}
				Arrays.sort(rSnow, rIdx, N, (a, b) -> a[1] - b[1]);
			}
		}

		return c;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		rSnow = new int[N][2];
		cSnow = new int[M][2];
		map = new int[N][M];

		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
				rSnow[r][0] = r;
				rSnow[r][1] += map[r][c];
				cSnow[c][0] = c;
				cSnow[c][1] += map[r][c];
			}
		}

		Arrays.sort(rSnow, (a, b) -> a[1] - b[1]);
		Arrays.sort(cSnow, (a, b) -> a[1] - b[1]);
		
		System.out.println(getCapability());
	}
}
