import java.util.Random;
class Fibo{
	public static void main(String arg[]){
		Random random = new Random();
		isPal(1);
		isPal(101);
		isPal(103001);
		isPal(123454321);
		isPal(11);
		
	}
	static int isPal(int num){
		int reverse = revNum(num);
		if(reverse == num){
			System.out.println("No "+num +" is palindrome");
		}else{
			System.out.println("No "+num +" NOT palindrome");
		}
		return 1;
	}
	static int revNum(int num){
		int digit=0;
		int rev=0;
		while(num > 0){
			digit = num%10;
			rev = rev*10 + digit;
			num = num/10;
		}
		return rev;
	}
	
	static int countDigits(int num){
		int digits=0;
		while(num > 0){
			digits++;
			num = num/10;
		}
		return digits;
	}
	
	public static int fiboLin(int n){
		int a=0, b=1;
		int num=0;
		for(int i=0; i< n; i++){
			if(i == 0)
				num=a;
			else if(i==1)
				num=b;
			else{
				num = a+b;
				a=b;
				b=num;
			}
			System.out.print(num+" ");
		}
		return num;
	}
	
	
	public static int fibo(int n){
		int num;
		if(n==1 || n==0){
			return n;
		}
		num = fibo(n-2) + fibo(n-1);
		return num;
	}
}