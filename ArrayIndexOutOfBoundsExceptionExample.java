
public class ArrayIndexOutOfBoundsExceptionExample {

	public static void main(String[] args) {
		try {
			int[] angka = {1, 2, 3, 4};
			System.out.print(angka[4]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println(e);
			System.err.println("Indeks tidak tersedia!");
		}

	}

}
