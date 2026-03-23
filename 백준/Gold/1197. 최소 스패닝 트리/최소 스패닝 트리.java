import java.util.*;
import java.io.*;

public class Main {

	static int V, E;
	static List<List<int[]>> adjInfo;

	static int prim(int startNode) {
		int count = 0;
		int totalWeight = 0;
		boolean[] visited = new boolean[V + 1];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.add(new int[] { startNode, 0 });

		while (!pq.isEmpty()) {
			int[] curr = pq.poll();

			if (visited[curr[0]]) continue;
			visited[curr[0]] = true;
			count++;
			totalWeight += curr[1];
			
			if (count == V) {
				break;
			}
			
			for (int[] adjNode : adjInfo.get(curr[0])) {
				if (!visited[adjNode[0]]) {
					pq.add(new int[] { adjNode[0], adjNode[1] });
				}
			}
		}

		return totalWeight;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		adjInfo = new ArrayList<>();
		for (int i = 0; i <= V; i++) {
			adjInfo.add(new ArrayList<>());
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			adjInfo.get(from).add(new int[] { to, weight });
			adjInfo.get(to).add(new int[] { from, weight });
		}

		System.out.println(prim(1));
	}

}
