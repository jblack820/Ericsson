package org.example;


public class Main {

    public static void main(String[] args) {

        String[] baseTable = new String[]{
                "........",
                "X.......",
                "..X.....",
                ".A..X.<.",
                ".....X..",
                "...X....",
                "^.......",
                "...^.X^."
        };

        Solution solution = new Solution();
        System.out.println("\n" + solution.solution(baseTable) + "\n");
        Solution.printTable(solution.TABLE);
    }

}
