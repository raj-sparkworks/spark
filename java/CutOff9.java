public class CutOff9 {
    public static void main(String[] args) {

        int x=1236988;
       while(x > 9) {
           x = sumDigit(x);
           System.out.println("Value : "+ x);
       }
       System.out.println("==========================================");
       System.out.println("Value : "+ x);
    }

    private static int sumDigit(int n)
    {
        int sum=0;
        while(n > 0)
        {
            if(n % 10 != 9)
            {
                sum = sum + (n % 10);
            }
            n = n / 10;
        }
        return sum;

    }
}
