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
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
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
