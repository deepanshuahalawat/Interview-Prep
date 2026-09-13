import java.util.Arrays;

public class SubarraySumK {

    public static void main(String[] args) {
        int[] nums = {-1,-1,1};
        //            0,1,2,3,4
        System.out.println("Output:"+ getSubarraysNo(nums,0));
    }

    public static int getSubarraysNo(int[] nums, int k){
        int count=0;
        //Arrays.sort(nums);
        int start=0,end = start;
        int sum=nums[start];
        while(start<nums.length && end < nums.length){
            if(sum > k){
                sum -= nums[start];
                start++;
            }else {
                if(sum == k) {
                    count++;
                    System.out.println("start: "+start+"\tend:"+end);
                }
                end++;
                if(end < nums.length){
                    sum += nums[end];
                }
            }


        }

        return count;
    }
}
