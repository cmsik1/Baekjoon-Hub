import java.io.*;
import java.util.*;

public class Main {

	static int INF = Integer.MAX_VALUE;
	static int N, P, K;
	static int[][] groups;

	static long getWaitTime() {
		long totalTime = 0; 
		int time = 0, curr = 0, left = K;
		Queue<int[]>[] nQ = new Queue[K + 1];
		for (int i = 0; i <= K; i++) nQ[i] = new LinkedList<int[]>();
		
		while (true) {
			// 대기중인 그룹 먼저 태우기
			while (true) {
				int aTime = INF;
				int next = 0;
				for (int i = 1; i <= left; i++) {
					if (!nQ[i].isEmpty() && aTime > nQ[i].peek()[0]) {
						aTime = nQ[i].peek()[0];
						next = i;
					}
				}
				if (next == 0) break;
				left -= next;
				totalTime += time - nQ[next].poll()[0];
			}
			
			// 현재 시간대에 탑승 가능한 승객 탑승 or 대기큐 보내기
			while (curr < N && groups[curr][0] <= time) {
				if (left >= groups[curr][1]) {
					left -= groups[curr][1];
					totalTime += time - groups[curr++][0];
				} else {
					nQ[groups[curr][1]].add(groups[curr++]);
				}
			}
			
			if (curr >= N && !isWait(nQ)) break;
			
			// 놀이기구 보내고 시간 증가
			if (!isWait(nQ)) {
				if (groups[curr][0] % P == 0) {
					time += ((groups[curr][0] - time) / P) * P;
				} else {
					time += ((groups[curr][0] - time) / P + 1) * P;
				}
			} else {
				time += P;
			}
			
			left = K;
		}
		
		return totalTime;
	}

	static boolean isWait(Queue<int[]>[] nQ) {
		for (Queue<int[]> queue : nQ) {
			if (!queue.isEmpty()) return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		groups = new int[N][2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			groups[i][0] = Integer.parseInt(st.nextToken());
			groups[i][1] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(groups, (a, b) -> a[0] - b[0]);
		
		System.out.println(getWaitTime());
	}
}
