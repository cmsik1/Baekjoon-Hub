import java.util.*;
import java.io.*;

public class Main {

	static int R, C, M;
	static int[][] map, sharks;

	static int getTotalSum() {
		int totalSum = 0;

		for (int c = 0; c < C; c++) {
			for (int r = 0; r < R; r++) {
				if (map[r][c] != 0) {
					totalSum += sharks[map[r][c]][4];
					sharks[map[r][c]][5] = 0;
					map[r][c] = 0;
					break;
				}
			}

			for (int i = 1; i <= M; i++) {
				int[] shark = sharks[i];
				if (shark[5] == 1) { // 살아있는 상어이면
					map[shark[0]][shark[1]] = 0;

					boolean isIncrease = (shark[3] == 2 || shark[3] == 3) ? true : false;
					boolean isRow = (shark[3] == 1 || shark[3] == 2) ? false : true;
					int[] newPos = moveShark(shark[0], shark[1], shark[2], isIncrease, isRow);
					shark[0] = newPos[0];
					shark[1] = newPos[1];
					shark[3] = newPos[2];
				}
			}

			for (int i = 1; i <= M; i++) {
				int[] shark = sharks[i];
				if (shark[5] == 1) {
					if (map[shark[0]][shark[1]] == 0) {
						map[shark[0]][shark[1]] = i;
					} else if (sharks[map[shark[0]][shark[1]]][4] < shark[4]) {
						sharks[map[shark[0]][shark[1]]][5] = 0;
						map[shark[0]][shark[1]] = i;
					} else {
						sharks[i][5] = 0;
					}
				}
			}
		}

		return totalSum;
	}

	/*
	 * 현재 좌표, 행 or 열 길이(lastIdx), 증감여부, 행/열여부 이동 이후 좌표만 반환
	 */
	static int[] moveShark(int cr, int cc, int speed, boolean isIncrease, boolean isRow) {
		int flag = 0;
		int initMove = 0;
		if (isRow) {
			initMove = (isIncrease) ? (C - 1) - cc : cc;
		} else {
			initMove = (isIncrease) ? (R - 1) - cr : cr;
		}

		if (speed <= initMove) {
			if (isRow) {
				cc = (isIncrease) ? cc + speed : cc - speed;
			} else {
				cr = (isIncrease) ? cr + speed : cr - speed;
			}
			flag = 1;
		}

		if (flag == 0) {
			speed -= initMove;
			if (isRow) {
				if ((speed / (C - 1)) % 2 == 0) {
					isIncrease = (isIncrease) ? false : true;
				}
				cc = (isIncrease) ? (speed % (C - 1)) : (C - 1) - (speed % (C - 1));
			} else {
				if ((speed / (R - 1)) % 2 == 0) {
					isIncrease = (isIncrease) ? false : true;
				}
				cr = (isIncrease) ? (speed % (R - 1)) : (R - 1) - (speed % (R - 1));
			}
		}

		int dir = 0;
		if (!isRow) {
			dir = (isIncrease) ? 2 : 1;
		} else {
			dir = (isIncrease) ? 3 : 4;
		}

		return new int[] { cr, cc, dir };
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		sharks = new int[M + 1][6];

		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());

			sharks[i][0] = Integer.parseInt(st.nextToken()) - 1;
			sharks[i][1] = Integer.parseInt(st.nextToken()) - 1;
			sharks[i][2] = Integer.parseInt(st.nextToken());
			sharks[i][3] = Integer.parseInt(st.nextToken());
			sharks[i][4] = Integer.parseInt(st.nextToken());
			sharks[i][5] = 1;

			map[sharks[i][0]][sharks[i][1]] = i;
		}

		System.out.println(new StringBuilder().append(getTotalSum()));
	}

}