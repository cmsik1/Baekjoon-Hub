import java.util.*;
import java.io.*;

public class Main {

	static int N;
	static int[] energy, first, second;
	static int[][] dp;

	static int dfs(int curr, int prev) {
		if (first[curr] == prev && second[curr] == 0) {
			return curr;
		}

		if (first[curr] == prev) {
			if (dp[curr][1] != 0) {
				return dp[curr][1];
			}
			return dp[curr][1] = dfs(second[curr], curr);
		} else {
			if (dp[curr][0] != 0) {
				return dp[curr][0];
			}
			return dp[curr][0] = dfs(first[curr], curr);
		}
	}

	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		energy = new int[N + 1];
		first = new int[N + 1];
		second = new int[N + 1];
		dp = new int[N + 1][2];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			energy[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			if (energy[to] > energy[first[from]]) {
				second[from] = first[from];
				first[from] = to;
			} else if (energy[to] > energy[second[from]]) {
				second[from] = to;
			}

			if (energy[from] > energy[first[to]]) {
				second[to] = first[to];
				first[to] = from;
			} else if (energy[from] > energy[second[to]]) {
				second[to] = from;
			}
		}

		for (int i = 1; i <= N; i++) {
			sb.append(dfs(i, i) + "\n");
		}
		System.out.println(sb.toString());

//		System.out.println(Arrays.toString(first));
//		System.out.println(Arrays.toString(second));
//		System.out.println(Arrays.toString(energy));
//		for (int i = 1; i <= N; i++) {
//			System.out.println(Arrays.toString(dp[i]));
//		}
		
	}
}