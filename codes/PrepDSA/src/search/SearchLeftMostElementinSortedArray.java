package search;

public class SearchLeftMostElementinSortedArray {
    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 3, 4, 5, 5, 6, 7};
        System.out.println("left: "+leftInd(nums,5));
    }
    public static int leftInd(int[] nums, int k){
        int index=-1;
        int start=0,end=nums.length-1;
        int mid = (start+end)/2;
        while(start <= end ){
            if(nums[mid] == k ){
                index = mid;
                end = mid-1;
            }else if(k < nums[mid]){
                end = mid-1;
            }else{
                start = mid+1;
            }
            mid = (start+end)/2;
        }
        return index;
    }
}

