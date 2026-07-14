package DataStructures.queue;

/**
 * QueueArray.java
 *
 * Queue implementation using a circular array (ring buffer).
 * Avoids the O(n) shifting problem of naive array queues.
 *
 * Operations:
 *  - enqueue(T) : O(1)
 *  - dequeue()  : O(1)
 *  - front()    : O(1)
 *  - rear()     : O(1)
 *  - isEmpty()  : O(1)
 *  - isFull()   : O(1)
 *  - size()     : O(1)
 *
 * Space Complexity: O(n) where n = capacity
 *
 * FIFO - First In, First Out
 *
 * Applications:
 *  - CPU/disk scheduling
 *  - BFS graph traversal
 *  - Print spooler
 *  - Asynchronous data transfer (network packets)
 */
public class QueueArray<T> {

    private final Object[] data;
    private int front, rear, size;
    private final int capacity;

    public QueueArray(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
        front = 0;
        rear  = -1;
        size  = 0;
    }

    // â”€â”€â”€ Core Operations â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** Add element to rear. O(1). */
    public void enqueue(T item) {
        if (isFull()) throw new IllegalStateException("Queue is full");
        rear = (rear + 1) % capacity;
        data[rear] = item;
        size++;
    }

    /** Remove and return front element. O(1). */
    @SuppressWarnings("unchecked")
    public T dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        T item = (T) data[front];
        data[front] = null;       // help GC
        front = (front + 1) % capacity;
        size--;
        return item;
    }

    /** Peek at front without removing. O(1). */
    @SuppressWarnings("unchecked")
    public T front() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        return (T) data[front];
    }

    /** Peek at rear. O(1). */
    @SuppressWarnings("unchecked")
    public T rear() {
        if (isEmpty()) throw new java.util.NoSuchElementException("Queue is empty");
        return (T) data[rear];
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull()  { return size == capacity; }
    public int size()        { return size; }

    public void print() {
        System.out.print("Queue (front->rear): [");
        for (int i = 0; i < size; i++) {
            System.out.print(data[(front + i) % capacity]);
            if (i < size - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // â”€â”€â”€ Application: BFS Level-Order Traversal helper â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    // (Full BFS implemented in BinaryTree.java, shown here as Queue usage demo)

    /**
     * Generates first n numbers in binary representation using Queue BFS.
     * Queue-based approach, O(n) time and space.
     */
    public static void printBinaryNumbers(int n) {
        QueueArray<String> q = new QueueArray<>(n + 2);
        q.enqueue("1");
        System.out.print("Binary of 1.." + n + ": ");
        for (int i = 0; i < n; i++) {
            String curr = q.dequeue();
            System.out.print(curr + " ");
            q.enqueue(curr + "0");
            q.enqueue(curr + "1");
        }
        System.out.println();
    }

    // â”€â”€â”€ Application: Reverse first k elements of a queue â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /**
     * Reverses the first k elements of the queue, rest stays intact.
     * Uses an auxiliary stack.
     * Time: O(n), Space: O(k)
     */
    public static void reverseFirstK(QueueArray<Integer> q, int k) {
        if (k <= 0 || k > q.size()) return;
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        for (int i = 0; i < k; i++) stack.push(q.dequeue());
        while (!stack.isEmpty()) q.enqueue(stack.pop());
        for (int i = 0; i < q.size() - k; i++) q.enqueue(q.dequeue());
    }

    // â”€â”€â”€ Main â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static void main(String[] args) {
        QueueArray<Integer> q = new QueueArray<>(8);
        for (int i = 1; i <= 5; i++) q.enqueue(i * 10);
        q.print();
        System.out.println("Front: " + q.front() + "  Rear: " + q.rear());
        System.out.println("Dequeue: " + q.dequeue());
        q.print();

        System.out.println("\n-- Binary numbers 1..10 --");
        printBinaryNumbers(10);

        System.out.println("\n-- Reverse first 3 elements --");
        QueueArray<Integer> q2 = new QueueArray<>(10);
        for (int i = 1; i <= 6; i++) q2.enqueue(i);
        System.out.print("Before: "); q2.print();
        reverseFirstK(q2, 3);
        System.out.print("After : "); q2.print();
    }
}

