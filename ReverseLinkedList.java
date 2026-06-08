/**
 * ReverseLinkedList.java
 *
 * Problem: Reverse a singly linked list.
 *
 * This file demonstrates TWO core approaches with different complexity profiles:
 *
 * ┌─────────────────────────────┬────────────┬──────────────────────────────────┐
 * │ Method                      │ Time       │ Space                            │
 * ├─────────────────────────────┼────────────┼──────────────────────────────────┤
 * │ reverse          (iterative)│ O(n)       │ O(1) — in-place, 3 pointers only │
 * │ reverseRecursive (recursive)│ O(n)       │ O(n) — call stack depth = n      │
 * └─────────────────────────────┴────────────┴──────────────────────────────────┘
 *
 * Why iterative is preferred?
 * - Same O(n) time but only O(1) space — no call stack overhead.
 * - For very long lists (e.g., 10,000+ nodes), recursion risks StackOverflowError.
 * - Iterative is always preferred in production unless readability is critical.
 */

/**
 * Represents a single node in a singly linked list.
 */
class Node {
    int data;
    Node next;

    /** Default constructor. */
    Node() {}

    /**
     * Convenience constructor to create a node with a given value.
     *
     * @param data the integer value stored in this node
     */
    Node(int data) {
        this.data = data;
    }
}

public class ReverseLinkedList {

    /**
     * Reverses a singly linked list iteratively using the three-pointer technique.
     *
     * Approach:
     * Maintain three pointers: prev (initially null), curr (starts at head), next.
     * At each step:
     *   1. Save curr.next in 'next' (to avoid losing the rest of the list).
     *   2. Point curr.next backward to prev (reversal step).
     *   3. Advance prev to curr, and curr to saved next.
     * When curr becomes null, prev is the new head of the reversed list.
     *
     * Why this approach?
     * - O(1) space — only 3 pointer variables regardless of list length.
     * - No recursion depth risk; safe for arbitrarily long lists.
     * - Clean and widely recognized as the canonical linked list reversal method.
     *
     * Time Complexity  : O(n) — each node is visited exactly once.
     * Space Complexity : O(1) — only prev, curr, temp pointer variables.
     *
     * @param head the head node of the linked list
     * @return the new head (originally the tail) of the reversed list
     */
    static Node reverse(Node head) {
        // Base case: empty list or single node — already reversed
        if (head == null || head.next == null) return head;

        Node newHead = null; // Will become the new head (prev pointer)
        while (head != null) {
            Node temp = head;       // Save current node
            head = head.next;       // Advance original head forward
            temp.next = newHead;    // Reverse the pointer
            newHead = temp;         // Move new head forward
        }
        return newHead;
    }

    /**
     * Reverses a singly linked list recursively.
     *
     * Approach:
     * Recurse to the last node (new head), then as the call stack unwinds:
     *   - head.next.next = head  (point the next node's next back to current)
     *   - head.next = null       (break the forward link to avoid cycles)
     * The last node found in the deepest call becomes and stays the new head.
     *
     * Why use this?
     * - Elegant and declarative — mirrors the recursive nature of linked lists.
     * - Useful for understanding recursion and call stack concepts.
     * - Trade-off: O(n) call stack depth; can throw StackOverflowError for large n.
     *
     * Time Complexity  : O(n) — one recursive call per node.
     * Space Complexity : O(n) — call stack grows to depth n (one frame per node).
     *
     * @param head the head node of the linked list
     * @return the new head of the reversed list
     */
    static Node reverseRecursive(Node head) {
        // Base case: if list is empty or has only one node
        if (head == null || head.next == null) {
            return head;
        }
        // Recurse to the end — O(n) depth call stack
        Node newHead = reverseRecursive(head.next);
        // Put current node at the end of reversed list
        head.next.next = head;
        head.next = null;  // Cut forward link to prevent cycle
        return newHead;
    }

    /**
     * Prints all nodes of the linked list to standard output.
     *
     * Time Complexity  : O(n) — traverses each node once.
     * Space Complexity : O(1) — only a single traversal pointer.
     *
     * @param head the head node of the list to print
     */
    static void print(Node head) {
        Node t = head;
        while (t != null) {
            System.out.print(t.data + " ");
            t = t.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Build list: 1 -> 2 -> 3 -> 4
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);

        System.out.println("Original List:");
        print(head);

        head = reverse(head);  // Iterative: O(n) time, O(1) space
        System.out.println("Reversed List [Iterative, O(1) space]:");
        print(head);

        System.out.println("\nReversing back with recursion [O(n) space]:");
        head = reverseRecursive(head);
        print(head);
    }
}
