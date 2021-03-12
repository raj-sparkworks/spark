public class RecursionTest {
    public static void main(String[] args) {
        count(3);
    }

    public static void count(int n)
    {
        if(n==0)
            return;

        //System.out.println("I : "+n);
        count(n-1);
        System.out.println("II : "+n);
        count(n-1);
        System.out.println("III : "+n);
    }
}
