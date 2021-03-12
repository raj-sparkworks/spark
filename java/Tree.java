public class Tree {
    private class Node {
        int value;
        Node leftChild;
        Node rightChild;

        public Node(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node = " + value;
        }
    }

    Node root;


    public void insert(int value) {

        if (root == null) {
            root = new Node(value);
            return;
        }
        Node current = root;
        while (true) {

            if (value < current.value) {
                if (current.leftChild == null) {
                    current.leftChild = new Node(value);
                    break;
                }
                current = current.leftChild;
            } else {
                if (current.rightChild == null) {
                    current.rightChild = new Node(value);
                    break;
                }
                current = current.rightChild;
            }
        }
    }

    public boolean findV2(int value) {
        Node current = root;
        while (current != null) {
            if (value < current.value) {
                current = current.leftChild;
            } else if (value > current.value) {
                current = current.rightChild;
            } else {
                return true;
            }
        }

        return false;
    }

    public boolean find(int value) {
        if (root == null) {
            return false;
        }
        if (value == root.value)
            return true;

        Node current = root;
        while (true) {
            if (current.leftChild == null || current.rightChild == null)
                return false;

            if (value < current.value) {
                if (value == current.leftChild.value)
                    return true;

                current = current.leftChild;
            } else {

                if (value == current.rightChild.value)
                    return true;

                current = current.rightChild;
            }
        }

    }

    public void traversePreOrder() {
        traversePreOrder(root);
    }

    public void traversePreOrder(Node root) {
        if (root == null)
            return;
        System.out.println(root.value);

        traversePreOrder(root.leftChild);
        traversePreOrder(root.rightChild);
    }

    public void traverseInorder() {
        traverseInOrder(root);
    }


    public void traverseInOrder(Node root) {
        if(root==null)
            return;
        traverseInOrder(root.leftChild);
        System.out.println(root.value);
        traverseInOrder(root.rightChild);
    }

    public void traversePostorder() {
        traversePostOrder(root);
    }


    public void traversePostOrder(Node root) {
        if(root==null)
            return;
        traversePostOrder(root.leftChild);
        traversePostOrder(root.rightChild);
        System.out.println(root.value);
    }

    public int height() {
        return height(root);
    }

    public int height(Node root) {
        if(root.leftChild == null || root.rightChild==null)
            return 0;

        return 1 + Math.max(height(root.leftChild),height(root.rightChild));
    }

    public int min()
    {
        return min(root);
    }

    private int min(Node root)
    {
        if(root.leftChild == null && root.rightChild == null)
            return root.value;
        //System.out.println("value : "+root.value);
        int left = min(root.leftChild);
        //System.out.println("After left : "+root.value + " left "+left);
        int right = min(root.rightChild);
        //System.out.println("After right : "+root.value+" right "+right);
        return Math.min(Math.min(left, right),root.value);
        //return Math.max(left,root.value);
    }

    public int minBST()
    {
        Node curent = root;
        Node last = curent;
        while(curent!=null){
            last=curent;
            curent=curent.rightChild;
        }

        return last.value;
    }


    public int minBST_recursion()
    {
       return minBST_recursion(root);
    }
    Node last = root;
    Node current = root;
    private int minBST_recursion(Node root)
    {
        if(root == null)
          return current.value;

        current=root;
        return minBST_recursion(root.rightChild);
    }

    public boolean equals1(Tree other)
    {
        return equals1(root,other.root);
    }

    private boolean equals1(Node first, Node second)
    {
        if(first ==null && second== null)
            return true;

        if(first!=null && second!=null)
        return first.value == second.value &&
                equals1(first.leftChild,second.leftChild) &&
                equals1(first.rightChild,second.rightChild);
            //return true;

        return false;

    }

    public boolean isBinarySearchTree()
    {
        return isBinarySearchTree(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    private boolean isBinarySearchTree(Node root, int min, int max)
    {
        if(root == null)
            return false;

        if(root.value < min || root.value > max)
            return false;

        return isBinarySearchTree(root.leftChild,min, root.value-1) &&
                isBinarySearchTree(root.rightChild, root.value +1,max );
    }

    public void kDistance(int distance)
    {
        kDistance(root,distance);

    }

    private void kDistance(Node root,int distance)
    {
        if(root == null)
            return;
        if(distance==0)
        {
            System.out.println(root.value);
        }

        kDistance(root.leftChild,distance-1);
        //return root.value;

        /*Node current=root;

        while(distance!=0)
        {
            current=current.leftChild;
            distance--;
        }*/

        //System.out.println(root.value);


    }
}