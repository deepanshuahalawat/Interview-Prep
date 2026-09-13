package pAndC;

import java.util.ArrayList;
import java.util.List;

public class SubsetOfString {
    public static void main(String[] args) {
        String st = "abc";
        List<String> ans =  allSubsets(st);
        System.out.println(ans);
    }
    public static List<String> allSubsets(String str){
        List<String> ans = new ArrayList<>();
        int n = str.length();
        int num = ((int)Math.pow(2,n)) - 1;
        System.out.println("num="+num+",\tn="+n);
        for(int i=0; i<= num; i++){
            StringBuilder sb = new StringBuilder("");
            for(int j=0; j<n; j++){
                if( (i & (1<<j)) != 0){
                    sb.insert(0,str.charAt(n-1-j));
                }
            }
            ans.add(sb.toString());
        }

        return ans;
    }
}
