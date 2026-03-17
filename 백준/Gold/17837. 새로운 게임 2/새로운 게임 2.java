import java.util.*;
import java.io.*;

public class Main {

	static int N, K;
	static int[] dr = { 0, 0, 0, -1, 1 };
	static int[] dc = { 0, 1, -1, 0, 0 };
	static int[] reversedDir = { 0, 2, 1, 4, 3 };
	static int[][] map; // 칸 정보 저장
	static String[][] pieceMap; // 말의 현재 위치를 map에 저장
	static List<int[]> pieceInfo; // 말의 현재 위치, 방향 저장

	static int getEndTurn() {
		int turn = 1;

		while (turn <= 1000) {
			for (int i = 0; i < K; i++) {
				int r = pieceInfo.get(i)[0];
				int c = pieceInfo.get(i)[1];
				int dir = pieceInfo.get(i)[2];

				int nr = r + dr[dir];
				int nc = c + dc[dir];

				if (nr < 0 || nr >= N || nc < 0 || nc >= N || map[nr][nc] == 2) {
					pieceInfo.get(i)[2] = reversedDir[dir];

					nr = r + dr[pieceInfo.get(i)[2]];
					nc = c + dc[pieceInfo.get(i)[2]];
					if (nr < 0 || nr >= N || nc < 0 || nc >= N || map[nr][nc] == 2)
						continue;
 
					if (map[nr][nc] == 0) {
						moveWBlock(nr, nc, detach(i));
					} else {
						moveRBlock(nr, nc, detach(i));
					}
				} else if (map[nr][nc] == 0) {
					moveWBlock(nr, nc, detach(i));
				} else {
					moveRBlock(nr, nc, detach(i));					
				}
				if (isGameEnd())
					return turn;
			}

			turn++;
		}

		return -1;
	}

	static void moveWBlock(int r, int c, String detachNum) {
		if (pieceMap[r][c] == null) {
			pieceMap[r][c] = detachNum;
		} else {
			pieceMap[r][c] = new StringBuilder(pieceMap[r][c]).append(detachNum).toString();
		}

		for (int i = 0; i < detachNum.length(); i++) {
			int num = detachNum.charAt(i) - '0';
			pieceInfo.get(num)[0] = r;
			pieceInfo.get(num)[1] = c;
		}
	}

	static void moveRBlock(int r, int c, String detachNum) {
		moveWBlock(r, c, new StringBuilder(detachNum).reverse().toString());
	}

	static String detach(int currNum) { // 현재 있는 칸에서 분리
		int[] info = pieceInfo.get(currNum);
		int cr = info[0];
		int cc = info[1];
		int dir = info[2];

		String currMapNum = pieceMap[cr][cc];
		String detachNum = null;
		int idx = currMapNum.indexOf(String.valueOf(currNum));
		if (idx == 0) {
			detachNum = currMapNum.substring(idx);
			pieceMap[cr][cc] = null;
		} else {
			detachNum = currMapNum.substring(idx);
			pieceMap[cr][cc] = currMapNum.substring(0, idx);
		}

		return detachNum;
	}

	static boolean isGameEnd() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if (pieceMap[r][c] != null && pieceMap[r][c].length() >= 4)
					return true;
			}
		}
		
		return false;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		pieceInfo = new ArrayList<>();
		pieceMap = new String[N][N];

		for (int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int dir = Integer.parseInt(st.nextToken());

			pieceMap[r][c] = String.valueOf(i);
			pieceInfo.add(new int[] { r, c, dir });
		}

		System.out.println(sb.append(getEndTurn()).toString());
	}

}
