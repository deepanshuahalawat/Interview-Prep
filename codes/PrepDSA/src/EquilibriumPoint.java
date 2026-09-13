public class EquilibriumPoint {
    public static void main(String[] args) {
        int nums[] = {1,7,3,6,5,6};
        System.out.println("Pivot: "+pivotIndex(nums));
    }
    public static int pivotIndex(int[] nums) {
        int left=0, right = nums.length-1;
        int leftSum=nums[left], rightSum=nums[right];
        while(left < right){
            if(leftSum < rightSum){
                left++;
                leftSum += nums[left];
            }else if(rightSum < leftSum){
                right--;
                rightSum += nums[right];
            }else{
                if(left+1 == right){
                    return left+1;
                }
                left++;
                leftSum += nums[left];
            }
        }
        return -1;
    }
}
