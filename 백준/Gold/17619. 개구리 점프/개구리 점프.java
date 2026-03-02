import java.util.*;
import java.io.*;

public class Main {

	static int N;
	static int[] group, originNum;
	static int[][] woods;

	static void makeGroup() {
		int head = woods[1][0];
		int tail = woods[1][1];
		int groupNum = 1;

		for (int i = 1; i <= N; i++) {
			originNum[woods[i][2]] = i;
			if (tail < woods[i][0]) {
				head = woods[i][0];
				tail = woods[i][1];
				woods[i][3] = ++groupNum;
			} else {
				tail = Math.max(tail, woods[i][1]);
				woods[i][3] = groupNum;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());
		woods = new int[N + 1][4];
		group = new int[N + 1];
		originNum = new int[N + 1];

		for (int n = 1; n <= N; n++) {
			st = new StringTokenizer(br.readLine());

			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			st.nextToken();

			woods[n] = new int[] { x, y, n, 0 };
		}

		Arrays.sort(woods, (a, b) -> a[0] - b[0]);
		makeGroup();
		
		for (int q = 0; q < Q; q++) {
			st = new StringTokenizer(br.readLine());
			int w1 = Integer.parseInt(st.nextToken());
			int w2 = Integer.parseInt(st.nextToken());
			
			if (woods[originNum[w1]][3] != woods[originNum[w2]][3]) {
				System.out.println(0);
			} else {
				System.out.println(1);
			}
		}
	}
	
}


