import java.util.*;
import java.io.*;

public class Main {

    static int[] dr = {0, -1, 0, 1};
    static int[] dc = {1, 0, -1, 0};
    static int[][] map = new int[200][200];
    static List<int[]> dragon = new ArrayList<>();

    static void setDragonCurve(int x, int y, int g, String route) {
        if (route.length() == (1 << g)) {
            return;
        }

        StringBuilder newRoute = new StringBuilder(route);
        String reverseRoute = new StringBuilder(route).reverse().toString();

        for (int i = 0; i < reverseRoute.length(); i++) {
            int dir = reverseRoute.charAt(i) - '0';
            dir = (dir == 3) ? 0 : dir + 1;
            x += dr[dir];
            y += dc[dir];
            if (map[x][y] == 0) dragon.add(new int[]{x, y});
            map[x][y] = 1;
            newRoute.append(dir);
        }

        setDragonCurve(x, y, g, newRoute.toString());
    }

    static int countSquare() {
        int count = 0;

        for (int[] pos : dragon) {
            int x = pos[0];
            int y = pos[1];
            if (map[x + 1][y] == 1 && map[x][y + 1] == 1 && map[x + 1][y + 1] == 1) count++;
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            if (map[x][y] == 0) dragon.add(new int[]{x, y});
            if (map[x + dr[d]][y + dc[d]] == 0) dragon.add(new int[]{x + dr[d], y + dc[d]});
            map[x][y] = 1;
            map[x + dr[d]][y + dc[d]] = 1;

            setDragonCurve(x + dr[d], y + dc[d], g, String.valueOf(d));
        }

        System.out.println(countSquare());
    }

}

