import java.util.ArrayList;
import java.util.List;

public class SubStringRecursion {
    public static void main(String[] args) {
        String str = "abc";
        sub(str);
    }
    public static void sub(List<Integer> nums){
        if(nums.size() == 0){
            return;
        }
        nums.forEach(num -> System.out.print(num+" "));
        System.out.println("");
        for(int i=0; i<nums.size(); i++){
            List<Integer> newList = new ArrayList<>(nums);
            newList.remove(i);
            sub(newList);
        }
    }
    public static void sub(String str){
        recSub(str, "", 0);
    }
    public static void recSub(String str, String curr, int n){
        if(n == str.length()){
            System.out.println("{"+curr+"}");
            return;
        }
        recSub(str, curr, n+1);
        recSub(str, curr+str.charAt(n), n+1);
    }


}
