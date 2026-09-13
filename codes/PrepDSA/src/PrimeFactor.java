import java.util.*;
class Solution {
    public static void main(String arg[]){
        ArrayList<Integer> nums;
        nums = primeFac(35);
        nums.forEach(num -> System.out.print(num+" "));
    }
    public static ArrayList<Integer> primeFac(int n) {
        // code here
        ArrayList<Integer> nums = new ArrayList<>();
        if(n <= 1){
            nums.add(n);
            return nums;
        }
        for(int i=2; i*i<= n; i++){
            if(n%i == 0){
                nums.add(i);

                while(n%i == 0){
                    n = n/i;
                }
            }
            if(n == 1){
                break;
            }
        }
        if(n > 1)
            nums.add(n);
        return nums;
    }
}