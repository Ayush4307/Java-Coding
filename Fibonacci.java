public class Fibonacci {
    /**
     * Calculates the nth Fibonacci number using Dynamic Programming.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibDP(int n) {
        if (n < 0) return -1;
        if (n <= 1) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }   
        return dp[n];
    }

    /**
     * Calculates the nth Fibonacci number with optimized space.
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibOptimized(int n) {
        if (n < 0) return -1;
        if (n <= 1) return n;
        int prev = 0, curr = 1;
        for (int i = 2; i <= n; i++) {
            int next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    /**
     * Calculates the nth Fibonacci number recursively with memoization.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibRecursiveMemoized(int n) {
        if (n < 0) return -1;
        int[] memo = new int[n + 1];
        java.util.Arrays.fill(memo, -1);
        return fibMemoHelper(n, memo);
    }

    private static int fibMemoHelper(int n, int[] memo) {
        if (n <= 1) return n;
        if (memo[n] != -1) return memo[n];
        memo[n] = fibMemoHelper(n - 1, memo) + fibMemoHelper(n - 2, memo);
        return memo[n];
    }

    /**
     * Calculates the nth Fibonacci number in logarithmic time using Fast Doubling.
     * Time Complexity: O(log n)
     * Space Complexity: O(log n) due to call stack recursion
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibFastDoubling(int n) {
        if (n < 0) return -1;
        return fastDoublingHelper(n)[0];
    }

    private static int[] fastDoublingHelper(int n) {
        if (n == 0) return new int[]{0, 1};
        int[] a = fastDoublingHelper(n / 2);
        int c = a[0] * (2 * a[1] - a[0]);
        int d = a[0] * a[0] + a[1] * a[1];
        if (n % 2 == 0) {
            return new int[]{c, d};
        } else {
            return new int[]{d, c + d};
        }
    }

    public static void main(String[] args) {
        int n = 10;   
        System.out.println("Using DP array:");
        System.out.println("F(" + n + ") = " + fibDP(n));
        System.out.println("\nUsing optimized space:");
        System.out.println("F(" + n + ") = " + fibOptimized(n));
        System.out.println("\nUsing recursive memoization:");
        System.out.println("F(" + n + ") = " + fibRecursiveMemoized(n));
        System.out.println("\nUsing fast doubling O(log n):");
        System.out.println("F(" + n + ") = " + fibFastDoubling(n));
        System.out.println("\nFirst 10 Fibonacci numbers:");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibDP(i) + " ");
        }
    }
}
