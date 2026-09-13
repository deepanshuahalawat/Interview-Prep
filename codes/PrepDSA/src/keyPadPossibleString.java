import java.util.ArrayList;

public class keyPadPossibleString {
    public static void main(String[] args) {
        int nums[] = {2,3};
        kpString(nums);
    }
    public static void kpString(int nums[]){
        ArrayList<String> strs = numberToString(nums);
        strs.forEach(s -> System.out.print(s+" "));
        System.out.println();
        ArrayList<String> out = new ArrayList<>();
        permute(strs, "", 0, out);
        out.forEach(e -> System.out.println(e));
    }
    public static ArrayList<String> permute(ArrayList<String> strs, String curr, int n, ArrayList<String> out){
        if(n == strs.size()){
            out.add(new String(curr));
            return out;
        }
        for(int i=n; i< strs.size(); i++){
            for(int j=0;j<strs.get(n).length(); j++){
                permute(strs, curr+strs.get(n).charAt(j), n+1, out);
            }
        }
        return out;
    }
    private static void swap(char arr[], int i, int j){
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static ArrayList<String> numberToString(int nums[]){
        ArrayList<String> strs = new ArrayList<>();
        for(int i=0; i< nums.length; i++){
            if(nums[i] == 2){
                strs.add("ABC");
            } else if (nums[i] == 3) {
                strs.add("DEF");
            } else if (nums[i] == 4) {
                strs.add("GHI");
            } else if (nums[i] == 4) {
                strs.add("JKL");
            } else if (nums[i] == 4) {
                strs.add("MNO");
            } else if (nums[i] == 4) {
                strs.add("PQRS");
            } else if (nums[i] == 4) {
                strs.add("TUV");
            } else if (nums[i] == 4) {
                strs.add("WXYZ");
            }
        }
        return strs;
    }

}
