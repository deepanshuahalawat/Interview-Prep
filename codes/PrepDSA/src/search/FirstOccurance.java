package search;

public class FirstOccurance {


    public static void main(String[] args) {
        int[] nums = {5, 10, 10, 10, 10, 20, 20};
        System.out.println("Index = "+lastOccurance(nums,10));
    }
    static int lastOccurance(int[] nums, int element){
        int low = 0;
        int high = nums.length-1;
        while(low <= high){
            int mid = (low+high)/2;
            if(element < nums[mid]){
                high = mid;
            }else if(element > nums[mid]){
                low = mid;
            }else {
                if(mid == nums.length-1 || nums[mid] != nums[mid+1]){
                    return mid;
                }else{
                    low = mid+1;
                }

            }
        }
        return -1;
    }

    static int firstOccurance(int[] nums, int element){
        int low = 0;
        int high = nums.length-1;
        int pos=-1;
        while(low <= high){
            int mid = (low+high)/2;
            if(nums[mid] > element){
                high = mid;
            } else if (nums[mid] < element) {
                low = mid;
            }else{
                if(nums[mid] == 0 || nums[mid-1] != nums[mid]){
                    return mid;
                }else{
                    high = mid-1;
                }
            }
        }
        return pos;
    }
}
