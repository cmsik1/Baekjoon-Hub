import java.util.*;
import java.io.*;

public class Main {

    static int minDistance = Integer.MAX_VALUE;
    static List<int[]> homes = new ArrayList<>();
    static List<int[]> chickenShops = new ArrayList<>();

    static void chickenCombination(int status, int idx, int count, int target) {
        if (count == target) {
            minDistance = Math.min(minDistance, getDistance(status));
            return;
        }

        for (int i = idx; i < chickenShops.size(); i++) {
            chickenCombination(status | (1 << i), i + 1, count + 1, target);
        }
    }

    static int getDistance(int status) {
        int totalDistance = 0;

        for (int i = 0; i < homes.size(); i++) {
            int[] home = homes.get(i);
            int hr = home[0];
            int hc = home[1];
            int dist = Integer.MAX_VALUE;

            for (int j = 0; j < chickenShops.size(); j++) {
                if ((status & (1 << j)) != 0) {
                    int[] chickenShop = chickenShops.get(j);
                    int cr = chickenShop[0];
                    int cc = chickenShop[1];

                    dist = Math.min(dist, Math.abs(hr - cr) + Math.abs(hc - cc));
                }
            }

            totalDistance += dist;
        }

        return totalDistance;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                int value = Integer.parseInt(st.nextToken());
                if (value == 1) {
                    homes.add(new int[]{r, c});
                } else if (value == 2) {
                    chickenShops.add(new int[]{r, c});
                }
            }
        }

        for (int i = 1; i <= M; i++) {
            chickenCombination(0, 0, 0, i);
        }
        System.out.println(sb.append(minDistance).toString());
    }

}

