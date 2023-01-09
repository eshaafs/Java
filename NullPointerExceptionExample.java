
public class NullPointerExceptionExample {

	public static void main(String[] args) {
		try {
			String a = null;
			System.out.print(a.charAt(2));
		} catch (NullPointerException e) {
			System.err.println(e);
			System.err.println("Variable bernilai null!");
		}

	}

}
