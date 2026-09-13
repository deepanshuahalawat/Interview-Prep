package matrix;

public class TransposeAMatrix {
    public static void main(String[] args) {
        //int[][] arr = MatrixUtil.generateRandomMatrix(3,3,10);
        int[][] arr = {
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        MatrixUtil.printMatrix(arr);
        System.out.println("");
        transpose(arr);
        MatrixUtil.printMatrix(arr);
        reverseColumns(arr);
        System.out.println("");
        MatrixUtil.printMatrix(arr);
    }
    public static void reverseColumns(int[][] arr){
        int n = arr.length;
        for(int j=0; j< arr.length; j++){//for each column
            for(int i=0; i<arr.length/2; i++){
                MatrixUtil.swap(arr, i,j, n-1-i,j);
            }
        }
    }
    public static void transpose(int[][] arr){
        int n = arr.length;
        for(int i=0; i< n; i++){
            for(int j=0; j<n; j++){
                if(i > j){
                    MatrixUtil.swap(arr, i,j, j,i);
                }
            }
        }
    }
}
