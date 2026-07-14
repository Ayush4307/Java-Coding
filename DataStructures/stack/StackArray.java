/**
 * StackArray.java
 *
 * Stack implementation backed by a fixed-size array.
 *
 * Operations:
 *  - push(T)   : O(1)
 *  - pop()     : O(1)
 *  - peek()    : O(1)
 *  - isEmpty() : O(1)
 *  - isFull()  : O(1)
 *  - size()    : O(1)
 *
 * Space Complexity: O(n) where n = capacity
 *
 * Applications:
 *  - Expression evaluation and conversion (infix ↔ postfix ↔ prefix)
 *  - Undo-redo mechanism
 *  - Function call stack (recursion)
 *  - Balanced parentheses checking
 *  - Browser back-button history
 */
public class StackArray<T> {

    private final Object[] data;
    private int top;
    private final int capacity;

    public StackArray(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
        top = -1;
    }

    // ─── Core Operations ─────────────────────────────────────────────────────
    /** Push element onto stack. O(1). */
    public void push(T item) {
        if (isFull()) throw new StackOverflowError("Stack is full (capacity=" + capacity + ")");
        data[++top] = item;
    }

    /** Remove and return top element. O(1). */
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) throw new java.util.EmptyStackException();
        T item = (T) data[top];
        data[top--] = null;     // help GC
        return item;
    }

    /** Return top element without removing. O(1). */
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) throw new java.util.EmptyStackException();
        return (T) data[top];
    }

    public boolean isEmpty() { return top == -1; }
    public boolean isFull()  { return top == capacity - 1; }
    public int size()        { return top + 1; }

    public void print() {
        System.out.print("Stack (top->bottom): [");
        for (int i = top; i >= 0; i--) {
            System.out.print(data[i]);
            if (i > 0) System.out.print(", ");
        }
        System.out.println("]");
    }

    // ─── Application 1: Balanced Parentheses ─────────────────────────────────
    /**
     * Checks if the given expression has balanced brackets: (), [], {}.
     * Time: O(n), Space: O(n)
     */
    public static boolean isBalanced(String expr) {
        StackArray<Character> stack = new StackArray<>(expr.length());
        for (char ch : expr.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) return false;
                char open = stack.pop();
                if ((ch == ')' && open != '(') ||
                    (ch == ']' && open != '[') ||
                    (ch == '}' && open != '{')) return false;
            }
        }
        return stack.isEmpty();
    }

    // ─── Application 2: Infix to Postfix ─────────────────────────────────────
    /**
     * Converts infix expression to postfix (Reverse Polish Notation).
     * Supports: +, -, *, /, ^, (, )
     * Time: O(n), Space: O(n)
     */
    public static String infixToPostfix(String infix) {
        StackArray<Character> stack = new StackArray<>(infix.length());
        StringBuilder result = new StringBuilder();
        for (char ch : infix.toCharArray()) {
            if (ch == ' ') continue;
            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result.append(stack.pop());
                if (!stack.isEmpty()) stack.pop();   // discard '('
            } else {
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch))
                    result.append(stack.pop());
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) result.append(stack.pop());
        return result.toString();
    }

    private static int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^'      -> 3;
            default       -> -1;
        };
    }

    // ─── Application 3: Evaluate Postfix ─────────────────────────────────────
    /**
     * Evaluates a postfix expression (operands are single digits).
     * Time: O(n), Space: O(n)
     */
    public static int evalPostfix(String postfix) {
        StackArray<Integer> stack = new StackArray<>(postfix.length());
        for (char ch : postfix.toCharArray()) {
            if (Character.isDigit(ch)) {
                stack.push(ch - '0');
            } else {
                int b = stack.pop(), a = stack.pop();
                stack.push(switch (ch) {
                    case '+' -> a + b;
                    case '-' -> a - b;
                    case '*' -> a * b;
                    case '/' -> a / b;
                    case '^' -> (int) Math.pow(a, b);
                    default  -> throw new IllegalArgumentException("Unknown op: " + ch);
                });
            }
        }
        return stack.pop();
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        StackArray<Integer> stack = new StackArray<>(10);
        stack.push(10); stack.push(20); stack.push(30);
        stack.print();
        System.out.println("Peek: " + stack.peek());
        System.out.println("Pop : " + stack.pop());
        stack.print();

        // Balanced brackets
        System.out.println("\n-- Balanced Parentheses --");
        System.out.println("\"({[]})\" -> " + isBalanced("({[]})"));
        System.out.println("\"({[})\"  -> " + isBalanced("({[})"));

        // Infix → Postfix
        System.out.println("\n-- Infix to Postfix --");
        String infix = "a+b*(c^d-e)^(f+g*h)-i";
        System.out.println("Infix  : " + infix);
        System.out.println("Postfix: " + infixToPostfix(infix));

        // Evaluate postfix
        System.out.println("\n-- Evaluate Postfix --");
        String pf = "231*+9-";  // (2 + 3*1) - 9 = -4
        System.out.println("Postfix \"" + pf + "\" = " + evalPostfix(pf));
    }
}
