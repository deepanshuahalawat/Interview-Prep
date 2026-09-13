package pAndC;

import java.util.ArrayList;
import java.util.List;

public class Subset {
    public static void main(String[] args) {
        int[] nums = {100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,97};
        int sum=0;
        for(int i=0; i< nums.length; i++){
            sum += nums[i];
        }
        List<Integer> list = new ArrayList<>();
        System.out.println("Running...");
        long  start = System.currentTimeMillis();
        //boolean res = checkSub(nums, list, 0, sum);
        boolean res = checkSubIt(nums, sum);
        System.out.println("Result: "+ (System.currentTimeMillis()-start));
    }
    static boolean checkSubIt(int[] nums, int sum){
        int n = nums.length;
        int maxNum = (int)Math.pow(2,n)-1;
        System.out.println("length: "+n);
        System.out.println("max num: "+maxNum);
        for(int i=0; i<= maxNum; i++){
            int currSum=0;
            for(int j=0; j<n; j++){
                if( (i&(1<<j)) != 0){
                    currSum += nums[j];
                }
            }
            if(currSum == sum/2){
                return true;
            }
        }
        return false;
    }

    static boolean checkSub(int[] nums, List<Integer> list, int n, int sum){
        if(n == nums.length){
            int currSum=0;
            //System.out.println(list);
            for(int i=0; i< list.size(); i++){
                currSum += list.get(i);
            }
            if(currSum == sum/2){
                System.out.println("\t---<Result\n\n");

                return true;
            }else{
                return false;
            }
        }


        boolean left = checkSub(nums,list, n+1, sum);
        list.addLast(nums[n]);
        boolean right = checkSub(nums,list, n+1, sum);
        list.removeLast();
        return left || right;



    }
}
