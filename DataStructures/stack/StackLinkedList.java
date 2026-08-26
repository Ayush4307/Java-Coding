/**
 * StackLinkedList.java
 *
 * Stack implementation using a Singly Linked List (no size limit).
 *
 * Operations:
 *  - push(T)  : O(1)
 *  - pop()    : O(1)
 *  - peek()   : O(1)
 *  - size()   : O(1)
 *
 * Advantage over array stack: grows dynamically, no capacity limit.
 * Disadvantage: extra memory per node for pointer.
 *
 * Space Complexity: O(n)
 */
package DataStructures.stack;

public class StackLinkedList<T> {

    // ─── Node ─────────────────────────────────────────────────────────────────
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }

    // ─── Fields ───────────────────────────────────────────────────────────────
    private Node<T> top;
    private int size;

    // ─── Core Operations ─────────────────────────────────────────────────────
    /** Push element. O(1). */
    public void push(T item) {
        Node<T> node = new Node<>(item);
        node.next = top;
        top = node;
        size++;
    }

    /** Pop top element. O(1). */
    public T pop() {
        if (isEmpty()) throw new java.util.EmptyStackException();
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }

    /** Peek top without removing. O(1). */
    public T peek() {
        if (isEmpty()) throw new java.util.EmptyStackException();
        return top.data;
    }

    public boolean isEmpty() { return top == null; }
    public int size()        { return size; }

    public void print() {
        Node<T> cur = top;
        System.out.print("Stack (top->bottom): [");
        while (cur != null) {
            System.out.print(cur.data);
            if (cur.next != null) System.out.print(", ");
            cur = cur.next;
        }
        System.out.println("]");
    }

    // ─── Application: Sort a stack using an auxiliary stack ──────────────────
    /**
     * Sorts a stack so the smallest element is on top.
     * Uses only stack operations (no array/list).
     * Time: O(n^2), Space: O(n)
     */
    public static StackLinkedList<Integer> sortStack(StackLinkedList<Integer> input) {
        StackLinkedList<Integer> sorted = new StackLinkedList<>();
        while (!input.isEmpty()) {
            int tmp = input.pop();
            while (!sorted.isEmpty() && sorted.peek() < tmp)
                input.push(sorted.pop());
            sorted.push(tmp);
        }
        return sorted;
    }

    // ─── Application: Reverse a stack using recursion ────────────────────────
    /** Inserts element at bottom of stack. O(n) */
    private static <T> void insertAtBottom(StackLinkedList<T> stack, T item) {
        if (stack.isEmpty()) { stack.push(item); return; }
        T top = stack.pop();
        insertAtBottom(stack, item);
        stack.push(top);
    }

    /** Reverses a stack using recursion (no extra DS). O(n^2). */
    public static <T> void reverseStack(StackLinkedList<T> stack) {
        if (stack.isEmpty()) return;
        T top = stack.pop();
        reverseStack(stack);
        insertAtBottom(stack, top);
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        StackLinkedList<Integer> stack = new StackLinkedList<>();
        int[] vals = {5, 1, 4, 2, 3};
        for (int v : vals) stack.push(v);
        stack.print();

        System.out.println("\n-- Sorted Stack (min on top) --");
        StackLinkedList<Integer> sorted = sortStack(stack);
        sorted.print();

        System.out.println("\n-- Reverse Stack --");
        StackLinkedList<String> strStack = new StackLinkedList<>();
        strStack.push("A"); strStack.push("B"); strStack.push("C");
        strStack.print();
        reverseStack(strStack);
        System.out.print("Reversed: "); strStack.print();
    }
}
