import java.io.*;
import java.util.*;

public class Main {

	static int N, M;
	static List<List<Integer>> up, down;

	static int solve() {
		int count = 0;

		for (int i = 0; i < N; i++) {
			if (getUpNode(i) + getDownNode(i) == N-1) {
				count++;
			}
		}

		return count;
	}

	static int getUpNode(int node) {
		int count = 0;
		boolean[] visited = new boolean[N];
		visited[node] = true;
		Queue<Integer> q = new LinkedList<>();
		q.add(node);

		while (!q.isEmpty()) {
			int curr = q.poll();

			for (Integer n : up.get(curr)) {
				if (visited[n]) continue; 
				visited[n] = true;
				q.add(n);
				count++;
			}
		}

		return count;
	}

	static int getDownNode(int node) {
		int count = 0;
		boolean[] visited = new boolean[N];
		visited[node] = true;
		Queue<Integer> q = new LinkedList<>();
		q.add(node);

		while (!q.isEmpty()) {
			int curr = q.poll();

			for (Integer n : down.get(curr)) {
				if (visited[n]) continue;
				visited[n] = true;
				q.add(n);
				count++;
			}
		}

		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		up = new ArrayList<>();
		down = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			up.add(new ArrayList<>());
			down.add(new ArrayList<>());
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;

			up.get(a).add(b);
			down.get(b).add(a);
		}

		System.out.println(solve());
	}
}
