import java.util.Scanner;

public class PrimeNumber {
	// Method to check prime number
	static boolean isPrime(int n) {
		if(n <= 1) return false;
		for(int j = 2; j < n; j++) {
			if(n % j == 0) return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		// Get number of Prime number
		System.out.print("Masukkan N\t: ");
		int n = 0;
		
		// Exception handling
		try (Scanner input = new Scanner(System.in);){
			n = input.nextInt();
		} catch (Exception e) {
			System.out.println("Masukkan bilangan bulat positif!");
			System.exit(1);
		}
		
		// Creating row of prime number
		System.out.print("Deret\t\t: ");
		int[] prime = new int[n];
		if(n < 2) {
			System.out.println("Deret\t\t: N\\A");
			System.out.print("Total\t\t: 0");
		} else {
			int totalPrime = 0, counter = 2;
			while(totalPrime < n) {
				if(isPrime(counter) == true) {
					prime[totalPrime] = counter;
					totalPrime++;
				}
				counter++;
			}
		}
		
		String rowPrime = "";
		for(int i = 0; i < n; i++) {
			rowPrime += prime[i] + ", ";
		}

		if(rowPrime.length() > 2) {
			System.out.print(rowPrime.substring(0, rowPrime.length()-2));
		} else {
			System.out.print("N\\A");
		}
		System.out.println();
		
		
		// Sum all prime number in array
		System.out.print("Total\t\t: ");
		int sum = 0;
		for(int i = 0; i < n; i++) {
			sum += prime[i];
		}
		System.out.print(sum);
	}

}
