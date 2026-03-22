import java.util.*;
import java.io.*;

public class Main {

    static int N;
    static int[] op;

    static String getInitCard() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            if (op[i] == 1) {
                dq.addFirst(i);
            } else if (op[i] == 2) {
                int temp = dq.pollFirst();
                dq.addFirst(i);
                dq.addFirst(temp);
            } else {
                dq.add(i);
            }
        }

        for (Integer i : dq) {
            sb.append(i + " ");
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        op = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = N; i > 0; i--) {
            op[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(getInitCard());
    }
}
