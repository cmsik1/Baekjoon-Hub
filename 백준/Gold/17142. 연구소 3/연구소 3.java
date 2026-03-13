import java.util.*;
import java.io.*;

public class Main {

	static int N, M, emptySpaceCnt, minTime;
	static int[] dr = { 1, 0, 0, -1 };
	static int[] dc = { 0, 1, -1, 0 };
	static int[][] map;
	static List<int[]> virus;
	static boolean[][] visited;

	static void combination(int idx, int status) {
		if (Integer.bitCount(status) == M) {
			int time = getMinTime(status);
			if (time != -1)
				minTime = Math.min(minTime, time);
			return;
		}

		for (int i = idx; i < virus.size(); i++) {
			combination(i + 1, status | (1 << i));
		}
	}

	static int getMinTime(int status) {
		int spaceCnt = 0;
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		visited = new boolean[N][N];

		for (int i = 0; i < virus.size(); i++) {
			if ((status & (1 << i)) != 0)
				dq.add(new int[] { virus.get(i)[0], virus.get(i)[1], 0 });
		}

		while (!dq.isEmpty()) {
			int[] curr = dq.poll();
			int r = curr[0];
			int c = curr[1];
			int time = curr[2];

			for (int i = 0; i < 4; i++) {
				int nr = r + dr[i];
				int nc = c + dc[i];

				if (0 <= nr && nr < N && 0 <= nc && nc < N && map[nr][nc] != 1 && !visited[nr][nc]) {
					visited[nr][nc] = true;
					if (map[nr][nc] == 0 && ++spaceCnt == emptySpaceCnt) {
						return time + 1;
					}
					dq.add(new int[] { nr, nc, time + 1 });
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		emptySpaceCnt = 0;
		minTime = Integer.MAX_VALUE;
		map = new int[N][N];
		virus = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int value = Integer.parseInt(st.nextToken());
				if (value == 2)
					virus.add(new int[] { i, j });
				if (value == 0)
					emptySpaceCnt++;
				map[i][j] = value;
			}
		}

		combination(0, 0);
		if (minTime == Integer.MAX_VALUE) {
			if (emptySpaceCnt == 0) System.out.println(0);
			else System.out.println(-1);
		}
		else System.out.println(minTime);
	}

}