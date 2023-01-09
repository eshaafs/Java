
public class StringIndexOutOfBoundExceptionExample {

	public static void main(String[] args) {
		try {
			String nama = "Esha Sajaka";
			System.out.println(nama.charAt(50));
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println(e);
			System.err.println("Index string tidak tersedia!");
		}

	}

}
