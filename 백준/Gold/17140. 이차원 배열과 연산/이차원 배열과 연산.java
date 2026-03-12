import java.util.*;
import java.io.*;

public class Main {

    static int R, C, K, rowLen, colLen;
    static int[][] map;

    static int getMinTime() {
        int time = 0;

        while (true) {
            rowLen = map.length; // 변경 전 행 길이
            colLen = map[0].length; // 변경 전 열 길이

            if (R < rowLen && C <colLen && map[R][C] == K)
                return time;
            if (time >= 100)
                return -1;

            if (rowLen >= colLen) { // 행 정렬
                List<List<Map.Entry<Integer, Integer>>> totalList = new ArrayList<>();
                int maxSize = 0; // 정렬된 리스트들의 최대 열 길이 저장

                for (int i = 0; i < rowLen; i++) {
                    List<Map.Entry<Integer, Integer>> list = sortArray(map[i]);
                    maxSize = Math.max(maxSize, list.size() * 2);
                    totalList.add(list); // 전체 리스트에 추가
                }

                map = new int[rowLen][maxSize];
                for (int r = 0; r < rowLen; r++) {
                    List<Map.Entry<Integer, Integer>> list = totalList.get(r);
                    int count = 0;
                    for (int i = 0; i < list.size(); i++) { // 정렬된 리스트에서 key, value 추출 후 map에 저장
                        map[r][count++] = list.get(i).getKey();
                        map[r][count++] = list.get(i).getValue();
                    }

                    while (count < maxSize) { // map의 남은 열 부분을 0으로 채우기
                        map[r][count++] = 0;
                    }
                }

            } else { // 열 정렬
                List<List<Map.Entry<Integer, Integer>>> totalList = new ArrayList<>();
                int maxSize = 0; // 정렬된 리스트들의 최대 행 길이 저장

                for (int i = 0; i < colLen; i++) {
                    List<Map.Entry<Integer, Integer>> list = sortArray(getColumn(i, map));
                    maxSize = Math.max(maxSize, list.size() * 2);
                    totalList.add(list); // 전체 리스트에 추가
                }

                map = new int[maxSize][colLen];
                for (int c = 0; c < colLen; c++) {
                    List<Map.Entry<Integer, Integer>> list = totalList.get(c);
                    int count = 0;
                    for (int i = 0; i < list.size(); i++) { // 정렬된 리스트에서 key, value 추출 후 map에 저장
                        map[count++][c] = list.get(i).getKey();
                        map[count++][c] = list.get(i).getValue();
                    }

                    while (count < maxSize) { // map의 남은 행 부분을 0으로 채우기
                        map[count++][c] = 0;
                    }
                }
            }

            time++;
        }
    }

    static int[] getColumn(int col, int[][] map) {
        int[] column = new int[rowLen];
        for (int row = 0; row < rowLen; row++) {
            column[row] = map[row][col];
        }
        return column;
    }

    static List<Map.Entry<Integer, Integer>> sortArray(int[] array) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0)
                hm.put(array[i], hm.getOrDefault(array[i], 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(hm.entrySet());
        list.sort((a, b) -> {
            if (a.getValue() != b.getValue())
                return a.getValue() - b.getValue();
            return a.getKey() - b.getKey();
        });

        return list;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken()) - 1;
        C = Integer.parseInt(st.nextToken()) - 1;
        K = Integer.parseInt(st.nextToken());
        map = new int[3][3];

        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(getMinTime());
    }

}