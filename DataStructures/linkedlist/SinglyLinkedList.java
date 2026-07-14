/**
 * SinglyLinkedList.java
 *
 * A complete implementation of a Singly Linked List.
 *
 * Operations:
 *  - addFirst, addLast, addAt(index)
 *  - removeFirst, removeLast, removeAt(index), removeValue
 *  - get, size, contains, indexOf
 *  - reverse (iterative + recursive)
 *  - detectCycle (Floyd's algorithm)
 *  - findMiddle (slow-fast pointer)
 *  - print
 *
 * Time Complexities:
 *  - addFirst / removeFirst : O(1)
 *  - addLast / removeLast   : O(n) without tail pointer
 *  - addAt / removeAt       : O(n)
 *  - reverse                : O(n)
 *  - detectCycle            : O(n)
 *  - findMiddle             : O(n)
 *
 * Space Complexity: O(n)
 */
public class SinglyLinkedList<T> {

    // ─── Node ─────────────────────────────────────────────────────────────────
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    // ─── Fields ───────────────────────────────────────────────────────────────
    private Node<T> head;
    private int size;

    // ─── Add ──────────────────────────────────────────────────────────────────
    /** O(1) */
    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        node.next = head;
        head = node;
        size++;
    }

    /** O(n) */
    public void addLast(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) { head = node; size++; return; }
        Node<T> cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = node;
        size++;
    }

    /** O(n) */
    public void addAt(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) { addFirst(data); return; }
        Node<T> prev = getNode(index - 1);
        Node<T> node = new Node<>(data);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    // ─── Remove ───────────────────────────────────────────────────────────────
    /** O(1) */
    public T removeFirst() {
        if (head == null) throw new java.util.NoSuchElementException();
        T data = head.data;
        head = head.next;
        size--;
        return data;
    }

    /** O(n) */
    public T removeLast() {
        if (head == null) throw new java.util.NoSuchElementException();
        if (head.next == null) { T d = head.data; head = null; size--; return d; }
        Node<T> prev = head;
        while (prev.next.next != null) prev = prev.next;
        T data = prev.next.data;
        prev.next = null;
        size--;
        return data;
    }

    /** O(n) */
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0) return removeFirst();
        Node<T> prev = getNode(index - 1);
        T data = prev.next.data;
        prev.next = prev.next.next;
        size--;
        return data;
    }

    /** Removes first occurrence. O(n) */
    public boolean removeValue(T data) {
        int idx = indexOf(data);
        if (idx == -1) return false;
        removeAt(idx);
        return true;
    }

    // ─── Access ───────────────────────────────────────────────────────────────
    public T get(int index) { return getNode(index).data; }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        Node<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur;
    }

    // ─── Search ───────────────────────────────────────────────────────────────
    public int indexOf(T data) {
        Node<T> cur = head; int i = 0;
        while (cur != null) {
            if ((data == null ? cur.data == null : data.equals(cur.data))) return i;
            cur = cur.next; i++;
        }
        return -1;
    }

    public boolean contains(T data) { return indexOf(data) != -1; }

    // ─── Reverse (Iterative) ──────────────────────────────────────────────────
    /** Reverses list in-place. O(n) time, O(1) space. */
    public void reverse() {
        Node<T> prev = null, cur = head, next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        head = prev;
    }

    // ─── Reverse (Recursive) ─────────────────────────────────────────────────
    /** O(n) time, O(n) stack space. */
    public void reverseRecursive() { head = reverseRec(head); }

    private Node<T> reverseRec(Node<T> node) {
        if (node == null || node.next == null) return node;
        Node<T> newHead = reverseRec(node.next);
        node.next.next = node;
        node.next = null;
        return newHead;
    }

    // ─── Find Middle (Slow-Fast Pointer) ─────────────────────────────────────
    /** Returns middle element. For even-length, returns second middle. O(n). */
    public T findMiddle() {
        if (head == null) throw new java.util.NoSuchElementException();
        Node<T> slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow.data;
    }

    // ─── Detect Cycle (Floyd's) ───────────────────────────────────────────────
    /** Returns true if list has a cycle. O(n) time, O(1) space. */
    public boolean detectCycle() {
        Node<T> slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // ─── Utilities ───────────────────────────────────────────────────────────
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    public void print() {
        Node<T> cur = head;
        System.out.print("HEAD -> ");
        while (cur != null) { System.out.print(cur.data + " -> "); cur = cur.next; }
        System.out.println("NULL");
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();

        // Build list
        for (int i = 1; i <= 6; i++) list.addLast(i * 10);
        list.print();

        System.out.println("Size   : " + list.size());
        System.out.println("Middle : " + list.findMiddle());
        System.out.println("Contains 30? " + list.contains(30));

        // Reverse
        list.reverse();
        System.out.print("Reversed: "); list.print();

        // Remove operations
        System.out.println("removeFirst: " + list.removeFirst());
        System.out.println("removeLast : " + list.removeLast());
        System.out.print("After removes: "); list.print();

        // Cycle detection
        System.out.println("Has cycle? " + list.detectCycle());

        // Add at index
        list.addAt(1, 999);
        System.out.print("After addAt(1, 999): "); list.print();
    }
}
