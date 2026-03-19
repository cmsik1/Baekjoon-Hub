import java.util.*;
import java.io.*;

public class Main {

	static int N, M, islandNum = 2, islandCnt = 0;
	static int[] dr = { 1, 0, 0, -1 };
	static int[] dc = { 0, 1, -1, 0 };
	static int[] parents;
	static int[][] map;

	static int getMinBridge(PriorityQueue<int[]> pq) {
		int count = 0;
		int bridgeSum = 0;
		
		while (!pq.isEmpty()) {
			if (count == islandCnt - 1) {
				break;
			}

			int[] curr = pq.poll();
			int from = curr[0];
			int to = curr[1];
			int length = curr[2];
			if (findRoot(from) != findRoot(to)) {
				unionRoot(from, to);
				bridgeSum += length;
				count++;
			}
		}

		if (count != islandCnt - 1)
			bridgeSum = -1;
		return bridgeSum;
	}

	static int findRoot(int x) {
		if (parents[x] == x) {
			return x;
		}
		return parents[x] = findRoot(parents[x]);
	}

	static void unionRoot(int x, int y) {
		int rx = findRoot(x);
		int ry = findRoot(y);

		if (rx != ry)
			parents[ry] = parents[rx];
	}

	static PriorityQueue<int[]> setAdjList() {
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[2] - b[2]));

		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				for (int i = 0; i < 2; i++) {
					int from = map[r][c];
					int nr = r + dr[i];
					int nc = c + dc[i];

					if (map[r][c] == 0) continue;
					
					while (0 <= nr && nr < N && 0 <= nc && nc < M) {
						if (map[nr][nc] == from) {
							break;
						}

						if (map[nr][nc] != 0) {
							if (Math.abs(nr - r) + Math.abs(nc - c) > 2) {
								pq.add(new int[] { from, map[nr][nc], Math.abs(nr - r) + Math.abs(nc - c) - 1 });
							}
							break;
						}

						nr += dr[i];
						nc += dc[i];
					}
				}
			}
		}

		return pq;
	}

	static void setIsland() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < M; c++) {
				if (map[r][c] == 1) {
					bfs(r, c);
					islandCnt++;
				}
			}
		}
	}

	static void bfs(int sr, int sc) {
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		dq.add(new int[] { sr, sc });
		map[sr][sc] = islandNum;

		while (!dq.isEmpty()) {
			int[] curr = dq.poll();

			for (int i = 0; i < 4; i++) {
				int nr = curr[0] + dr[i];
				int nc = curr[1] + dc[i];

				if (0 <= nr && nr < N && 0 <= nc && nc < M && map[nr][nc] == 1) {
					map[nr][nc] = islandNum;
					dq.add(new int[] { nr, nc });
				}
			}
		}
		islandNum++;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		parents = new int[8]; // 섬 최대개수 6개 & 섬 번호 2번부터 시작
		for (int i = 0; i < 8; i++) {
			parents[i] = i;
		}
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < M; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		setIsland();
		System.out.println(getMinBridge(setAdjList()));
	}

}
