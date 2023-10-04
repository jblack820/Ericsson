package org.example;

public class Task2 {
    int solution2 (int X, int Y, int[] A) {
        int N = A.length;
        int result = -1;
        int nX = 0;
        int nY = 0;
        for (int i = 0; i < N; i++) {
            if (A[i] == X)
                nX += 1;
            else if (A[i] == Y)
                nY += 1;
            if (nX>=1 && nY >=1){
                if (nX==nY){result = i;}
                if (nX-nY==2 || nY-nX==2){
                    return result;
                }
            }

        }
        return result;
    }
}
