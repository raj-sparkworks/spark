import java.util.Arrays;

public class MergeSort {
    public void sort(int[] array) {

        if(array.length < 2)
            return;

        int middle = array.length / 2;

        int[] left = new int[middle];
        int[] right = new int[array.length - middle];

        for(int i=0;i<middle;i++) {
            left[i] = array[i];
        }

        for(int j=middle;j< array.length;j++) {
          right[j-middle] = array[j];
        }

        sort(left);
        sort(right);

        merge(left,right,array);

        //return array;
    }

    public void merge(int[] left, int[] right, int[] result) {
        int i=0,j=0,k=0;

        while(i < left.length && j< right.length) {
            if(left[i] < right[j])
                result[k++] = left[i++];
            else
                result[k++] = right[j++];
        }

        while(i < left.length) {
            result[k++]=left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        //return result;
        System.out.println(Arrays.toString(result));
    }
}
