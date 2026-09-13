class PositionOfFirstSetBit {
    public static void main(String[] args) {
        System.out.println("Pos: "+getFirstSetBit(12));
    }
    public static int getFirstSetBit(int n) {
        // code here
        int a = n & (n-1);
        int b = n^a;
        int c = b<<1;
        int bitPos=0;
        while(c > 1){
            bitPos++;
            c = c>>1;
        }
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(Integer.toBinaryString(c));
        return bitPos;
    }

}