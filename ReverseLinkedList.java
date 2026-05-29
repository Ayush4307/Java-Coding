class Node {
    int data;
    Node next;
}
public class ReverseLinkedList {
    static Node reverse(Node head) {
        if (head == null || head.next == null) return head;
        Node newHead = null;
        while (head != null) {
            Node temp = head;
            head = head.next;
            temp.next = newHead;
            newHead = temp;
        }
        return newHead;
    }
    static Node reverseRecursive(Node head) {
        // Base case: if list is empty or has only one node
        if (head == null || head.next == null) {
            return head;
        }
        Node newHead = reverseRecursive(head.next);
        // Put current node at the end of reversed list
        head.next.next = head;
        head.next = null;
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

        System.out.println("\nReversing back with recursion:");
        head = reverseRecursive(head);
        print(head);
    }
}
