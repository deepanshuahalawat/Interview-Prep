public class AllPermutationRecursive {
    public static void main(String[] args) {
        char arr[] = {'a', 'b', 'c'};
        per(arr,0);
    }
    public static void per(char arr[], int n){
        if(n == arr.length){
            for(char ch: arr){
                System.out.print(ch+" ");
            }
            System.out.println("");
        }
        for(int i=n; i< arr.length; i++){
            swap(arr, i,n);
            per(arr, n+1);
            swap(arr, i,n);
        }
    }

    public static void swap(char arr[], int from ,int to){
        char temp = arr[from];
        arr[from] = arr[to];
        arr[to] = temp;
    }
}
