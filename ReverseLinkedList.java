//AYUSH SINGH PAWAR
class Node {
    int data;
    Node next;
}
public class ReverseLinkedList {
    /**
     * Reverses a singly linked list iteratively in-place.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param head The head node of the linked list
     * @return The new head node of the reversed list
     */
    static Node reverse(Node head) {
        Node newHead = null;
        while (head != null) {
            Node temp = head;
            head = head.next;
            temp.next = newHead;
            newHead = temp;
        }
        return newHead;
    }
    static void print(Node head) {
        Node t = head;
        while (t != null) {
            System.out.print(t.data + " ");
            t = t.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Node head = new Node();
        head.data = 1;
        head.next = new Node();
        head.next.data = 2;
        head.next.next = new Node();
        head.next.next.data = 3;
        head.next.next.next = new Node();
        head.next.next.next.data = 4;
        System.out.println("Original List:");
        print(head);
        head = reverse(head);
        System.out.println("Reversed List:");
        print(head);
    }
}
