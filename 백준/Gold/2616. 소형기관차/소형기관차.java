import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n + 1];
		int[][] dp = new int[n + 1][4];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			arr[i] += arr[i - 1];
		}

		int k = Integer.parseInt(br.readLine());
		for (int i = k; i <= n; i++) {
			for (int j = 1; j <= 3; j++) {
				dp[i][j] = Math.max(dp[i - k][j - 1] + arr[i] - arr[i - k], dp[i - 1][j]);
			}
		}

		System.out.println(dp[n][3]);
	}
}
