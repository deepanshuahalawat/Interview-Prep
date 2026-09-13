package matrix;

import java.util.concurrent.ThreadLocalRandom;

public class MatrixUtil {
    public static void printMatrix(int[][] arr){
        for (int row = 0; row < arr.length; row++){
            for(int col=0; col< arr[row].length; col++){
                System.out.print(arr[row][col]+"\t");
            }
            System.out.println("");
        }
    }

    public static int[][] generateRandomMatrix(int rows, int cols, int maxVal) {
        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = ThreadLocalRandom.current().nextInt(maxVal+1); // 0 to 100 inclusive
            }
        }

        return matrix;
    }

    public static void swap(int[][] arr, int i1, int j1, int i2, int j2) {
        int temp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = temp;
    }
}
