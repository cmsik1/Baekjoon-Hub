import java.util.*;
import java.io.*;

public class Main {

    static int bagCnt, jewelCnt;
    static int[] bag;
    static int[][] jewelry;

    static long getMaxCost() {
        long totalCost = 0;
        int currBag = 0;
        int currJewel = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (b[1] - a[1]));

        while (currBag < bagCnt) {
            while (currJewel < jewelCnt && bag[currBag] >= jewelry[currJewel][0]) {
                pq.add(jewelry[currJewel++]);
            }

            if (!pq.isEmpty()) {
                totalCost += pq.poll()[1];
            }
            currBag++;
        }

        return totalCost;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        jewelCnt = Integer.parseInt(st.nextToken());
        bagCnt = Integer.parseInt(st.nextToken());
        jewelry = new int[jewelCnt][2];
        bag = new int[bagCnt];

        for (int i = 0; i < jewelCnt; i++) {
            st = new StringTokenizer(br.readLine());
            jewelry[i][0] = Integer.parseInt(st.nextToken());
            jewelry[i][1] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < bagCnt; i++) {
            bag[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(jewelry, (a, b) -> a[0] - b[0]);
        Arrays.sort(bag);
        System.out.println(getMaxCost());
    }
}
