public class SwapBits {
    public static void main(String[] args) {
        System.out.println("Out"+swapBits(900031530));
    }
    public static int swapBits(int n) {
        int k=n,j=n;
        // code here
        if(n < 2)
            return n;
        else{
            System.out.println(Integer.toBinaryString(n));
            for(int i = 1; k>0; i=i+2){
                int a = (n>>i)&1;
                int b = (n>>(i-1))&1;
                System.out.print(Integer.toBinaryString(a)+"\t");
                System.out.println(Integer.toBinaryString(b));
                if((a^b) != 0){
                    n = n^(1<<i);
                    n = n^(1<<(i-1));
                    System.out.println(Integer.toBinaryString(n));
                }
                k = k>>2;
            }
        }
        System.out.println("j"+Integer.toBinaryString(j));
        System.out.println("n"+Integer.toBinaryString(n));
        return n;
    }
}
