public class Fibonacci {
    /**
     * Calculates the nth Fibonacci number using Dynamic Programming.
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibDP(int n) {
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
     * Calculates the nth Fibonacci number recursively.
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) due to call stack recursion depth
     * @param n The index of Fibonacci sequence
     * @return The nth Fibonacci number
     */
    public static int fibRecursive(int n) {
        if (n <= 1) return n;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }
    public static void main(String[] args) {
        int n = 10;   
        System.out.println("Using DP array:");
        System.out.println("F(" + n + ") = " + fibDP(n));
        System.out.println("\nUsing optimized space:");
        System.out.println("F(" + n + ") = " + fibOptimized(n));
        System.out.println("\nUsing recursion:");
        System.out.println("F(" + n + ") = " + fibRecursive(n));
        // Note: fibRecursive has exponential time complexity O(2^n)
        // whereas fibDP and fibOptimized have linear time complexity O(n).
        System.out.println("\nFirst 10 Fibonacci numbers:");
        for (int i = 0; i < 10; i++) {
            System.out.print(fibDP(i) + " ");
        }
    }
}
