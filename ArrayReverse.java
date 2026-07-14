import java.util.Stack;

/**
 * ArrayReverse.java
 *
 * Problem: Reverse an array in-place.
 *
 * Approach: Two-Pointer Technique
 * ----------------------------------
 * We use two pointers — one starting from the beginning (left) and one from
 * the end (right). We swap the elements at both pointers and move them towards
 * each other until they meet in the middle.
 *
 * Why this approach?
 * - No extra array is needed (in-place), giving us O(1) space.
 * - A single pass through half the array is enough, giving O(n) time.
 * - Simple, clean, and cache-friendly compared to stack-based or recursive solutions.
 *
 * Time Complexity  : O(n) — we visit each element exactly once (n/2 swaps).
 * Space Complexity : O(1) — only a constant number of temp variables used.
 */
public class ArrayReverse {

    /**
     * Reverses the entire array in-place using the two-pointer technique.
     *
     * Time Complexity  : O(n) — n/2 swaps performed.
     * Space Complexity : O(1) — no additional data structures used.
     *
     * @param arr the integer array to reverse (modified in-place)
     */
    public static void reverseArray(int[] arr) {
        // Edge case: null or single-element arrays are already "reversed"
        if (arr == null || arr.length <= 1) return;

        int start = 0;
        int end = arr.length - 1;

        // Two-pointer swap: O(n/2) iterations = O(n)
        while (start < end) {
            // 1. Store the value at 'start' pointer temporarily
            int temp = arr[start];
            // 2. Overwrite 'start' value with 'end' value
            arr[start] = arr[end];
            // 3. Put the original 'start' value into the 'end' position
            arr[end] = temp;
            
            // Move pointers inward for the next iteration
            start++;
            end--;
        }
    }

    /**
     * Reverses a sub-range of the array in-place, from index 'from' to index 'to' (inclusive).
     *
     * Approach: Same two-pointer technique, but scoped to the given index range.
     * This is useful in algorithms like Rotate Array, where partial reversal is needed.
     *
     * Why sub-range reversal?
     * - Many classic problems (e.g., rotate array by k) decompose into 3 sub-reversals.
     * - Reusing this method keeps code DRY and the logic modular.
     *
     * Time Complexity  : O(to - from + 1) — proportional to the sub-range length.
     *                    In the worst case (full array), this is O(n).
     * Space Complexity : O(1) — only a temp variable; no extra memory allocated.
     *
     * @param arr  the array containing the sub-range
     * @param from the starting index (inclusive)
     * @param to   the ending index (inclusive)
     */
    public static void reverseSubArray(int[] arr, int from, int to) {
        // Validate index bounds before proceeding
        if (arr == null || from < 0 || to >= arr.length || from >= to) return;

        // Two-pointer approach scoped to [from, to]
        while (from < to) {
            int temp = arr[from];
            arr[from] = arr[to];
            arr[to] = temp;
            from++;
            to--;
        }
    }

    /**
     * Reverses the array using a Stack (LIFO) data structure.
     *
     * Approach: Push all elements onto a stack, then pop them back into the array.
     * A stack naturally reverses order because of its LIFO (Last-In, First-Out) property.
     *
     * Why show this approach?
     * - Demonstrates the conceptual link between stacks and reversal.
     * - Useful when elements arrive as a stream (not random-access array).
     *
     * Trade-off vs Two-Pointer:
     * - Both have O(n) time, but this uses O(n) extra space for the stack.
     * - Two-pointer is PREFERRED in production code since it is O(1) space.
     * - This approach is shown for educational comparison only.
     *
     * Time Complexity  : O(n) — one full pass to push, one full pass to pop.
     * Space Complexity : O(n) — stack holds all n elements simultaneously.
     *
     * @param arr the integer array to reverse (modified in-place via stack)
     */
    public static void reverseUsingStack(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        Stack<Integer> stack = new Stack<>();

        // Push all elements — O(n) time, O(n) space
        for (int val : arr) {
            stack.push(val);
        }

        // Pop back into array — stack reverses order automatically (LIFO)
        for (int i = 0; i < arr.length; i++) {
            arr[i] = stack.pop();
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println("Original array: " + java.util.Arrays.toString(arr));
        reverseArray(arr);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr));

        int[] arr2 = {1, 2, 3, 4};
        System.out.println("\nOriginal array (even elements): " + java.util.Arrays.toString(arr2));
        reverseArray(arr2);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr2));

        int[] arr3 = {42};
        System.out.println("\nOriginal array (single element): " + java.util.Arrays.toString(arr3));
        reverseArray(arr3);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr3));

        int[] arr4 = {};
        System.out.println("\nOriginal array (empty): " + java.util.Arrays.toString(arr4));
        reverseArray(arr4);
        System.out.println("Reversed array: " + java.util.Arrays.toString(arr4));

        // Demo: reverseSubArray
        int[] arr5 = {10, 20, 30, 40, 50, 60};
        System.out.println("\nOriginal array: " + java.util.Arrays.toString(arr5));
        reverseSubArray(arr5, 1, 4);
        System.out.println("After reverseSubArray(1,4): " + java.util.Arrays.toString(arr5));

        // Demo: reverseUsingStack (O(n) space — for educational comparison)
        int[] arr6 = {5, 10, 15, 20, 25};
        System.out.println("\nOriginal array: " + java.util.Arrays.toString(arr6));
        reverseUsingStack(arr6);
        System.out.println("Reversed via Stack [O(n) space]: " + java.util.Arrays.toString(arr6));
    }
}

//Time Complexity

//The algorithm uses two pointers (start and end) that move toward the center of the array. In each iteration, one pair of elements is swapped. Since approximately n/2 swaps are performed for an array of size n, the running time grows linearly with the size of the array.

//Time Complexity: O(n)

//Space Complexity

//The algorithm performs the reversal directly within the original array and uses only three additional variables (start, end, and temp) regardless of the input size.

//Space Complexity: O(1)

//Why This Approach is Used

//This approach is known as the Two-Pointer In-Place Reversal Technique. It is preferred because:

//It reverses the array without creating an additional array.
//It requires only constant extra memory.
//It achieves optimal linear-time performance.
//It is simple to implement and easy to understand.

//Thus, this method provides an efficient solution for reversing an array while minimizing both execution time and memory usage.
