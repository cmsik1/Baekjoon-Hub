import java.io.*;
import java.util.*;

public class Main {

	static int N, M, count = 0;
	static boolean[] isTarget;
	static List<List<Integer>> adjList;
	static StringBuilder sb = new StringBuilder();

	static void dfs(int curr, int prev) {
		sb.append(curr + " ");
		count++;

		for (Integer next : adjList.get(curr)) {
			if (next == prev || !isTarget[next])
				continue;
			dfs(next, curr);
			sb.append(curr + " ");
			count++;
		}
	}

	static boolean setTarget(int curr, int prev) {
		boolean isT = isTarget[curr];

		for (Integer next : adjList.get(curr)) {
			if (next == prev)
				continue;
			if (setTarget(next, curr))
				isT = true;
		}

		return isTarget[curr] = isT;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		isTarget = new boolean[N];
		adjList = new ArrayList<>();
		for (int i = 0; i < N; i++)
			adjList.add(new ArrayList<Integer>());

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++)
			isTarget[Integer.parseInt(st.nextToken())] = true;

		for (int i = 0; i < N - 1; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			adjList.get(from).add(to);
			adjList.get(to).add(from);
		}

		setTarget(0, 0);
		dfs(0, 0);
		System.out.println(count - 1);
		System.out.println(sb.toString());
	}
}
