/**
 * MinHeap.java
 *
 * Min-Heap (Priority Queue) backed by an array.
 * The minimum element is always at the root (index 0).
 *
 * Operations:
 *  - insert(key)   : O(log n)
 *  - extractMin()  : O(log n)
 *  - peekMin()     : O(1)
 *  - decreaseKey() : O(log n)
 *  - delete(index) : O(log n)
 *  - buildHeap()   : O(n)  ← Floyd's algorithm
 *
 * Heap properties:
 *  - Parent of i  : (i-1) / 2
 *  - Left child   : 2*i + 1
 *  - Right child  : 2*i + 2
 *
 * Space Complexity: O(n)
 *
 * Applications:
 *  - Priority queues
 *  - Dijkstra's shortest path
 *  - Heap sort
 *  - K-th smallest / largest element
 *  - Merge K sorted arrays
 */
package DataStructures.heap;

public class MinHeap {

    private int[] heap;
    private int size;
    private final int capacity;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        heap = new int[capacity];
        size = 0;
    }

    // ─── Parent / Children ───────────────────────────────────────────────────
    private int parent(int i) { return (i - 1) / 2; }
    private int left(int i)   { return 2 * i + 1; }
    private int right(int i)  { return 2 * i + 2; }

    private void swap(int i, int j) {
        int tmp = heap[i]; heap[i] = heap[j]; heap[j] = tmp;
    }

    // ─── Insert ───────────────────────────────────────────────────────────────
    /** Inserts key and sifts up. O(log n). */
    public void insert(int key) {
        if (size == capacity) throw new IllegalStateException("Heap is full");
        heap[size] = key;
        int i = size++;
        // Sift up
        while (i > 0 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    // ─── Extract Min ─────────────────────────────────────────────────────────
    /** Removes and returns the minimum. O(log n). */
    public int extractMin() {
        if (size == 0) throw new java.util.NoSuchElementException("Heap is empty");
        int min = heap[0];
        heap[0] = heap[--size];
        heapifyDown(0);
        return min;
    }

    /** Restore heap property downward from index i. */
    private void heapifyDown(int i) {
        int smallest = i;
        int l = left(i), r = right(i);
        if (l < size && heap[l] < heap[smallest]) smallest = l;
        if (r < size && heap[r] < heap[smallest]) smallest = r;
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    // ─── Peek Min ────────────────────────────────────────────────────────────
    public int peekMin() {
        if (size == 0) throw new java.util.NoSuchElementException();
        return heap[0];
    }

    // ─── Decrease Key ────────────────────────────────────────────────────────
    /** Reduces heap[index] to newKey, then sifts up. O(log n). */
    public void decreaseKey(int index, int newKey) {
        checkIndex(index);
        if (newKey > heap[index]) throw new IllegalArgumentException("New key is greater");
        heap[index] = newKey;
        while (index > 0 && heap[parent(index)] > heap[index]) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    // ─── Delete at index ─────────────────────────────────────────────────────
    public void delete(int index) {
        decreaseKey(index, Integer.MIN_VALUE);
        extractMin();
    }

    // ─── Build Heap from array (Floyd's O(n)) ────────────────────────────────
    public void buildHeap(int[] arr) {
        if (arr.length > capacity) throw new IllegalStateException("Array too large for heap");
        size = arr.length;
        System.arraycopy(arr, 0, heap, 0, size);
        // Heapify all non-leaf nodes bottom-up
        for (int i = size / 2 - 1; i >= 0; i--) heapifyDown(i);
    }

    // ─── Heap Sort (ascending) ────────────────────────────────────────────────
    /**
     * Sorts an array using a MaxHeap approach in-place.
     * Time: O(n log n), Space: O(1)
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        // Build max-heap
        for (int i = n / 2 - 1; i >= 0; i--) maxHeapify(arr, n, i);
        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0]; arr[0] = arr[i]; arr[i] = tmp;
            maxHeapify(arr, i, 0);
        }
    }

    private static void maxHeapify(int[] arr, int n, int i) {
        int largest = i, l = 2*i+1, r = 2*i+2;
        if (l < n && arr[l] > arr[largest]) largest = l;
        if (r < n && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            int tmp = arr[i]; arr[i] = arr[largest]; arr[largest] = tmp;
            maxHeapify(arr, n, largest);
        }
    }

    // ─── K Smallest elements ─────────────────────────────────────────────────
    /**
     * Returns k smallest elements from arr using a Min-Heap.
     * Time: O(n + k log n)
     */
    public static int[] kSmallest(int[] arr, int k) {
        MinHeap mh = new MinHeap(arr.length);
        mh.buildHeap(arr);
        int[] result = new int[k];
        for (int i = 0; i < k; i++) result[i] = mh.extractMin();
        return result;
    }

    // ─── Utilities ───────────────────────────────────────────────────────────
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    public void print() {
        System.out.print("Heap: [");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i]);
            if (i < size - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        MinHeap mh = new MinHeap(15);
        int[] vals = {15, 10, 5, 3, 20, 8};
        for (int v : vals) mh.insert(v);
        mh.print();

        System.out.println("Min      : " + mh.peekMin());
        System.out.println("ExtractMin: " + mh.extractMin());
        mh.print();

        System.out.println("\n-- Build Heap (Floyd) --");
        MinHeap mh2 = new MinHeap(10);
        mh2.buildHeap(new int[]{9, 4, 7, 1, 8, 3, 6, 2, 5});
        mh2.print();

        System.out.println("\n-- Heap Sort --");
        int[] arr = {12, 11, 13, 5, 6, 7};
        heapSort(arr);
        System.out.print("Sorted: [");
        for (int i = 0; i < arr.length; i++) System.out.print(arr[i] + (i < arr.length-1 ? ", " : ""));
        System.out.println("]");

        System.out.println("\n-- 3 Smallest from {7,10,4,3,20,15} --");
        int[] res = kSmallest(new int[]{7,10,4,3,20,15}, 3);
        System.out.print("Result: [");
        for (int i = 0; i < res.length; i++) System.out.print(res[i] + (i < res.length-1 ? ", " : ""));
        System.out.println("]");
    }
}
