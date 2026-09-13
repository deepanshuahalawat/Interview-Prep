import java.util.LinkedList;
import java.util.List;

public class maxSumSubArray {

    public static void main(String[] args) {
        List<Integer> nums = List.of(-5,1,-2,3,-1,2,-2);
        System.out.println("Max sum: "+maxSumSubArray(nums));
    }
    static int maxSumSubArray(List<Integer> nums){
        int maxSum=nums.get(0);
        int lastSum  = nums.get(0);;
        for(int i=1; i < nums.size(); i++){
            System.out.println(lastSum+nums.get(i)+", "+nums.get(i)+", "+maxSum);
            maxSum = Math.max(lastSum+nums.get(i), nums.get(i));
            if(maxSum > lastSum)
                lastSum = maxSum;
        }
        return maxSum;
    }
}
