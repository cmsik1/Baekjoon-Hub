import java.util.*;
import java.io.*;

public class Main {

	static int N, cr, cc, size;
	static int[] dr = { -1, 0, 0, 1 };
	static int[] dc = { 0, -1, 1, 0 };
	static int[][] map;
	static boolean[][] visited;

	static class Fish implements Comparable<Fish> {
		int r, c, dist;

		public Fish(int r, int c, int dist) {
			super();
			this.r = r;
			this.c = c;
			this.dist = dist;
		}

		@Override
		public int compareTo(Main.Fish o) {
			if (this.dist != o.dist)
				return this.dist - o.dist;
			if (this.r != o.r)
				return this.r - o.r;
			return this.c - o.c;
		}
	}

	static int simulation() {
		int totalTime = 0;
		int caughtFish = 0;
		size = 2;
		
		while (true) {
			Fish nextFish = bfs();
			if (nextFish == null)
				return totalTime;

			totalTime += nextFish.dist;
			cr = nextFish.r;
			cc = nextFish.c;
			map[cr][cc] = 0;
			
			if (++caughtFish == size) {
				caughtFish = 0;
				size++;
			}
		}
	}

	static Fish bfs() {
		PriorityQueue<Fish> pq = new PriorityQueue<>();
		pq.add(new Fish(cr, cc, 0));
		visited = new boolean[N][N];
		visited[cr][cc] = true;

		while (!pq.isEmpty()) {
			Fish curFish = pq.poll();
			if (map[curFish.r][curFish.c] > 0 && map[curFish.r][curFish.c] < size) {
				return curFish;
			}

			for (int i = 0; i < 4; i++) {
				int nr = curFish.r + dr[i];
				int nc = curFish.c + dc[i];

				if (0 <= nr && nr < N && 0 <= nc && nc < N && !visited[nr][nc] && map[nr][nc] <= size) {
					visited[nr][nc] = true;
					pq.add(new Fish(nr, nc, curFish.dist + 1));
				}
			}
		}

		return null;
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