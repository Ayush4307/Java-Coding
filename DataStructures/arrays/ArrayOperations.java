/**
 * ArrayOperations.java
 *
 * Demonstrates fundamental array operations in Java:
 *  - Insertion, Deletion, Search (Linear & Binary)
 *  - Rotation (left & right), Reversal
 *  - Finding max/min, sum, second-largest
 *
 * Time Complexities:
 *  - Access         : O(1)
 *  - Search (linear): O(n)
 *  - Search (binary): O(log n) [sorted array]
 *  - Insertion      : O(n) worst case (shift elements)
 *  - Deletion       : O(n) worst case (shift elements)
 *
 * Space Complexity  : O(n)
 */
public class ArrayOperations {

    // ─── Insert at given index ────────────────────────────────────────────────
    /**
     * Inserts 'value' at 'index' in arr[0..n-1].
     * arr must have at least n+1 capacity.
     * @return new logical size (n + 1)
     */
    public static int insert(int[] arr, int n, int index, int value) {
        if (index < 0 || index > n) throw new IllegalArgumentException("Index out of bounds");
        for (int i = n; i > index; i--) {
            arr[i] = arr[i - 1];
        }
        arr[index] = value;
        return n + 1;
    }

    // ─── Delete at given index ────────────────────────────────────────────────
    /**
     * Deletes element at 'index' from arr[0..n-1].
     * @return new logical size (n - 1)
     */
    public static int delete(int[] arr, int n, int index) {
        if (index < 0 || index >= n) throw new IllegalArgumentException("Index out of bounds");
        for (int i = index; i < n - 1; i++) {
            arr[i] = arr[i + 1];
        }
        return n - 1;
    }

    // ─── Linear Search ────────────────────────────────────────────────────────
    /** Returns index of 'target' or -1 if not found. O(n) */
    public static int linearSearch(int[] arr, int n, int target) {
        for (int i = 0; i < n; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    // ─── Binary Search (sorted array) ────────────────────────────────────────
    /** Returns index of 'target' or -1. Requires sorted array. O(log n) */
    public static int binarySearch(int[] arr, int n, int target) {
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return -1;
    }

    // ─── Reverse ──────────────────────────────────────────────────────────────
    /** Reverses arr[0..n-1] in-place. O(n) */
    public static void reverse(int[] arr, int n) {
        int lo = 0, hi = n - 1;
        while (lo < hi) {
            int tmp = arr[lo]; arr[lo] = arr[hi]; arr[hi] = tmp;
            lo++; hi--;
        }
    }

    // ─── Left Rotation by k positions ────────────────────────────────────────
    /** Rotates arr left by k positions. O(n) */
    public static void rotateLeft(int[] arr, int n, int k) {
        k = k % n;
        reverse(arr, k);
        reverse(arr, n);         // reverse whole
        // Correct: reverse first k, reverse rest, reverse all
        // Using three-reversal trick:
        reverseRange(arr, 0, k - 1);
        reverseRange(arr, k, n - 1);
        reverseRange(arr, 0, n - 1);
    }

    /** Right rotation by k positions. O(n) */
    public static void rotateRight(int[] arr, int n, int k) {
        k = k % n;
        reverseRange(arr, 0, n - 1);
        reverseRange(arr, 0, k - 1);
        reverseRange(arr, k, n - 1);
    }

    private static void reverseRange(int[] arr, int lo, int hi) {
        while (lo < hi) {
            int tmp = arr[lo]; arr[lo] = arr[hi]; arr[hi] = tmp;
            lo++; hi--;
        }
    }

    // ─── Max & Min ────────────────────────────────────────────────────────────
    public static int max(int[] arr, int n) {
        int m = arr[0];
        for (int i = 1; i < n; i++) if (arr[i] > m) m = arr[i];
        return m;
    }

    public static int min(int[] arr, int n) {
        int m = arr[0];
        for (int i = 1; i < n; i++) if (arr[i] < m) m = arr[i];
        return m;
    }

    // ─── Second Largest ───────────────────────────────────────────────────────
    /** Returns second largest element. O(n) */
    public static int secondLargest(int[] arr, int n) {
        int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (arr[i] > first) { second = first; first = arr[i]; }
            else if (arr[i] > second && arr[i] != first) second = arr[i];
        }
        return second;
    }

    // ─── Sum ──────────────────────────────────────────────────────────────────
    public static long sum(int[] arr, int n) {
        long s = 0;
        for (int i = 0; i < n; i++) s += arr[i];
        return s;
    }

    // ─── Print ───────────────────────────────────────────────────────────────
    public static void print(int[] arr, int n) {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        int[] arr = new int[15];
        int n = 0;

        // Insert elements
        n = insert(arr, n, 0, 10);
        n = insert(arr, n, 1, 30);
        n = insert(arr, n, 2, 20);
        n = insert(arr, n, 3, 50);
        n = insert(arr, n, 4, 40);
        System.out.print("After insertions: "); print(arr, n);

        // Linear search
        System.out.println("Linear search 20 at index: " + linearSearch(arr, n, 20));

        // Max / Min / Second Largest
        System.out.println("Max: " + max(arr, n));
        System.out.println("Min: " + min(arr, n));
        System.out.println("Second Largest: " + secondLargest(arr, n));
        System.out.println("Sum: " + sum(arr, n));

        // Rotate left by 2
        rotateLeft(arr, n, 2);
        System.out.print("After left rotate by 2: "); print(arr, n);

        // Delete index 1
        n = delete(arr, n, 1);
        System.out.print("After delete index 1: "); print(arr, n);

        // Sort for binary search
        java.util.Arrays.sort(arr, 0, n);
        System.out.print("Sorted: "); print(arr, n);
        System.out.println("Binary search 40 at index: " + binarySearch(arr, n, 40));
    }
}
