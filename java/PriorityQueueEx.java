import java.util.Arrays;
import java.util.PriorityQueue;

public class PriorityQueueEx {
    private int[] items = new int[5];
    int counter;
    public static void main(String[] args) {
PriorityQueueEx queue = new PriorityQueueEx();
        queue.add(2);
        queue.add(4);
        queue.add(1);
        queue.add(5);
}

    public void add(int input)
    {


        //int counter = 1;
        //System.out.println("Array : "+ Arrays.toString(items));
        int i;
        for(i=counter;i>=0;i--)
        {
            if(items[i] > input)
                items[i+1] = items[i];
            else {
                //System.out.println("else : "+items[i]);
                //items[i+1] = input;
                break;
            }
        }
        counter++;
        items[i]=input;
        System.out.println("Array : "+Arrays.toString(items));
    }
}
