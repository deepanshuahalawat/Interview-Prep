package string;

import java.util.ArrayList;
import java.util.List;

public class LexiGraphicalStrins {
    public static void main(String[] args) {
        int[] nums = {1,1};
        List<List<Integer>> ans = new Solution().subsetsWithDup(nums);
        for(List<Integer> set: ans){
            System.out.println(set);
        }
    }
}
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new  ArrayList<>();
        ArrayList<Integer> oneSet = new ArrayList<>();
        set(nums, 0, oneSet, ans);
        return ans;
    }

    void set(int[] nums, int index, ArrayList<Integer> oneSet, List<List<Integer>> ans){
        if(index == nums.length){
            ans.add(new ArrayList<>(oneSet));
            return;
        }

            set(nums, index+1, oneSet, ans);
            oneSet.add(nums[index]);
            set(nums, index+1, oneSet, ans);
            oneSet.remove(oneSet.size()-1);


    }
}
