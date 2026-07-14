package DataStructures.linkedlist;

/**
 * CircularLinkedList.java
 *
 * A Circular Singly Linked List where the last node points back to head.
 *
 * Operations:
 *  - addFirst, addLast, addAt(index)
 *  - removeFirst, removeLast, removeAt(index)
 *  - contains, size, print
 *
 * Key property: tail.next == head (always)
 *
 * Time Complexities:
 *  - addFirst / removeFirst : O(1)
 *  - addLast                : O(1) using tail pointer
 *  - removeLast             : O(n) (need previous of tail)
 *  - addAt / removeAt       : O(n)
 *
 * Space Complexity: O(n)
 *
 * Use cases:
 *  - Round-robin scheduling
 *  - Circular buffers
 *  - Josephus problem
 */
public class CircularLinkedList<T> {

    // â”€â”€â”€ Node â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    // â”€â”€â”€ Fields â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private Node<T> tail;   // tail.next == head
    private int size;

    private Node<T> head() { return tail == null ? null : tail.next; }

    // â”€â”€â”€ Add â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** O(1) */
    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) { tail = node; tail.next = tail; }
        else { node.next = tail.next; tail.next = node; }
        size++;
    }

    /** O(1) using tail */
    public void addLast(T data) {
        addFirst(data);        // insert after tail (becomes new head)
        tail = tail.next;      // advance tail to the new node
    }

    /** O(n) */
    public void addAt(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0)    { addFirst(data); return; }
        if (index == size) { addLast(data);  return; }
        Node<T> prev = head();
        for (int i = 0; i < index - 1; i++) prev = prev.next;
        Node<T> node = new Node<>(data);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    // â”€â”€â”€ Remove â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** O(1) */
    public T removeFirst() {
        if (tail == null) throw new java.util.NoSuchElementException();
        Node<T> h = tail.next;
        T data = h.data;
        if (tail == h) tail = null;          // single element
        else tail.next = h.next;
        size--;
        return data;
    }

    /** O(n) â€“ must find node before tail */
    public T removeLast() {
        if (tail == null) throw new java.util.NoSuchElementException();
        Node<T> prev = tail.next;            // start from head
        if (prev == tail) { T d = tail.data; tail = null; size--; return d; }
        while (prev.next != tail) prev = prev.next;
        T data = tail.data;
        prev.next = tail.next;               // skip tail
        tail = prev;
        size--;
        return data;
    }

    /** O(n) */
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0)        return removeFirst();
        if (index == size - 1) return removeLast();
        Node<T> prev = head();
        for (int i = 0; i < index - 1; i++) prev = prev.next;
        T data = prev.next.data;
        prev.next = prev.next.next;
        size--;
        return data;
    }

    // â”€â”€â”€ Search â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public boolean contains(T data) {
        if (tail == null) return false;
        Node<T> cur = head();
        for (int i = 0; i < size; i++) {
            if (data == null ? cur.data == null : data.equals(cur.data)) return true;
            cur = cur.next;
        }
        return false;
    }

    // â”€â”€â”€ Utilities â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    public void print() {
        if (tail == null) { System.out.println("(empty)"); return; }
        Node<T> cur = head();
        System.out.print("HEAD -> ");
        for (int i = 0; i < size; i++) {
            System.out.print(cur.data);
            if (i < size - 1) System.out.print(" -> ");
            cur = cur.next;
        }
        System.out.println(" -> (back to HEAD)");
    }

    // â”€â”€â”€ Josephus Problem â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /**
     * Josephus problem: n people in a circle, every k-th person is eliminated.
     * Returns the position (1-indexed) of the last survivor.
     * O(n*k) time, O(n) space.
     */
    public static int josephus(int n, int k) {
        CircularLinkedList<Integer> circle = new CircularLinkedList<>();
        for (int i = 1; i <= n; i++) circle.addLast(i);
        // Start at head
        Node<Integer> cur = circle.head();
        while (circle.size() > 1) {
            // Move k-1 steps forward (cur will be the k-th person after removal of prev)
            for (int i = 0; i < k - 1; i++) cur = cur.next;
            System.out.println("Eliminated: " + cur.data);
            // Remove cur
            // Find prev
            Node<Integer> prev = cur;
            for (int i = 0; i < circle.size() - 1; i++) prev = prev.next;
            prev.next = cur.next;
            if (cur == circle.tail) circle.tail = prev;
            cur = cur.next;
            circle.size--;
        }
        return circle.head().data;
    }

    // â”€â”€â”€ Main â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static void main(String[] args) {
        CircularLinkedList<Integer> cll = new CircularLinkedList<>();
        for (int i = 1; i <= 5; i++) cll.addLast(i * 10);
        cll.print();

        cll.addFirst(-1);
        cll.addAt(3, 999);
        System.out.print("After inserts: "); cll.print();

        System.out.println("removeFirst: " + cll.removeFirst());
        System.out.println("removeLast : " + cll.removeLast());
        System.out.print("After removes: "); cll.print();

        System.out.println("Contains 30? " + cll.contains(30));

        System.out.println("\n=== Josephus (n=6, k=2) ===");
        System.out.println("Survivor: " + josephus(6, 2));
    }
}

