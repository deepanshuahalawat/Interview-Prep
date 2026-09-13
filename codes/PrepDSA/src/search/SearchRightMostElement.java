package search;

public class SearchRightMostElement {
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 3, 4, 5, 5, 6, 7};
        System.out.println("Index: "+getRightMost(nums,5));
    }
    static int getRightMost(int[] nums, int k){
        int index = -1;
        int start=0, end = nums.length-1;
        int mid = (start+end)/2;
        while(start <= end){
            if(nums[mid] == k){
                index = mid;
                if(mid == (nums.length-1) ){
                    return mid;
                }else{
                    start = mid+1;
                }
            }else if(k > nums[mid]){
                start = mid+1;
            }else{
                end = mid-1;
            }
            mid = (start+end)/2;
        }
        return index;
    }
}
