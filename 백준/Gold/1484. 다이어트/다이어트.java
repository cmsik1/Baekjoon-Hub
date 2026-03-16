import java.util.*;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		TreeSet<Integer> ts = new TreeSet<>();
		int G = Integer.parseInt(br.readLine());
		int diff = 1;
		while (diff <= G) {
			if (G % diff == 0 && (diff + G / diff) % 2 == 0) {
				int weight = (diff * diff + G) / (2 * diff);
				if (weight > diff)
					ts.add(weight);
			}
			diff++;
		}

		if (ts.isEmpty()) System.out.println(-1);
		else {
			while (!ts.isEmpty()) {
				sb.append(ts.pollFirst() + "\n");
			}
			System.out.println(sb.toString());	
		}
	}

}
