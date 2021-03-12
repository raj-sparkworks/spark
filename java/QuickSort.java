import java.util.Arrays;

public class QuickSort {
    //partition
    //sort left
    //sort right

    public void sort(int[] array) {
        sort(array,0,array.length-1);
    }


    private void sort(int[] array, int start, int end) {
        if(start>=end)
            return;

        System.out.println(start + " : "+end);
        int boundary = partition(array,start,end);
        sort(array,start,boundary-1);
        sort(array,boundary+1,end);
        Arrays.toString(array);
    }

    public int partition(int[] array, int start, int end)
    {
        int pivot = array[end];
        int boundary = start -1;
        for(int i=start; i<=end;i++) {
            if(array[i] <= pivot) {
                int temp = array[i];
                array[i]=array[++boundary];
                array[boundary]=temp;
                //boundary++;
            }
        }
        return boundary;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i]=array[j];
        array[j]=temp;
    }
}
