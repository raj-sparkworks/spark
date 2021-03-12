import java.util.Arrays;

public class BubbleSort {
    public void sort(int[] array) {
        int temp;
        boolean isSorted;
        for(int i=0;i<array.length;i++) {
            isSorted=true;
            for (int j = 1; j < array.length-i; j++) {
                if (array[j] < array[j - 1]) {
                    temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                    isSorted = false;
                }
            }
            if(isSorted)
                break;
        }

        System.out.println(Arrays.toString(array));
    }
}
