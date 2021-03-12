import java.util.Arrays;

public class Sorting {
    public static void main(String[] args) {
        int[] array = {4,2,6,3,7,1};
        int[] output = execBubbleSort(array);
        System.out.println("Output : "+ Arrays.toString(output));
    }
    public static int[] execBubbleSort(int[] array)
    {
        int temp;
        boolean flag=false;
        while(flag==false) {
            flag=true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                    flag=false;
                }
            }
        }
        return array;
    }
}
