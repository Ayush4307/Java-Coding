package DataStructures.trees;

/**
 * BinarySearchTree.java
 *
 * Binary Search Tree (BST) with:
 *  - insert, search, delete
 *  - Inorder, Preorder, Postorder traversal
 *  - Min, Max
 *  - Successor, Predecessor
 *  - Floor, Ceiling
 *  - Count nodes, Height
 *  - Validate BST
 *  - LCA (Lowest Common Ancestor)
 *  - Convert sorted array to balanced BST
 *
 * BST Property: left.data < node.data < right.data
 *
 * Time Complexities (average / worst):
 *  - Search / Insert / Delete : O(log n) / O(n)
 *
 * Space Complexity: O(n)
 */
public class BinarySearchTree {

    // â”€â”€â”€ Node â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    static class Node {
        int data;
        Node left, right;
        Node(int data) { this.data = data; }
    }

    // â”€â”€â”€ Root â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    private Node root;

    // â”€â”€â”€ Insert â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void insert(int data) { root = insertRec(root, data); }

    private Node insertRec(Node node, int data) {
        if (node == null) return new Node(data);
        if (data < node.data)      node.left  = insertRec(node.left,  data);
        else if (data > node.data) node.right = insertRec(node.right, data);
        // duplicate: ignore
        return node;
    }

    // â”€â”€â”€ Search â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public boolean search(int data) { return searchRec(root, data); }

    private boolean searchRec(Node node, int data) {
        if (node == null) return false;
        if (data == node.data) return true;
        return data < node.data ? searchRec(node.left, data) : searchRec(node.right, data);
    }

    // â”€â”€â”€ Delete â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /**
     * Three cases:
     *  1. Leaf   â†’ just remove
     *  2. One child â†’ replace with child
     *  3. Two children â†’ replace with inorder successor (min of right subtree)
     */
    public void delete(int data) { root = deleteRec(root, data); }

    private Node deleteRec(Node node, int data) {
        if (node == null) return null;
        if (data < node.data)      node.left  = deleteRec(node.left,  data);
        else if (data > node.data) node.right = deleteRec(node.right, data);
        else {
            if (node.left  == null) return node.right;
            if (node.right == null) return node.left;
            // Find inorder successor (min of right subtree)
            node.data = minNode(node.right).data;
            node.right = deleteRec(node.right, node.data);
        }
        return node;
    }

    // â”€â”€â”€ Min / Max â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int min() {
        if (root == null) throw new java.util.NoSuchElementException();
        return minNode(root).data;
    }
    private Node minNode(Node n) { while (n.left != null) n = n.left; return n; }

    public int max() {
        if (root == null) throw new java.util.NoSuchElementException();
        Node n = root;
        while (n.right != null) n = n.right;
        return n.data;
    }

    // â”€â”€â”€ Inorder Successor & Predecessor â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public Integer successor(int data) {
        Node suc = null, cur = root;
        while (cur != null) {
            if (data < cur.data) { suc = cur; cur = cur.left; }
            else if (data > cur.data) cur = cur.right;
            else { if (cur.right != null) { suc = minNode(cur.right); } break; }
        }
        return suc == null ? null : suc.data;
    }

    public Integer predecessor(int data) {
        Node pred = null, cur = root;
        while (cur != null) {
            if (data > cur.data) { pred = cur; cur = cur.right; }
            else if (data < cur.data) cur = cur.left;
            else { if (cur.left != null) { Node n = cur.left; while (n.right != null) n = n.right; pred = n; } break; }
        }
        return pred == null ? null : pred.data;
    }

    // â”€â”€â”€ Floor / Ceiling â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    /** Largest value â‰¤ key */
    public Integer floor(int key) {
        Node res = null, cur = root;
        while (cur != null) {
            if (cur.data == key) return key;
            if (cur.data < key) { res = cur; cur = cur.right; }
            else cur = cur.left;
        }
        return res == null ? null : res.data;
    }

    /** Smallest value â‰¥ key */
    public Integer ceiling(int key) {
        Node res = null, cur = root;
        while (cur != null) {
            if (cur.data == key) return key;
            if (cur.data > key) { res = cur; cur = cur.left; }
            else cur = cur.right;
        }
        return res == null ? null : res.data;
    }

    // â”€â”€â”€ Height â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int height() { return heightRec(root); }
    private int heightRec(Node n) {
        if (n == null) return -1;
        return 1 + Math.max(heightRec(n.left), heightRec(n.right));
    }

    // â”€â”€â”€ Count â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public int count() { return countRec(root); }
    private int countRec(Node n) { return n == null ? 0 : 1 + countRec(n.left) + countRec(n.right); }

    // â”€â”€â”€ Validate BST â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public boolean isValidBST() { return validateRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE); }
    private boolean validateRec(Node n, int min, int max) {
        if (n == null) return true;
        if (n.data <= min || n.data >= max) return false;
        return validateRec(n.left, min, n.data) && validateRec(n.right, n.data, max);
    }

    // â”€â”€â”€ LCA (Lowest Common Ancestor) â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public Integer lca(int a, int b) {
        Node res = lcaRec(root, a, b);
        return res == null ? null : res.data;
    }
    private Node lcaRec(Node n, int a, int b) {
        if (n == null) return null;
        if (a < n.data && b < n.data) return lcaRec(n.left,  a, b);
        if (a > n.data && b > n.data) return lcaRec(n.right, a, b);
        return n;
    }

    // â”€â”€â”€ Sorted Array â†’ Balanced BST â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void buildFromSortedArray(int[] arr) { root = buildRec(arr, 0, arr.length - 1); }
    private Node buildRec(int[] arr, int lo, int hi) {
        if (lo > hi) return null;
        int mid = lo + (hi - lo) / 2;
        Node n = new Node(arr[mid]);
        n.left  = buildRec(arr, lo,     mid - 1);
        n.right = buildRec(arr, mid + 1, hi);
        return n;
    }

    // â”€â”€â”€ Traversals â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public void inorder()   { System.out.print("Inorder  : "); inRec(root);   System.out.println(); }
    public void preorder()  { System.out.print("Preorder : "); preRec(root);  System.out.println(); }
    public void postorder() { System.out.print("Postorder: "); postRec(root); System.out.println(); }

    private void inRec(Node n)   { if (n != null) { inRec(n.left);   System.out.print(n.data + " "); inRec(n.right); } }
    private void preRec(Node n)  { if (n != null) { System.out.print(n.data + " "); preRec(n.left); preRec(n.right); } }
    private void postRec(Node n) { if (n != null) { postRec(n.left); postRec(n.right); System.out.print(n.data + " "); } }

    // â”€â”€â”€ Main â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€â”€
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] vals = {50, 30, 70, 20, 40, 60, 80};
        for (int v : vals) bst.insert(v);

        bst.inorder();
        bst.preorder();
        bst.postorder();

        System.out.println("\nSearch 40: " + bst.search(40));
        System.out.println("Search 99: " + bst.search(99));
        System.out.println("Min: " + bst.min() + "  Max: " + bst.max());
        System.out.println("Successor of 40  : " + bst.successor(40));
        System.out.println("Predecessor of 40: " + bst.predecessor(40));
        System.out.println("Floor(55)  : " + bst.floor(55));
        System.out.println("Ceiling(55): " + bst.ceiling(55));
        System.out.println("Height: " + bst.height());
        System.out.println("Nodes : " + bst.count());
        System.out.println("Valid BST? " + bst.isValidBST());
        System.out.println("LCA(20,40) : " + bst.lca(20, 40));
        System.out.println("LCA(20,80) : " + bst.lca(20, 80));

        System.out.println("\n-- Delete 30 --");
        bst.delete(30);
        bst.inorder();

        System.out.println("\n-- Sorted Array â†’ BST --");
        BinarySearchTree bst2 = new BinarySearchTree();
        bst2.buildFromSortedArray(new int[]{1,2,3,4,5,6,7});
        bst2.inorder();
        System.out.println("Balanced? " + bst2.isValidBST());
    }
}

