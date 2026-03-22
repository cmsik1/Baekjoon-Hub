import java.util.*;
import java.io.*;

public class Main {

    static int getValue(String input) {
        ArrayDeque<String> stack = new ArrayDeque<>();
        int totalSum = 0;

        for (int i = 0; i < input.length(); i++) {
            String value = String.valueOf(input.charAt(i));

            if (value.equals("(") || value.equals("[")) {
                stack.add(value);
            } else {
                int sum = 0;
                while (true) {
                    if (stack.isEmpty() || (value.equals(")") && stack.peekLast().equals("["))
                        || (value.equals("]") && stack.peekLast().equals("("))) {
                        return 0;
                    }

                    if ((value.equals(")") && stack.peekLast().equals("(")) || (value.equals("]") && stack.peekLast().equals("["))) {
                        stack.pollLast();
                        if (stack.isEmpty()) {
                            if (sum == 0) {
                                if (value.equals(")")){
                                    totalSum += 2;
                                } else {
                                    totalSum += 3;
                                }
                            } else {
                                if (value.equals(")")) {
                                    totalSum += sum * 2;
                                } else {
                                    totalSum += sum * 3;
                                }
                            }
                        }
                        else {
                            if (sum == 0) {
                                if (value.equals(")")){
                                    stack.add(String.valueOf(2));
                                } else {
                                    stack.add(String.valueOf(3));
                                }
                            } else {
                                if (value.equals(")")) {
                                    stack.add(String.valueOf(2 * sum));
                                } else {
                                    stack.add(String.valueOf(3 * sum));
                                }
                            }
                        }
                        break;
                    } else {
                        sum += Integer.parseInt(stack.pollLast());
                    }
                }
            }
        }

        return totalSum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        System.out.println(getValue(input));
    }
}
