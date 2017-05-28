public class PQHeap implements PQ {
    /*
    By:
            Jeff Gyldenbrand - jegyl16
            Bjarke Holst Kreiberg - bjkre16
    */

    /*
        1.  Array A consisting of Element-type is set to final,
            because we want it to be a constant (we cant modify it).

        2.  Defined a heap-size to maintain control over A's actual element count.
    */
    private final Element[] A;
    public int heapSize = 0;

     /*
        1.  Creating constructor taking maxElms as parameter from
            Heapsort-class, implemented from PQ-class.
    */
    public PQHeap(int maxElms) {

        // Creating new instance of A with maxElms as argument.
        this.A = new Element[maxElms];
    }
    @Override
    public Element extractMin() {
        /*
        1. Now that all elements are inserted to our array, we define a min
        variable, that stores the first element in the array (also the smallest
        value).

        2. then we decrement the heapsize by one.

        3. now we run minHeapify method from the top (index 0), and returning
           the stored value from min.
        */
        Element min = A[0];
        A[0] = A[--heapSize];
        minHeapify(0);

        // .. and returning the smallest value to output.
        return min;
    }
    @Override
    public void insert(Element e) {
        /*
        1. setting 'i' equal heapsize (which is zero at initialization),
           and then incrementing the heapsize by one (making space for next
           element).

        2. inserting element 'e' into the i'th index in array 'A'.

        3. setting 'p' to be the parent-index of current 'i'-index.

        4.  Then checking if the parent value is greater than its child,
            while true, we swap the values. Then set the child-index to its
            parent-index, and the parent-index to parent(i).
        */
        int i = heapSize++;
        A[i] = e;
        int p = parent(i);

        while (i > 0 && A[p].key > A[i].key) {
           swap(i, p);
           i = p;
           p = parent(i);
        }
    }
    public void minHeapify(int i) {
        /*
        1.  when minHeapify is runned from extractMin(), it creates an array of
            the children of index 0 (the top). This is defined by the left(i)
            and right(i)-methods.

        2.  when the index of the children is known, its checks if any of them
            is greater or equal to current heapsize, if true, then it returns
            to extractMin() which outputs the current min-value.
            if not true, it checks if the child-index is smaller than its
            parent-index, if so, swaps the values and run minHeapify.
        */
        int[] children = {
            left(i),
            right(i)
        };
        for(int j : children){
           if(j >= heapSize) {
               return;
           }
           if(A[j].key < A[i].key){
                swap(j,i);
                minHeapify(j);
            }
        }
    }
    // method 'left' returns the index one left from 'i'
    private int left(int i) {
        return 2 * i + 1;
    }
    // method 'right' returns the index one right from 'i'
    private int right(int i) {
        return left(i) + 1;
    }
    // method 'parent' returns the parent index of the 'i'.
    private int parent(int i) {
        return (i - 1) / 2;
    }
    // method 'swap' do just that; swaps the inputs.
    public void swap(int i, int j){
            Element temp = A[i];
            A[i] = A[j];
            A[j] = temp;
    }
}