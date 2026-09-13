import java.util.HashMap;
import java.util.Map;

public class MajorityElement {
    public static void main(String[] args) {
        int[] nums = {8,8,7,7,7};
        System.out.println("Majority: "+mooresAlgo(nums) );
    }
    public static int majorityElement(int[] nums){
        int element = -1,maxFreq=0;
        Map<Integer, Integer> map = new HashMap<>();

        for(int i=0;i<nums.length; i++){
            if(map.containsKey(nums[i])){
                map.put(nums[i], map.get(nums[i])+1);
                if(map.get(nums[i])+1 > maxFreq){
                    maxFreq = map.get(nums[i])+1;
                    element = nums[i];
                }
            }else{
                map.put(nums[i], 1);
            }
        }
        return element;
    }

    static int mooresAlgo(int[] nums){
        int element=nums[0];
        int count=1;
        for(int i=1; i<nums.length; i++){
            if(nums[i] == element){
                count++;
            }else{
                count--;
            }
            if(count == 0){
                element = nums[i];
                count=1;
            }
        }
        System.out.println("element: "+element);
        int freq = 0;
        for(int i=0; i< nums.length; i++){
            if(nums[i] == element){
                freq++;
            }
        }
        if(freq <= nums.length/2 ){
            return -1;
        }

        return element;
    }

}
