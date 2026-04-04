import java.util.*;
import java.io.*;

public class Main {

    static int N, count = 0;
    static boolean[] column, slash, bslash;

    static void setQueen(int r) {
        if (r == N) {
            count++;
            return;
        }

        for (int c = 0; c < N; c++) {
            if (!column[c] && !slash[r + c] && !bslash[r - c + N - 1]) {
                column[c] = true;
                slash[r + c] = true;
                bslash[r - c + N - 1] = true;
                setQueen(r + 1);
                column[c] = false;
                slash[r + c] = false;
                bslash[r - c + N - 1] = false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        column = new boolean[N];
        slash = new boolean[2 * N - 1];
        bslash = new boolean[2 * N - 1];

        setQueen(0);
        System.out.println(count);
    }
}
