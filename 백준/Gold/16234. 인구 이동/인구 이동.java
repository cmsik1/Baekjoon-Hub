import java.util.*;
import java.io.*;

public class Main {

	static int N, L, R;
	static int[] dr = { -1, 0, 1, 0 };
	static int[] dc = { 0, 1, 0, -1 };
	static int[][] map;
	static boolean[][] visited;

	static int simulation() {
		int turn = 0;

		while (true) {
			boolean isMoved = false;
			visited = new boolean[N][N];
			
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					if (!visited[r][c]) {
						ArrayDeque<int[]> dq = new ArrayDeque<>();
						List<int[]> group = new ArrayList<>();
						dq.add(new int[] { r, c });
						visited[r][c] = true;
						int count = 1;
						int sum = map[r][c];

						while (!dq.isEmpty()) {
							int[] pos = dq.poll();
							group.add(pos);
							int cr = pos[0];
							int cc = pos[1];							

							for (int i = 0; i < 4; i++) {
								int nr = cr + dr[i];
								int nc = cc + dc[i];

								if (0 <= nr && nr < N && 0 <= nc && nc < N && !visited[nr][nc]) {
									int diff = Math.abs(map[cr][cc] - map[nr][nc]);
									if (diff >= L && diff <= R) {
										isMoved = true;
										visited[nr][nc] = true;
										count++;
										sum += map[nr][nc];
										dq.add(new int[] { nr, nc });
									}
								}
							}
						}
						
						int value = sum / count;
						for (int[] pos : group) {
							int cr = pos[0];
							int cc = pos[1];

							map[cr][cc] = value;
						}
					}
				}
			}
			
			if (!isMoved) {
				break;
			}
			turn++;
		}
		

		return turn;
	}

	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		
		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(sb.append(simulation()).toString());
	}

}