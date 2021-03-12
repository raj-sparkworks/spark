import java.util.Arrays;
import java.util.Map;
import java.util.Spliterator;
//import java.util.HashMap;

public class SaranamIyyappa {
    public static void main(String[] args) {
        //getLinkedList();
        //getHashMap();
        //getTree();
        //getBubbleSort();
        //getMergeSort();
        //getQuickSort();
        //getPalindrome();

        System.out.println(1%2);
        Spliterator s =null;

        String vowels = "aeiou";
        for(int i=0;i<vowels.length();i++)
            System.out.println(vowels.indexOf('i'));
    }

    private static void getPalindrome() {
        boolean isPalindrome = StringUtil.isPalindrome("MADAMb");
        System.out.println(isPalindrome);
    }


    private static void getQuickSort()
    {
        int[] array = {100,5,6,3,4,1,2};
        QuickSort quickSort = new QuickSort();
        quickSort.sort(array);
        Arrays.toString(array);

    }


    private static void getMergeSort(){
        int[] array = {100,5,6,3,4,1,2};
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(array);
    }
    private static void getBubbleSort(){
        int[] array = {100,5,6,3,4,1,2};
        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.sort(array);
    }

    private static void getTree() {
        Tree tree = new Tree();
        tree.insert(7);
        tree.insert(4);
        tree.insert(9);
        tree.insert(1);
        tree.insert(6);
        tree.insert(8);
        tree.insert(10);

        Tree tree2 = new Tree();
        tree2.insert(7);
        tree2.insert(4);
        tree2.insert(9);
        tree2.insert(1);
        tree2.insert(6);
        tree2.insert(8);
        tree2.insert(10);

        //tree.insert(100);

        //tree.traversePostorder();
        //System.out.println( tree.height());
        //System.out.println(tree.min());
        //System.out.println(tree.minBST());
        //System.out.println(tree.equals(tree2));
        //System.out.println(tree.isBinarySearchTree());
        //tree.kDistance(1);

        //System.out.println("Value : "+tree.findV2(80));

        System.out.println(tree.minBST_recursion());
        System.out.println("Done !!");
    }

    private static void getLinkedList() {
        LInkedList list = new LInkedList();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addLast(40);
        list.addLast(50);
        list.addLast(60);
        list.addLast(70);
        list.addLast(80);
        list.addLast(90);
        list.addLast(100);
        list.addLast(110);
        list.addLast(120);
        list.addLast(130);

        System.out.println(list.getKthNode(3));
        System.out.println("Mid Value : "+list.getMidElement());
        list.getMidElementV2();

        System.out.println(list.toString());
    }

    private static void getHashMap() {
        HashMap map = new HashMap();
        map.put(1,"AAA");
        map.put(3,"BBB");
        map.put(6,"CCC");

        System.out.println(map.get(6));
        map.remove(6);
        System.out.println(map.get(6));
    }

    private static void test() {
        Map map = new java.util.HashMap();
        map.put(new Integer(11),new String("ss"));
        map.put(new Double(33.33),new Integer(33));


    }
}
