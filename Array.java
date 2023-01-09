
public class Array {

	public static void main(String[] args) {
//		int[] array1 = {10,20,30};
//		int[] array2 = new int[array1.length];
//		for(int i = 0; i < array2.length; i++) {
//			array2[i] = array1[i];
//		}
//		int[] array3 = {100,200,300};
//		int[] array4 = array3.clone();
		
		char[] array5 = {'a', 'b', 'c'};
		char[] array6 = new char[array5.length];
		System.arraycopy(array5, 0, array6, 0, array5.length);
		
		System.out.print("array5 = ");
		for(int i = 0; i < array5.length; i++) {
			System.out.print(array5[i] + " ");
		}
		System.out.print("\n");
		System.out.print("array6 = ");
		for(int i = 0; i < array6.length; i++) {
			System.out.print(array6[i] + " ");
		}
		
	}

}

