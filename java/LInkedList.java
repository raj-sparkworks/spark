public class LInkedList {
    private class Node {
        int value;
        Node next;
        Node (int value)
        {
            this.value=value;
        }
    }

    Node first;
    Node last;

    public void addLast(int item)
    {
        Node node = new Node(item);

        if(first == null)
            first = last = node;
        else {
            last.next=node;
            last=node;
        }
    }

    public int getKthNode(int k)
    {
        Node a=first;
        Node b=first;
        for(int i=0;i<k-1;i++)
        {
            b=b.next;
            System.out.println("b value : "+b.value);
        }
        while(b!=last)
        {
            System.out.println("while b value : "+b.value);
            a=a.next;
            b=b.next;

        }
        return a.value;
    }

    public int getMidElement()
    {
        Node a = first;
        Node b = first;
        int j=1;
        while(b!=last)
        {
            if(j!=1)
            a=a.next;
            for(int i=1;i<=2;i++)
            {

                if(b==last)
                    return a.value;
                else {
                    if(j!=1)
                    b = b.next;

                }
            }
            System.out.println(" A value : "+a.value);
            System.out.println(" B value : "+b.value);
            j=j+2;
        }
        return a.value;
    }

    public void getMidElementV2()
    {
        Node a = first;
        Node b = first;
        while(b !=last && b.next != last)
        {
            a = a.next;
            b = b.next.next;
        }

        if(b==last)
        {
            System.out.println("Mid "+a.value);
        }
        else
        {
            System.out.println("MidS " +a.value+ " , "+a.next.value);
        }
    }


}
