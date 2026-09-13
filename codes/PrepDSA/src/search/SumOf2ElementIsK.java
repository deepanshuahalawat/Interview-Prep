package search;

public class SumOf2ElementIsK {
    public static void main(String[] args) {
        int[] arr = {1,3,4,5,7,8,9};
        System.out.println("Ans: "+sum(arr,11));

    }
    public static boolean sum(int[] arr, int k){
        int left=0, right = arr.length-1;
        int sum = arr[left] + arr[right];
        while(left < right){
            if(sum < k){
                left++;
            }else if(sum > k){
                right--;
            }else {
                System.out.println("left: "+arr[left]+"\t, right"+arr[right]);
                return true;
            }
            sum = arr[left] + arr[right];
        }



        return false;
    }
}
