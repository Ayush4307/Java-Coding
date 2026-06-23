/**
 * BinaryTree.java
 *
 * A complete Binary Tree implementation with:
 *  - Insertion (level-order)
 *  - Traversals: Inorder, Preorder, Postorder (recursive + iterative)
 *  - Level-order (BFS), Spiral/Zigzag traversal
 *  - Height, Diameter, Node count, Leaf count
 *  - Mirror / invert
 *  - Left & Right view
 *  - Check if balanced
 *
 * Time Complexities:
 *  - All traversals : O(n)
 *  - Height         : O(n)
 *  - Diameter       : O(n)
 *  - Insert         : O(n) (level-order BFS to find gap)
 *
 * Space Complexity: O(n)
 */
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

    // ─── Node ─────────────────────────────────────────────────────────────────
    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // ─── Root ─────────────────────────────────────────────────────────────────
    private Node root;

    // ─── Insert (level-order, fills left-to-right) ───────────────────────────
    /** Inserts a new node using BFS, filling level by level. O(n). */
    public void insert(int data) {
        Node newNode = new Node(data);
        if (root == null) { root = newNode; return; }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left == null)  { cur.left  = newNode; return; }
            else queue.add(cur.left);
            if (cur.right == null) { cur.right = newNode; return; }
            else queue.add(cur.right);
        }
    }

    // ─── Traversals (Recursive) ───────────────────────────────────────────────
    public void inorder()   { System.out.print("Inorder   : "); inRec(root);   System.out.println(); }
    public void preorder()  { System.out.print("Preorder  : "); preRec(root);  System.out.println(); }
    public void postorder() { System.out.print("Postorder : "); postRec(root); System.out.println(); }

    private void inRec(Node n)   { if (n == null) return; inRec(n.left);   System.out.print(n.data + " "); inRec(n.right); }
    private void preRec(Node n)  { if (n == null) return; System.out.print(n.data + " "); preRec(n.left); preRec(n.right); }
    private void postRec(Node n) { if (n == null) return; postRec(n.left); postRec(n.right); System.out.print(n.data + " "); }

    // ─── Inorder (Iterative using Stack) ─────────────────────────────────────
    public void inorderIterative() {
        Stack<Node> stack = new Stack<>();
        Node cur = root;
        System.out.print("Inorder IT: ");
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) { stack.push(cur); cur = cur.left; }
            cur = stack.pop();
            System.out.print(cur.data + " ");
            cur = cur.right;
        }
        System.out.println();
    }

    // ─── Level-order / BFS ───────────────────────────────────────────────────
    public void levelOrder() {
        if (root == null) return;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        System.out.print("LevelOrder: ");
        while (!q.isEmpty()) {
            Node cur = q.poll();
            System.out.print(cur.data + " ");
            if (cur.left  != null) q.add(cur.left);
            if (cur.right != null) q.add(cur.right);
        }
        System.out.println();
    }

    // ─── Level-order with levels ──────────────────────────────────────────────
    public void levelOrderByLevel() {
        if (root == null) return;
        Queue<Node> q = new LinkedList<>();
        q.add(root); int level = 0;
        while (!q.isEmpty()) {
            int sz = q.size();
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < sz; i++) {
                Node cur = q.poll();
                System.out.print(cur.data + " ");
                if (cur.left  != null) q.add(cur.left);
                if (cur.right != null) q.add(cur.right);
            }
            System.out.println(); level++;
        }
    }

    // ─── Spiral / Zigzag ─────────────────────────────────────────────────────
    public void spiralOrder() {
        if (root == null) return;
        Deque<Node> dq = new ArrayDeque<>();
        dq.addFirst(root); boolean ltr = false;
        System.out.print("Spiral    : ");
        while (!dq.isEmpty()) {
            int sz = dq.size();
            for (int i = 0; i < sz; i++) {
                if (ltr) {
                    Node cur = dq.pollLast();
                    System.out.print(cur.data + " ");
                    if (cur.right != null) dq.addFirst(cur.right);
                    if (cur.left  != null) dq.addFirst(cur.left);
                } else {
                    Node cur = dq.pollFirst();
                    System.out.print(cur.data + " ");
                    if (cur.left  != null) dq.addLast(cur.left);
                    if (cur.right != null) dq.addLast(cur.right);
                }
            }
            ltr = !ltr;
        }
        System.out.println();
    }

    // ─── Height ───────────────────────────────────────────────────────────────
    /** Height = number of edges on longest root-to-leaf path. O(n). */
    public int height() { return heightRec(root); }
    private int heightRec(Node n) {
        if (n == null) return -1;
        return 1 + Math.max(heightRec(n.left), heightRec(n.right));
    }

    // ─── Diameter ─────────────────────────────────────────────────────────────
    /** Diameter = longest path between any two nodes. O(n). */
    private int diameter = 0;
    public int diameter() {
        diameter = 0; diameterRec(root); return diameter;
    }
    private int diameterRec(Node n) {
        if (n == null) return 0;
        int lh = diameterRec(n.left), rh = diameterRec(n.right);
        diameter = Math.max(diameter, lh + rh);
        return 1 + Math.max(lh, rh);
    }

    // ─── Node count / Leaf count ──────────────────────────────────────────────
    public int countNodes()  { return countRec(root); }
    private int countRec(Node n) { return n == null ? 0 : 1 + countRec(n.left) + countRec(n.right); }

    public int countLeaves() { return leavesRec(root); }
    private int leavesRec(Node n) {
        if (n == null) return 0;
        if (n.left == null && n.right == null) return 1;
        return leavesRec(n.left) + leavesRec(n.right);
    }

    // ─── Mirror (Invert) ──────────────────────────────────────────────────────
    public void mirror() { mirrorRec(root); }
    private void mirrorRec(Node n) {
        if (n == null) return;
        Node tmp = n.left; n.left = n.right; n.right = tmp;
        mirrorRec(n.left); mirrorRec(n.right);
    }

    // ─── Left View ───────────────────────────────────────────────────────────
    public void leftView() {
        System.out.print("Left View : ");
        leftViewRec(root, 0, new int[]{-1});
        System.out.println();
    }
    private void leftViewRec(Node n, int level, int[] maxLevel) {
        if (n == null) return;
        if (level > maxLevel[0]) { System.out.print(n.data + " "); maxLevel[0] = level; }
        leftViewRec(n.left, level + 1, maxLevel);
        leftViewRec(n.right, level + 1, maxLevel);
    }

    // ─── Balanced check ───────────────────────────────────────────────────────
    /** Returns true if tree is height-balanced. O(n). */
    public boolean isBalanced() { return balancedHeight(root) != -1; }
    private int balancedHeight(Node n) {
        if (n == null) return 0;
        int lh = balancedHeight(n.left);
        if (lh == -1) return -1;
        int rh = balancedHeight(n.right);
        if (rh == -1) return -1;
        if (Math.abs(lh - rh) > 1) return -1;
        return 1 + Math.max(lh, rh);
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        int[] vals = {1, 2, 3, 4, 5, 6, 7};
        for (int v : vals) tree.insert(v);

        tree.inorder();
        tree.preorder();
        tree.postorder();
        tree.inorderIterative();
        tree.levelOrder();
        System.out.println();
        tree.levelOrderByLevel();
        tree.spiralOrder();

        System.out.println("\nHeight   : " + tree.height());
        System.out.println("Diameter : " + tree.diameter());
        System.out.println("Nodes    : " + tree.countNodes());
        System.out.println("Leaves   : " + tree.countLeaves());
        System.out.println("Balanced : " + tree.isBalanced());

        tree.leftView();

        tree.mirror();
        System.out.print("Mirrored inorder: ");
        tree.inorder();
    }
}
