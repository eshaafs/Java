
public class NumberFormatExceptionExample {

	public static void main(String[] args) {
		try {
			// Esha bukanlah angka
			int a = Integer.parseInt("Esha");
		} catch (NumberFormatException e) {
			System.err.println(e);
			System.err.println("Data bukanlah sebuah angka!");
		}

	}

}
