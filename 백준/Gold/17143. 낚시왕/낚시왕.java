import java.util.*;
import java.io.*;

public class Main {

    static int R, C, M;
    static int[] dr = {0, -1, 1, 0, 0};
    static int[] dc = {0, 0, 0, 1, -1};
    static int[] revDir = {0, 2, 1, 4, 3};
    static Shark[] sharks;
    static Shark[][] map;

    static int catchShark() {
        int count = 0;

        for (int fisher = 0; fisher < C; fisher++) {
            for (int r = 0; r < R; r++) {
                if (map[r][fisher] != null) {
                    count += map[r][fisher].size;
                    map[r][fisher].isAlive = false;
                    map[r][fisher] = null;
                    break;
                }
            }

            for (int s = 0; s < M; s++) {
                if (sharks[s].isAlive) sharks[s].move();
            }

            for (int s = 0; s < M; s++) {
                if (sharks[s].isAlive) {
                    if (map[sharks[s].row][sharks[s].col] == null) {
                        map[sharks[s].row][sharks[s].col] = sharks[s];
                    } else {
                        if (map[sharks[s].row][sharks[s].col].size > sharks[s].size) {
                            sharks[s].isAlive = false;
                        } else {
                            map[sharks[s].row][sharks[s].col].isAlive = false;
                            map[sharks[s].row][sharks[s].col] = sharks[s];
                        }
                    }
                }
            }
        }

        return count;
    }


    static class Shark {
        int row, col, speed, dir, size;
        boolean isAlive;

        Shark(int row, int col, int speed, int dir, int size, boolean isAlive) {
            this.row = row;
            this.col = col;
            this.speed = speed;
            this.dir = dir;
            this.size = size;
            this.isAlive = isAlive;
        }

        void move() {
            map[row][col] = null;
            int totalMove = 0;

            if (dir == 1 || dir == 2) totalMove = speed % ((R - 1) * 2);
            else totalMove = speed % ((C - 1) * 2);

            for (int i = 0; i < totalMove; i++) {
                int nr = row + dr[dir];
                int nc = col + dc[dir];

                if (nr < 0 || nr >= R || nc < 0 || nc >= C) dir = revDir[dir];

                row += dr[dir];
                col += dc[dir];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new Shark[R][C];
        sharks = new Shark[M];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());

            sharks[i] = new Shark(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1,
                Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), true);
            map[sharks[i].row][sharks[i].col] = sharks[i];
        }

        System.out.println(sb.append(catchShark()).toString());
    }

}

