package bitwiseoperators;

public class RightShiftOperatorMain {

	public static void main(String[] args) {
        System.out.println((99 >>> 1));
		System.out.println(49 << 1);
		System.out.println((49 << 1 ) + 1);
		System.out.println((49 << 1 ) + 2);
        
        System.out.println("********");
        int size = 100;
        assert 10 >= 0 && 10 < 2;
        
        /*
        for (int i = (size >>> 1) - 1; i >= 0; i--)
            System.out.println(i);
            */
    }
	
}
