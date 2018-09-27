

public class Array {
    public static void main(String[] args) {
		int[] a=new int[] {12,43,65,3,-8,64,2};
		for(int i=a.length-1;i>=0;i--) {
			a[i]=a[i]/a[0];
		}//±éÀúa
		for(int i=0;i<=a.length;i++) {
			System.out.print(a[i]+"");
		}
	}
}
