import java.util.*;
import java.io.*;

public class Main {

    static int N, minDiff = Integer.MAX_VALUE;
    static int[] S;
    static int[] B;

    static void getMinDiff(int status, int idx) {
        if (idx == N) {
            if (Integer.bitCount(status) == 0) return;
            int s = 1;
            int b = 0;
            for (int i = 0; i < N; i++) {
                if ((status & (1 << i)) != 0) {
                    s *= S[i];
                    b += B[i];
                }
            }
            minDiff = Math.min(minDiff, Math.abs(s - b));
            return;
        }

        getMinDiff(status | (1 << idx), idx + 1);
        getMinDiff(status, idx + 1);
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        S = new int[N];
        B = new int[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            S[i] = Integer.parseInt(st.nextToken());
            B[i] = Integer.parseInt(st.nextToken());
        }

        getMinDiff(0, 0);
        System.out.println(sb.append(minDiff).toString());
    }
}
