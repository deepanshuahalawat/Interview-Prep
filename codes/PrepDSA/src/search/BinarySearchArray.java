package search;

public class BinarySearchArray {
    public static void main(String[] args) {
        int[] nums = {1,2,4,5,6,8,9};
        System.out.println("index: "+bsa(nums,9));
    }
    public static int bsa(int[] nums, int k){
        int res = -1;
        int low = 0, high = nums.length-1;
        int mid=(low+high)/2;
        while(low <= high){
            if(k < nums[mid]){
                high = mid-1;
            }else if (k > nums[mid]){
                low = mid+1;
            }else{
                return mid;
            }

            mid = (low+high)/2;
        }
        return res;
    }
}
