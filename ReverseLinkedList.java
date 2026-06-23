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

    // Default constructor removed as it's never used

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

        // Demo: reverseInGroups
        Node head2 = new Node(1);
        head2.next = new Node(2);
        head2.next.next = new Node(3);
        head2.next.next.next = new Node(4);
        head2.next.next.next.next = new Node(5);
        head2.next.next.next.next.next = new Node(6);
        System.out.println("\nOriginal List for group reversal:");
        print(head2);
        head2 = reverseInGroups(head2, 3); // Reverse in groups of 3
        System.out.println("After reverseInGroups(k=3) [O(n) time, O(n/k) space]:");
        print(head2);

        // Demo: isPalindrome
        Node pal1 = new Node(1);
        pal1.next = new Node(2);
        pal1.next.next = new Node(2);
        pal1.next.next.next = new Node(1);
        System.out.println("\nList: 1->2->2->1 isPalindrome? " + isPalindrome(pal1));

        Node pal2 = new Node(1);
        pal2.next = new Node(2);
        pal2.next.next = new Node(3);
        System.out.println("List: 1->2->3      isPalindrome? " + isPalindrome(pal2));
    }

    /**
     * Reverses a linked list in groups of k nodes at a time.
     *
     * Example: List = 1->2->3->4->5->6, k=3
     * Result  = 3->2->1->6->5->4
     *
     * Approach (Recursive):
     * 1. Reverse the first k nodes iteratively (same 3-pointer technique).
     * 2. Recursively reverse the rest of the list (from node k+1 onwards).
     * 3. Connect the tail of the first reversed group to the head of the recursive result.
     *
     * Why recursion for group reversal?
     * - Each group of k nodes is processed exactly once.
     * - Recursion naturally "chops off" k nodes each time and processes the rest.
     * - The recursive call stack depth is n/k (one frame per group), not n.
     * - Cleaner to implement than a purely iterative doubly-nested approach.
     *
     * Time Complexity  : O(n)   — every node is visited exactly once across all groups.
     * Space Complexity : O(n/k) — recursion stack depth equals number of groups (n/k).
     *                   In worst case k=1: O(n). In best case k=n: O(1).
     *
     * @param head the head of the linked list
     * @param k    the group size (must be >= 1)
     * @return the new head of the list after group reversal
     */
    static Node reverseInGroups(Node head, int k) {
        if (head == null || k <= 1) return head;

        Node curr = head;
        Node prev = null;
        int count = 0;

        // Step 1: Reverse the first k nodes of the current group — O(k)
        while (curr != null && count < k) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count++;
        }

        // 'prev' is now the new head of this reversed group
        // 'curr' points to the (k+1)-th node (start of next group)

        // Step 2: Recursively reverse the remaining list and link it
        // head is now the TAIL of the reversed group — connect it to the next group
        if (curr != null) {
            head.next = reverseInGroups(curr, k); // Recursive call: O(n/k) depth
        }

        return prev; // New head of this group
    }

    /**
     * Checks if the singly linked list is a palindrome.
     *
     * A palindrome reads the same forwards and backwards.
     * Example: 1->2->2->1 is a palindrome. 1->2->3 is not.
     *
     * Approach (Slow-Fast Pointer + In-Place Reversal):
     * 1. Use slow/fast pointers to find the middle of the list.
     *    - slow moves 1 step; fast moves 2 steps.
     *    - When fast reaches end, slow is at the midpoint.
     * 2. Reverse the second half of the list in-place (using our reverse() method).
     * 3. Compare the first half and reversed second half node-by-node.
     * 4. Restore the list by reversing the second half back (optional, for immutability).
     *
     * Why this approach?
     * - Naive approach: copy all values to an array, check palindrome — O(n) space.
     * - This approach avoids extra memory entirely.
     * - The in-place second-half reversal reuses our existing reverse() — DRY code.
     * - Slow/fast pointer is the canonical O(1)-space midpoint finding technique.
     *
     * Time Complexity  : O(n) — one pass to find middle + one pass to reverse + one pass to compare.
     * Space Complexity : O(1) — only pointer variables; no extra data structures.
     *
     * @param head the head of the linked list to check
     * @return true if the list is a palindrome, false otherwise
     */
    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;

        // Step 1: Find the middle using slow/fast pointers — O(n/2)
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;       // Moves 1 step
            fast = fast.next.next;  // Moves 2 steps
        }
        // 'slow' is now at the start of the second half

        // Step 2: Reverse the second half in-place — O(n/2), O(1) space
        Node secondHalfHead = reverse(slow);
        Node secondHalfCopy = secondHalfHead; // Save to restore later

        // Step 3: Compare first and reversed second half — O(n/2)
        Node firstHalf = head;
        boolean isPalin = true;
        while (secondHalfHead != null) {
            if (firstHalf.data != secondHalfHead.data) {
                isPalin = false;
                break;
            }
            firstHalf = firstHalf.next;
            secondHalfHead = secondHalfHead.next;
        }

        // Step 4: Restore the list (reverse the second half back) — O(n/2)
        reverse(secondHalfCopy);

        return isPalin;
    }
}
