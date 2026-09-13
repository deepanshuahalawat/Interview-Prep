import java.util.ArrayList;
import java.util.List;

public class DivisorCount {
    public static void main(String[] args) {
        List<Integer> query = new ArrayList<>();
        query.add(6);
        query.add(10);
        query.add(30);
        List<Integer> divs = threeDivisors(query);
        divs.forEach(div -> System.out.print(div+" "));
    }
    public static List<Integer> threeDivisors(List<Integer> query) {
        // code here
        List<Integer> divs = new ArrayList<>();
        for(Integer num: query){
            divs.add(countDivs(num));
        }
        return divs;
    }
    public static int countDivs(int num){
        int count=0;
        for(int no=4; no<= num; no++){
            int numDivs=0;
            for(int i=2; i< no; i++){
                if(no%i ==0){
                    numDivs++;
                }
            }
            if(numDivs == 1){
                System.out.println("Main num:"+num+", Exactly 3 divisors: "+no);
                count++;
            }
        }
        return count;
    }

}
