/**
 * EqulibriumIndex.java
 *
 * Problem: Find the equilibrium index of an array.
 * An equilibrium index is one where the sum of elements to its left equals
 * the sum of elements to its right.
 *
 * Approach: Prefix Sum (Running Sum Trick)
 * -----------------------------------------
 * 1. Compute the total sum of all elements.
 * 2. Iterate through the array, maintaining a running leftSum.
 *    At each index i: rightSum = totalSum - arr[i] - leftSum
 * 3. If leftSum == rightSum, index i is an equilibrium point.
 *
 * Why this approach?
 * - Avoids recomputing left and right sums for every index (which would be O(n²)).
 * - One pre-computation pass + one scan = O(n) total, O(1) extra space.
 * - The "subtract current element" trick elegantly maintains left/right balance.
 *
 * Time Complexity  : O(n) — two linear passes over the array.
 * Space Complexity : O(1) — only scalar variables used (leftSum, totalSum).
 */
public class EqulibriumIndex {

    /**
     * Finds the first equilibrium index in the array.
     *
     * An equilibrium index i satisfies: sum(arr[0..i-1]) == sum(arr[i+1..n-1])
     *
     * Approach: Running prefix sum with one pre-computation pass.
     *
     * Time Complexity  : O(n) — one pass to compute totalSum, one pass to find equilibrium.
     * Space Complexity : O(1) — only leftSum and totalSum scalar variables.
     *
     * @param arr the input array
     * @return the first equilibrium index, or -1 if none exists
     */
    public static int findEquilibrium(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        // Step 1: Compute total sum — O(n)
        int totalSum = 0;
        for (int x : arr) {
            totalSum += x;
        }

        // Step 2: Scan array; at each i, rightSum = totalSum - arr[i] - leftSum
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            // Subtract current element to isolate the right portion
            totalSum -= arr[i];

            // Check if left sum equals right sum (equilibrium condition)
            if (leftSum == totalSum) {
                return i;
            }

            // Update left sum for next iteration
            leftSum += arr[i];
        }

        return -1; // No equilibrium index found
    }

    public static void main(String[] args) {
        int[] arr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println("Equilibrium Index: " + findEquilibrium(arr));

        int[] arr2 = {1, 2, 3};
        System.out.println("Equilibrium Index (no equilibrium): " + findEquilibrium(arr2));

        int[] arr3 = {5};
        System.out.println("Equilibrium Index (single element): " + findEquilibrium(arr3));
    }
}
