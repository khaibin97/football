///////////////////////////////////////////////////////////////////////////////
//
// Title: p1 Implement and Test an ADT
// Class: MaxPQ
// Course: CS400
//
// Author: Khai Bin Woon
// Email: woon2@wisc.edu
// Due : Feb 5 2018
// Known Bugs :
// Limitations : null values inputs are ignored
//
//////////////////////////////////////////////////////////////////////////////
/**
 * Implementation of Priority Queue with the largest on top
 * 
 * @author wk_bi
 * @version 1.01
 */
public class MaxPQ<E extends Comparable<E>> implements PriorityQueueADT <E> {

    private E[] heap;
    private int arraySize;// keeps track of number of elements in array

    /**
     * Initializes a new priority queue.
     */
    public MaxPQ() {
        heap = (E[])new Comparable[10];
        arraySize = 0;
    }

    /**
     * Checks if PriorityQueue have elements
     * 
     * @return true if there is no elements in the array
     */
    @Override
    public boolean isEmpty() {
        return arraySize == 0;
    }

    /**
     * Inserts at the end of the array and moves it up. 
     * Null values entered are ignored
     * Decision to move up is made by helper method.
     */
    @Override
    public void insert(E item) {
        if (item == null) return;
        
        this.expandArray();// make sure that array is not full and expand iff full

        heap[++arraySize] = item;
        siftUp(arraySize); // sift up the newly added element
    }

    /**
     * Peeks at the max element without removing it.
     * 
     * @throws EmtptyQueueException if array is empty
     */
    @Override
    public E getMax() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        return heap[1];
    }

    /**
     * Removes the element from the front of the queue
     * 
     * @throws EmptyQueueExcxeption there is no element to remove
     */
    @Override
    public E removeMax() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        E max = heap[1]; // Stores the element to be removed
        swap(1, arraySize--); // Swaps with the last element and moves it down
        siftDown(1); // Helper method to move the root of the tree down to its place
        heap[arraySize + 1] = null; // Deletes the "max" element which is at root now

        return max;
    }

    /**
     * The number of elements in the PriorityQueue
     * 
     * @return the number of elements in the array
     */
    @Override
    public int size() {
        return arraySize;
    }


    /**
     * Expands the array size only if the array is full.
     */
    private void expandArray() {
        if (arraySize >= heap.length -1) { // nothing is done if array is not full

            E[] bigger = (E[])new Comparable[heap.length + 10];

            int i = 0;
            for (E e : heap) {
                bigger[i++] = e;
            }

            heap = bigger;
        }
    }

    /**
     * Swaps the array elements of given @param
     * 
     * @param i - index of element 1
     * @param j - index of element 2
     */
    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * If one is less than two. Uses compareTo to compare element at heap
     * 
     * @param one - index of element one
     * @param two - index of element 2
     * @return one < two ? true : false
     */
    private boolean less(int one, int two) {
        return (heap[one].compareTo(heap[two]) < 0);
    }

    /**
     * Propagates the inserted element up to maintain max-heap structure Insert helper method
     * 
     * @param k - index of element to sift up
     */
    private void siftUp(int k) {
        // while element not at root and parent less than current
        // swap parent and child
        while (k > 1 && less(k / 2, k)) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    /**
     * Propagates the element down to maintain max-heap structure Delete Max helper method
     * 
     * @param k - index of element to sift down
     */
    private void siftDown(int k) {
        // while there is a child and parent is less than child
        while (2 * k <= arraySize) {
            int child = 2*k;
            
            // if there is a left and right child and right child is greater
            if (child < arraySize && less(child, child+1)) {
                child++; // moves index to right
            }
            if(!less(k,child)){ //if parent is greater than left OR right child
                break; //exit current loop
            }
            swap(k,child); //exchange with the bigger child
            k = child; //moves index into bigger child
        }
    }
}
