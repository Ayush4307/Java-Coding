/**
 * Fibonacci.java
 *
 * Problem: Compute the N-th Fibonacci number.
 * Fibonacci sequence: F(0)=0, F(1)=1, F(n)=F(n-1)+F(n-2)
 *
 * This file demonstrates FIVE approaches with different time/space trade-offs:
 *
 * ┌─────────────────────────────┬────────────┬──────────────┐
 * │ Method                      │ Time       │ Space        │
 * ├─────────────────────────────┼────────────┼──────────────┤
 * │ fibRecursive   (naive)      │ O(2^n) !!  │ O(n)         │
 * │ fibDP          (DP array)   │ O(n)       │ O(n)         │
 * │ fibOptimized   (2 vars)     │ O(n)       │ O(1)         │
 * │ fibRecursiveMemo(top-down)  │ O(n)       │ O(n)         │
 * │ fibFastDoubling (math)      │ O(log n)   │ O(log n)     │
 * └─────────────────────────────┴────────────┴──────────────┘
 */
public class Fibonacci {

    /**
     * Computes F(n) using plain recursion WITHOUT memoization (naive approach).
     *
     * Approach: Directly translates the mathematical recurrence:
     *   F(n) = F(n-1) + F(n-2)
     * Each call branches into two more calls, forming a binary call tree.
     *
     * Why is this BAD?
     * - The call tree has ~2^n nodes. For n=40, that is over 1 billion calls!
     * - Many sub-problems (e.g., F(n-2)) are recomputed exponentially many times.
     * - Example: F(5) computes F(3) twice, F(2) three times, F(1) five times.
     * - Use memoization (fibRecursiveMemoized) or iteration to fix this.
     *
     * ⚠ WARNING: NEVER use this in production for n > ~30.
     *
     * Time Complexity  : O(2^n) — exponential; binary call tree with ~2^n nodes.
     * Space Complexity : O(n)   — maximum call stack depth equals n.
     *
     * @param n the index in the Fibonacci sequence (n >= 0)
     * @return F(n), or -1 if n is negative
     */
    public static int fibRecursive(int n) {
        if (n < 0) throw new IllegalArgumentException("Fibonacci sequence is not defined for negative integers.");
        // Base cases
        if (n <= 1) return n;
        // Two recursive calls — each spawns two more: exponential growth!
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    /**
     * Computes F(n) using a bottom-up DP array (tabulation).
     *
     * Approach: Build a dp[] table from F(0) up to F(n), filling each cell
     * using the recurrence dp[i] = dp[i-1] + dp[i-2].
     *
     * Why use this?
     * - Eliminates redundant recursive calls (no call stack overhead).
     * - Intuitive and easy to understand for beginners learning DP.
     * - Trade-off: stores all F(0)..F(n) values — use fibOptimized if only F(n) needed.
     *
     * Time Complexity  : O(n) — single loop from 2 to n.
     * Space Complexity : O(n) — dp array of size n+1 allocated.
     *
     * @param n the index in the Fibonacci sequence (n >= 0)
     * @return F(n), or -1 if n is negative
     */
    public static int fibDP(int n) {
        if (n < 0) throw new IllegalArgumentException("Fibonacci sequence is not defined for negative integers.");
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        // Fill table bottom-up — O(n)
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * Computes F(n) using only two rolling variables (space-optimized DP).
     *
     * Approach: At each step, we only need F(i-1) and F(i-2) to compute F(i).
     * So we maintain just two variables (prev, curr) instead of a full array.
     *
     * Why use this?
     * - Achieves the same O(n) time as fibDP but reduces space to O(1).
     * - Preferred in production when only the final value is needed, not the series.
     *
     * Time Complexity  : O(n) — single loop from 2 to n.
     * Space Complexity : O(1) — only two integer variables (prev, curr).
     *
     * @param n the index in the Fibonacci sequence (n >= 0)
     * @return F(n), or -1 if n is negative
     */
    public static int fibOptimized(int n) {
        if (n < 0) throw new IllegalArgumentException("Fibonacci sequence is not defined for negative integers.");
        if (n <= 1) return n;
        int prev = 0, curr = 1;
        // Roll the two variables forward — O(n)
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    /**
     * Computes F(n) using top-down recursion with memoization.
     *
     * Approach: Recursive calls, but cache results in a memo[] array to avoid
     * recomputing sub-problems. Each unique sub-problem is solved exactly once.
     *
     * Why use this?
     * - Natural translation of the recurrence relation F(n)=F(n-1)+F(n-2).
     * - Useful when only a subset of Fibonacci values are needed (sparse queries).
     * - O(n) call stack depth — avoid for very large n (risk of StackOverflowError).
     *
     * Time Complexity  : O(n) — each of the n sub-problems computed once.
     * Space Complexity : O(n) — memo array of size n+1, plus O(n) call stack depth.
     *
     * @param n the index in the Fibonacci sequence (n >= 0)
     * @return F(n), or -1 if n is negative
     */
    public static int fibRecursiveMemoized(int n) {
        if (n < 0) throw new IllegalArgumentException("Fibonacci sequence is not defined for negative integers.");
        int[] memo = new int[n + 1];
        java.util.Arrays.fill(memo, -1); // Sentinel: -1 means "not computed yet"
        return fibMemoHelper(n, memo);
    }

    /**
     * Internal recursive helper for memoized Fibonacci.
     *
     * Time Complexity  : O(n) overall (each n computed once due to memoization).
     * Space Complexity : O(n) call stack + O(n) memo array.
     */
    private static int fibMemoHelper(int n, int[] memo) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (memo[n] != -1) return memo[n]; // Cache hit: O(1)
        memo[n] = fibMemoHelper(n - 1, memo) + fibMemoHelper(n - 2, memo);
        return memo[n];
    }

    /**
     * Computes F(n) using the Fast Doubling algorithm (matrix exponentiation shortcut).
     *
     * Approach: Uses the mathematical identities:
     *   F(2k)   = F(k) * [2*F(k+1) - F(k)]
     *   F(2k+1) = F(k)^2 + F(k+1)^2
     * This halves n at each step (like binary exponentiation), giving O(log n) time.
     *
     * Why use this?
     * - Fastest approach for very large n where O(n) would be too slow.
     * - Used in competitive programming and cryptography contexts.
     * - Harder to understand — only justified when performance is critical.
     *
     * Time Complexity  : O(log n) — n is halved at each recursive level.
     * Space Complexity : O(log n) — recursion depth equals log₂(n).
     *
     * @param n the index in the Fibonacci sequence (n >= 0)
     * @return F(n), or -1 if n is negative
     */
    public static int fibFastDoubling(int n) {
        if (n < 0) throw new IllegalArgumentException("Fibonacci sequence is not defined for negative integers.");
        return fastDoublingHelper(n)[0];
    }

    /**
     * Returns int[]{F(n), F(n+1)} using the fast doubling recurrence.
     *
     * Time Complexity  : O(log n) per call, O(log n) total recursion depth.
     * Space Complexity : O(log n) — call stack depth.
     */
    private static int[] fastDoublingHelper(int n) {
        if (n == 0) return new int[]{0, 1};
        int[] a = fastDoublingHelper(n / 2);
        int c = a[0] * (2 * a[1] - a[0]);   // F(2k)
        int d = a[0] * a[0] + a[1] * a[1];  // F(2k+1)
        if (n % 2 == 0) {
            return new int[]{c, d};
        } else {
            return new int[]{d, c + d};
        }
    }

    /**
     * Prints the first 'count' Fibonacci numbers to standard output.
     *
     * Approach: Uses fibOptimized (O(1) space two-variable iteration) to generate
     * the series. Iterative generation is chosen over recursive because:
     *
     *   1. Recursion: Each call to fibRecursive(i) takes O(2^i) time independently,
     *      making printing n numbers an O(2^n) operation — completely impractical.
     *
     *   2. DP Array: fibDP builds a full array, but to print a series we would
     *      rebuild it for each element, wasting memory.
     *
     *   3. Iterative (chosen): We maintain a rolling window of two variables and
     *      print each number as we compute it. One pass, minimal memory.
     *
     * Why iteration over recursion for series generation?
     * - Avoids O(n) call stack depth (no StackOverflowError risk).
     * - Cache-friendly sequential memory access.
     * - Produces all values in a single O(n) pass with O(1) extra space.
     *
     * Time Complexity  : O(count) — one loop iteration per Fibonacci number printed.
     * Space Complexity : O(1)     — only two integer rolling variables (prev, curr).
     *
     * @param count the number of Fibonacci numbers to print (must be > 0)
     */
    public static void printFibSeries(int count) {
        if (count <= 0) return;
        System.out.print("Fibonacci Series (" + count + " terms): ");

        // Rolling two-variable approach — O(1) space, O(count) time
        int prev = 0, curr = 1;
        for (int i = 0; i < count; i++) {
            System.out.print(prev + " ");
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int n = 10;
        System.out.println("Using naive recursion [O(2^n) time — AVOID for large n]:");
        System.out.println("F(" + n + ") = " + fibRecursive(n));
        System.out.println("\nUsing DP array [O(n) time, O(n) space]:");
        System.out.println("F(" + n + ") = " + fibDP(n));
        System.out.println("\nUsing optimized space [O(n) time, O(1) space]:");
        System.out.println("F(" + n + ") = " + fibOptimized(n));
        System.out.println("\nUsing recursive memoization [O(n) time, O(n) space]:");
        System.out.println("F(" + n + ") = " + fibRecursiveMemoized(n));
        System.out.println("\nUsing fast doubling [O(log n) time, O(log n) space]:");
        System.out.println("F(" + n + ") = " + fibFastDoubling(n));

        // Demo: printFibSeries — iterative, O(n) time, O(1) space
        System.out.println();
        printFibSeries(10);
        printFibSeries(15);
    }
}
