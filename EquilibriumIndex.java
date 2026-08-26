import java.util.ArrayList;
import java.util.List;

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
public class EquilibriumIndex {

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

    /**
     * Finds ALL equilibrium indices in the array (not just the first one).
     *
     * An array can have multiple equilibrium points. For example:
     * {0, 0, 0} has equilibrium at indices 0, 1, and 2.
     *
     * Approach: Same prefix-sum technique as findEquilibrium(), but instead of
     * returning on the first match, we collect all matching indices into a list.
     *
     * Why return a List?
     * - Real-world problems may require all equilibria (e.g., partitioning arrays).
     * - The list grows only as large as the number of equilibrium points found,
     *   which in the worst case is O(n) (e.g., all-zeros array).
     *
     * Time Complexity  : O(n) — two linear passes (total sum + scan).
     * Space Complexity : O(k) where k = number of equilibrium indices found.
     *                    O(1) auxiliary space excluding the output list.
     *
     * @param arr the input array
     * @return a List of all equilibrium indices (empty list if none found)
     */
    public static List<Integer> findAllEquilibriumIndices(int[] arr) {
        List<Integer> result = new ArrayList<>();
        if (arr == null || arr.length == 0) return result;

        // Step 1: Compute full total sum — O(n)
        int totalSum = 0;
        for (int x : arr) {
            totalSum += x;
        }

        // Step 2: Scan and collect ALL equilibrium positions
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i]; // rightSum = totalSum after removing arr[i]

            if (leftSum == totalSum) {
                result.add(i); // Collect instead of returning early
            }

            leftSum += arr[i];
        }

        return result;
    }

    /**
     * Finds the first equilibrium index using a naive brute-force approach.
     *
     * Approach (Brute Force):
     * For every index i, recompute the left sum (arr[0..i-1]) and right sum
     * (arr[i+1..n-1]) using nested loops and compare them.
     *
     * Why include this?
     * - Shows the "obvious" first solution most beginners reach for.
     * - Demonstrates why it is suboptimal: repeated summation work.
     * - Clearly highlights the improvement that the prefix-sum approach provides.
     *
     * Time Complexity  : O(n²) — for each of the n indices, we compute two sums
     *                    each of which takes O(n) time in the worst case.
     * Space Complexity : O(1) — no extra data structures; only scalar accumulators.
     *
     * Verdict: NOT recommended for large inputs. Use findEquilibrium() instead.
     *
     * @param arr the input array
     * @return the first equilibrium index, or -1 if none exists
     */
    public static int findEquilibriumBruteForce(int[] arr) {
        if (arr == null || arr.length == 0) return -1;

        int n = arr.length;

        // Outer loop: try each index as a potential equilibrium — O(n)
        for (int i = 0; i < n; i++) {

            // Compute left sum: sum of arr[0..i-1] — O(n) per iteration
            int leftSum = 0;
            for (int j = 0; j < i; j++) {
                leftSum += arr[j];
            }

            // Compute right sum: sum of arr[i+1..n-1] — O(n) per iteration
            int rightSum = 0;
            for (int j = i + 1; j < n; j++) {
                rightSum += arr[j];
            }

            // Total per outer iteration: O(n) + O(n) = O(n)
            // Combined with outer loop: O(n) * O(n) = O(n²)
            if (leftSum == rightSum) {
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println("Equilibrium Index: " + findEquilibrium(arr));

        int[] arr2 = {1, 2, 3};
        System.out.println("Equilibrium Index (no equilibrium): " + findEquilibrium(arr2));

        int[] arr3 = {5};
        System.out.println("Equilibrium Index (single element): " + findEquilibrium(arr3));

        // Demo: findAllEquilibriumIndices
        int[] arr4 = {0, 0, 0, 0};
        System.out.println("\nAll equilibrium indices in {0,0,0,0}: "
                + findAllEquilibriumIndices(arr4));

        int[] arr5 = {1, 3, 5, 2, 2};
        System.out.println("All equilibrium indices in {1,3,5,2,2}: "
                + findAllEquilibriumIndices(arr5));

        // Demo: brute-force vs optimal — same result, different complexity
        int[] arr6 = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println("\nBrute Force [O(n²)]: " + findEquilibriumBruteForce(arr6));
        System.out.println("Optimal     [O(n)] : " + findEquilibrium(arr6));
    }
}
