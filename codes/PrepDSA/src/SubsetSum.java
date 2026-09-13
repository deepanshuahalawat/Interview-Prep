import java.util.ArrayList;
import java.util.List;

public class SubsetSum {
    public static void main(String[] args) {
        int arr[] = {1,2,3};
        System.out.println("isSubset: "+isSubsetSum(arr,7));
        //sub(arr, new ArrayList<>(), 0);
    }
    static Boolean isSubsetSum(int arr[], int sum) {
        // code here
        return subSet(arr, sum, new ArrayList<>(), 0);
    }
    static Boolean subSet(int arr[], int sum, List<Integer> curr,  int n ){
        if(n == arr.length){
            System.out.print("{");
            curr.forEach(num -> System.out.print(num));
            System.out.println("}");
            return curr.stream().reduce(0, Integer::sum) == sum ;
        }
        boolean left = subSet(arr, sum, curr, n+1);
        curr.add(arr[n]);
        boolean right = subSet(arr, sum, curr, n+1);
        curr.remove(curr.size()-1);
        return left || right;

    }

    static void sub(int arr[],List<Integer> curr, int n){
        if(n==arr.length){
            System.out.print("{");
            curr.forEach(System.out::print);
            System.out.println("}");
            return ;
        }
        sub(arr, curr, n+1);
        curr.add(arr[n]);
        sub(arr, curr, n+1);
        curr.remove(curr.size()-1);
    }


}
