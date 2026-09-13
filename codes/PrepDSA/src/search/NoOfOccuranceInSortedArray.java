package search;

public class NoOfOccuranceInSortedArray {
    public static void main(String[] args) {
        int[] arr = {1,1,1,1,1,1,2,2,2,3,3,3,3,3,4,4,4,4};
        System.out.println("count: "+countoccurance(arr, 1));
    }
    public static int countoccurance(int[] nums, int k){
        int count=0;
        int startIndex = SearchLeftMostElementinSortedArray.leftInd(nums,k);
        int endIndex = SearchRightMostElement.getRightMost(nums,k);
        count = endIndex - startIndex +1;
        return count;
    }

}
