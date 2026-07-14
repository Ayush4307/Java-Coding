package DataStructures.queue;

/**
 * Deque.java  (Double-Ended Queue)
 *
 * Supports insert/delete at BOTH front and rear in O(1).
 * Backed by a doubly linked list.
 *
 * Operations:
 *  - addFirst / addLast     : O(1)
 *  - removeFirst / removeLast: O(1)
 *  - peekFirst / peekLast   : O(1)
 *  - size / isEmpty         : O(1)
 *
 * Space Complexity: O(n)
 *
 * Applications:
 *  - Sliding window maximum (monotonic deque)
 *  - Palindrome checking
 *  - Undo-redo at both ends
 *  - Work-stealing scheduling
 */
public class Deque<T> {

    // â”€â”€â”€ Node â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private static class Node<T> {
        T data;
        Node<T> prev, next;
        Node(T data) { this.data = data; }
    }

    // â”€â”€â”€ Fields â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private Node<T> head, tail;
    private int size;

    // â”€â”€â”€ Add â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) { head = tail = node; }
        else { node.next = head; head.prev = node; head = node; }
        size++;
    }

    public void addLast(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) { head = tail = node; }
        else { tail.next = node; node.prev = tail; tail = node; }
        size++;
    }

    // â”€â”€â”€ Remove â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public T removeFirst() {
        if (head == null) throw new java.util.NoSuchElementException();
        T data = head.data;
        if (head == tail) { head = tail = null; }
        else { head = head.next; head.prev = null; }
        size--;
        return data;
    }

    public T removeLast() {
        if (tail == null) throw new java.util.NoSuchElementException();
        T data = tail.data;
        if (head == tail) { head = tail = null; }
        else { tail = tail.prev; tail.next = null; }
        size--;
        return data;
    }

    // â”€â”€â”€ Peek â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public T peekFirst() {
        if (head == null) throw new java.util.NoSuchElementException();
        return head.data;
    }

    public T peekLast() {
        if (tail == null) throw new java.util.NoSuchElementException();
        return tail.data;
    }

    // â”€â”€â”€ Utilities â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    public void print() {
        Node<T> cur = head;
        System.out.print("Deque [front->rear]: [");
        while (cur != null) {
            System.out.print(cur.data);
            if (cur.next != null) System.out.print(", ");
            cur = cur.next;
        }
        System.out.println("]");
    }

    // â”€â”€â”€ Application: Sliding Window Maximum â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /**
     * Returns an array of max values for each sliding window of size k.
     * Uses a monotonic deque (stores indices).
     * Time: O(n), Space: O(k)
     *
     * Example: arr=[1,3,-1,-3,5,3,6,7], k=3 -> [3,3,5,5,6,7]
     */
    public static int[] slidingWindowMax(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n - k + 1];
        java.util.Deque<Integer> dq = new java.util.ArrayDeque<>();   // stores indices

        for (int i = 0; i < n; i++) {
            // Remove indices out of current window
            while (!dq.isEmpty() && dq.peekFirst() < i - k + 1)
                dq.pollFirst();
            // Remove smaller elements from rear (maintain decreasing order)
            while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i])
                dq.pollLast();
            dq.addLast(i);
            if (i >= k - 1) result[i - k + 1] = arr[dq.peekFirst()];
        }
        return result;
    }

    // â”€â”€â”€ Application: Palindrome check using Deque â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static boolean isPalindrome(String s) {
        Deque<Character> dq = new Deque<>();
        for (char c : s.toCharArray()) dq.addLast(c);
        while (dq.size() > 1) {
            if (!dq.removeFirst().equals(dq.removeLast())) return false;
        }
        return true;
    }

    // â”€â”€â”€ Main â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        dq.addLast(10); dq.addLast(20); dq.addLast(30);
        dq.addFirst(5);
        dq.print();
        System.out.println("peekFirst: " + dq.peekFirst() + "  peekLast: " + dq.peekLast());
        System.out.println("removeFirst: " + dq.removeFirst());
        System.out.println("removeLast : " + dq.removeLast());
        dq.print();

        System.out.println("\n-- Sliding Window Maximum --");
        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] maxes = slidingWindowMax(arr, 3);
        System.out.print("Window maxes (k=3): [");
        for (int i = 0; i < maxes.length; i++) {
            System.out.print(maxes[i]);
            if (i < maxes.length - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("\n-- Palindrome Check --");
        System.out.println("\"racecar\" -> " + isPalindrome("racecar"));
        System.out.println("\"hello\"   -> " + isPalindrome("hello"));
    }
}

