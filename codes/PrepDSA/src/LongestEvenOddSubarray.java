import java.util.List;

public class LongestEvenOddSubarray {

    public static void main(String[] args) {
        List<Integer> nums = List.of(10,12,14,7,8);
        //System.out.println("Ans: "+longestArray(nums));
        int arr[] = {1,2};
        System.out.println("ans:"+longestAlternatingSubarray(arr,2));
    }
    static int longestArray(List<Integer> nums){
        int maxLen=1;
        int last = nums.get(0);
        for(int i=1; i<nums.size(); i++){
            if( (last + nums.get(i))%2 != 0 ){
                maxLen++;
            }else {
                maxLen=1;
            }
            last = nums.get(i);
        }

        return maxLen;
    }
    public static int longestAlternatingSubarray(int[] nums, int threshold) {
        int len = 1,maxLen=0;
        int k=0, i=0;
        while(k < nums.length ) {
            while(k < nums.length  && nums[k] %2 != 0){
                k++;
            }
            for (i = k; i < nums.length - 1; i++) {
                System.out.println(nums[i] + ", " + nums[i + 1]);
                System.out.print(
                        ((nums[i] % 2) != (nums[i + 1] % 2)) +
                        "\t" + (nums[i] <= threshold) + "\t");

                if (((nums[i] % 2) != (nums[i + 1] % 2)) && nums[i] <= threshold) {
                    len++;
                } else {
                    maxLen = len>maxLen?len:maxLen;
                    len = 1;
                }
                System.out.println("len:" + len);
            }
            k = i;
        }
        return len;
    }
}
