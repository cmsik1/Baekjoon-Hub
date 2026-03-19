import java.util.*;
import java.io.*;

public class Main {

	static StringBuilder sb = new StringBuilder();

	static void combination(int status, int idx, int N, int M, String result) {
		if (Integer.bitCount(status) == M) {
			sb.append(result + "\n");
			return;
		}

		for (int i = idx; i <= N; i++) {
			if ((status & (1 << i)) == 0) {
				combination(status | (1 << i), i + 1, N, M, result + i + " ");
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		combination(0, 1, N, M, "");
		System.out.println(sb.toString());
	}

}
