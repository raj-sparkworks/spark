import java.util.*;

public class QueueEx {

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(10);
        queue.add(20);
        queue.add(30);
        queue.add(40);
        queue.add(50);
        //int a = queue.poll();
       // System.out.println(a);

        System.out.println(queue);
        System.out.println(queue.size());
        System.out.println("Reverse : "+reverse(queue));

        int [] test = {10,20,30,40,50};
        System.out.println(test[0] +":"+ test[4]);


    }

    public static Queue<Integer> reverse(Queue<Integer> queue)
    {
       Stack<Integer> stack = new Stack<>();
       while(!queue.isEmpty())
       {
           stack.push(queue.remove());
       }
       while(!stack.isEmpty()) {
           queue.add(stack.pop());
       }
       return queue;
    }
}

