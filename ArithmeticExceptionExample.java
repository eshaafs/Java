
public class ArithmeticExceptionExample {

	public static void main(String[] args) {
		try {
			int a = 10, b = 0;
			int c = a / b;
		} catch (ArithmeticException e) {
			System.err.println(e);
			System.err.println("Tidak dapat membagi dengan 0");
		}

	}

}
