package DataStructures.linkedlist;

/**
 * DoublyLinkedList.java
 *
 * A complete Doubly Linked List implementation with prev & next pointers.
 *
 * Operations:
 *  - addFirst, addLast, addAt(index)
 *  - removeFirst, removeLast, removeAt(index)
 *  - get, size, contains, indexOf
 *  - reverse, printForward, printBackward
 *
 * Time Complexities:
 *  - addFirst / removeFirst : O(1)
 *  - addLast  / removeLast  : O(1)  â† advantage of tail pointer
 *  - addAt  / removeAt      : O(n)
 *  - reverse                : O(n)
 *
 * Space Complexity: O(n)
 */
public class DoublyLinkedList<T> {

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
    /** O(1) */
    public void addFirst(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) { head = tail = node; }
        else { node.next = head; head.prev = node; head = node; }
        size++;
    }

    /** O(1) using tail pointer */
    public void addLast(T data) {
        Node<T> node = new Node<>(data);
        if (tail == null) { head = tail = node; }
        else { tail.next = node; node.prev = tail; tail = node; }
        size++;
    }

    /** O(n) */
    public void addAt(int index, T data) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0)    { addFirst(data); return; }
        if (index == size) { addLast(data);  return; }
        Node<T> cur = getNode(index);
        Node<T> node = new Node<>(data);
        node.next = cur;
        node.prev = cur.prev;
        cur.prev.next = node;
        cur.prev = node;
        size++;
    }

    // â”€â”€â”€ Remove â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** O(1) */
    public T removeFirst() {
        if (head == null) throw new java.util.NoSuchElementException();
        T data = head.data;
        if (head == tail) { head = tail = null; }
        else { head = head.next; head.prev = null; }
        size--;
        return data;
    }

    /** O(1) */
    public T removeLast() {
        if (tail == null) throw new java.util.NoSuchElementException();
        T data = tail.data;
        if (head == tail) { head = tail = null; }
        else { tail = tail.prev; tail.next = null; }
        size--;
        return data;
    }

    /** O(n) */
    public T removeAt(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        if (index == 0)        return removeFirst();
        if (index == size - 1) return removeLast();
        Node<T> cur = getNode(index);
        cur.prev.next = cur.next;
        cur.next.prev = cur.prev;
        size--;
        return cur.data;
    }

    // â”€â”€â”€ Access â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public T get(int index) { return getNode(index).data; }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index);
        // Optimise: traverse from nearest end
        Node<T> cur;
        if (index < size / 2) {
            cur = head;
            for (int i = 0; i < index; i++) cur = cur.next;
        } else {
            cur = tail;
            for (int i = size - 1; i > index; i--) cur = cur.prev;
        }
        return cur;
    }

    // â”€â”€â”€ Search â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int indexOf(T data) {
        Node<T> cur = head; int i = 0;
        while (cur != null) {
            if (data == null ? cur.data == null : data.equals(cur.data)) return i;
            cur = cur.next; i++;
        }
        return -1;
    }

    public boolean contains(T data) { return indexOf(data) != -1; }

    // â”€â”€â”€ Reverse â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** Swaps prev/next pointers of every node. O(n). */
    public void reverse() {
        Node<T> cur = head;
        while (cur != null) {
            Node<T> tmp = cur.prev;
            cur.prev = cur.next;
            cur.next = tmp;
            cur = cur.prev;   // move to original next
        }
        // swap head and tail
        Node<T> tmp = head; head = tail; tail = tmp;
    }

    // â”€â”€â”€ Print â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void printForward() {
        Node<T> cur = head;
        System.out.print("NULL <-> ");
        while (cur != null) { System.out.print(cur.data + " <-> "); cur = cur.next; }
        System.out.println("NULL");
    }

    public void printBackward() {
        Node<T> cur = tail;
        System.out.print("NULL <-> ");
        while (cur != null) { System.out.print(cur.data + " <-> "); cur = cur.prev; }
        System.out.println("NULL");
    }

    // â”€â”€â”€ Utilities â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    // â”€â”€â”€ Main â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static void main(String[] args) {
        DoublyLinkedList<Integer> dll = new DoublyLinkedList<>();

        for (int i = 1; i <= 5; i++) dll.addLast(i * 10);
        dll.printForward();
        dll.printBackward();

        dll.addFirst(0);
        dll.addAt(3, 999);
        System.out.print("After inserts: "); dll.printForward();

        System.out.println("Remove at 3: " + dll.removeAt(3));
        System.out.println("RemoveFirst : " + dll.removeFirst());
        System.out.println("RemoveLast  : " + dll.removeLast());
        System.out.print("After removes: "); dll.printForward();

        dll.reverse();
        System.out.print("Reversed: "); dll.printForward();
    }
}

