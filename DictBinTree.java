public class DictBinTree implements Dict {
    // creating a header node.
    private Node header;

    // creating size, to keep track of the tree size.
    private int size = 0;

    //creating the array
    int[] arr;

    // creating a counter to keep track of where we are in the array.
    private int counter = 0;

    public DictBinTree() {
        // returns empty dictionary.
        header = null;
    }

    public class Node {

    public int key;
    public Node left = null;
    public Node right = null;
    public Node parent;

    public Node(int key, Node parent) {
        this.key = key;
        this.parent = parent;
    }
}
    @Override
    public boolean search(int k) {
        return search(header, k);
    }

    public boolean search(Node x, int k) {
        // if the node is null, return false
        if (x == null) {
            return false;
        }

        // if current nodes key is the same as key, we found it. (returns true)
        if (x.key == k) {
            return true;
        }

        // but, if k is less than current nodes key, go left
        if (k < x.key) {
            return search(x.left , k);
        }

        // and if its greater, go right.
        else {
            return search(x.right, k);
        }
    }

    @Override
    public void insert(int k) {
        // increment size
        size++;

        // create a node and sets it to header, which references current node.
        Node x = header;
        // create a node y, which will function as a trailing pointer.
        Node y = null;

        // while header is not empty
        while (x != null) {
            // store the node in y
            y = x;

            // if k is less than current node, we search left in the tree.
            if (k < x.key) {
                x = x.left;
            }

            // if k is greater than current node, we search in the right side.
            else {
                x = x.right;
            }
        }
        if (y == null) {
            // if y is empty, we create a new root with k.
            header = new Node(k, y);
        }

        else if(k < y.key) {
            // else if k is less than current node, we create a new node, in
            // its left child, with k.
            y.left = new Node(k, y);
        }

        else {
            // if k is greater or equal to current node, we create a new node,
            // in its right child, with k.
            y.right = new Node(k, y);
        }
    }

        private int[] orderedTraversal_private(Node header) {
        // if the node is not null, we recursively go left and
        // add the current nodes key to the array. Increment the counter,
        // and recursively call go right.

        if (header != null) {
            orderedTraversal_private(header.left);
            arr[counter] = header.key;
            counter++;

            orderedTraversal_private(header.right);
        }
        return arr;
    }

    @Override
    public int[] orderedTraversal() {
        // create the array of current size, and returns it.
        arr = new int[size];
        orderedTraversal_private(header);
        return arr;
    }
}
