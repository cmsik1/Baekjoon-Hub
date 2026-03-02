import java.util.*;
import java.io.*;

public class Main {

    static int N, M, K;
    static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int[][] nutrientInfo; // 추가 양분 정보
    static int[][] nutrientMap; // 양분 상태 맵
    static List<int[]> spreadTree; // 가을에 번식할 나무 리스트
    static PriorityQueue<int[]> treeInfo = new PriorityQueue<>((a, b) -> a[0] - b[0]); // 전체 나무 저장[나이, r, c, 생존여부]

    static int countAliveTree() {
        for (int year = 0; year < K; year++) {
            // 봄
            List<int[]> temp = new ArrayList<>();
            while (!treeInfo.isEmpty()){
                int[] tree = treeInfo.poll();
                int age = tree[0];
                int r = tree[1];
                int c = tree[2];

                if (age <= nutrientMap[r][c]) {
                    nutrientMap[r][c] -= age++;
                    temp.add(new int[]{age, r, c, 1});
                } else {
                    temp.add(new int[]{age, r, c, 0});
                }
            }

            // 여름 + 봄에 업데이트된 트리 다시 우선순위큐에 넣기
            for (int t = 0; t < temp.size(); t++) {
                int[] tree = temp.get(t);
                if (tree[3] == 1) {
                    treeInfo.add(tree);
                } else {
                    nutrientMap[tree[1]][tree[2]] += tree[0] / 2;
                }
            }

            // 가을
            temp = new ArrayList<>();
            for (int[] tree : treeInfo) {
                int age = tree[0];
                int r = tree[1];
                int c = tree[2];

                if (age % 5 == 0) {
                    for (int i = 0; i < 8; i++) {
                        int nr = r + dr[i];
                        int nc = c + dc[i];

                        if (0 <= nr && nr < N && 0 <= nc && nc < N) {
                            temp.add(new int[]{1, nr, nc, 1});
                        }
                    }
                }
            }
            treeInfo.addAll(temp);

            // 겨울
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    nutrientMap[r][c] += nutrientInfo[r][c];
                }
            }
        }

        return treeInfo.size();
    }

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        nutrientMap = new int[N][N];
        nutrientInfo = new int[N][N];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                nutrientInfo[r][c] = Integer.parseInt(st.nextToken());
                nutrientMap[r][c] = 5;
            }
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int age = Integer.parseInt(st.nextToken());

            treeInfo.add(new int[]{age, x, y, 1});
        }

        System.out.println(sb.append(countAliveTree()));
    }

}

