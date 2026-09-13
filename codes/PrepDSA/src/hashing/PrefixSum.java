package hashing;

import java.util.HashSet;

public class PrefixSum {
    public static void main(String[] args) {
        int[] arr = {-3,4,-3,-1,1};
        System.out.println("sum zero: "+prefixSumZero(arr,8));
    }

    public static boolean prefixSumK(int[] arr, int k){
        int sum=0;
        HashSet<Integer> set = new HashSet<>();
        for(int i=0; i<arr.length; i++){
            sum += arr[i];
            if(set.contains(sum-k)){
                return true;
            }else {
                set.add(sum);
            }
        }
        return false;
    }


    public static boolean prefixSumZero(int[] arr, int k){
        int sum=0;
        HashSet<Integer> set = new HashSet<>();
        for(int i=0; i<arr.length; i++){
            sum += arr[i];
            if(set.contains(sum)){
                return true;
            }else {
                set.add(sum);
            }
        }
        return false;
    }


}
