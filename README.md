# Java-Coding 🚀

A comprehensive collection of Java programs covering classic algorithms and **all major data structures**, with detailed time & space complexity analysis for every implementation.

---

## 📁 Project Structure

```
Java-Coding/
├── DataStructures/
│   ├── arrays/
│   │   ├── ArrayOperations.java      # Insert, delete, search (linear+binary), rotate, reverse
│   │   └── DynamicArray.java         # Generic resizable array (ArrayList clone) with grow/shrink
│   ├── linkedlist/
│   │   ├── SinglyLinkedList.java     # Full singly LL: reverse, middle, cycle detection
│   │   ├── DoublyLinkedList.java     # Full doubly LL: O(1) addLast/removeLast via tail
│   │   └── CircularLinkedList.java   # Circular LL + Josephus problem solver
│   ├── stack/
│   │   ├── StackArray.java           # Array stack + balanced parentheses + infix→postfix
│   │   └── StackLinkedList.java      # Linked stack + sort stack + recursive reverse
│   ├── queue/
│   │   ├── QueueArray.java           # Circular array queue + binary numbers + reverse-k
│   │   └── Deque.java                # Double-ended queue + sliding window max + palindrome
│   ├── trees/
│   │   ├── BinaryTree.java           # BT: all traversals, height, diameter, views, mirror
│   │   └── BinarySearchTree.java     # BST: insert/search/delete, LCA, floor/ceiling, validate
│   ├── heap/
│   │   └── MinHeap.java              # Min-Heap + Floyd's buildHeap + heap sort + K-smallest
│   ├── hashing/
│   │   ├── HashMap.java              # HashMap (separate chaining) + resizing + word frequency
│   │   └── HashSet.java              # HashSet (linear probing) + union/intersection/difference
│   ├── graph/
│   │   └── Graph.java                # Graph: BFS, DFS, cycle detect, topo sort, shortest path
│   └── trie/
│       └── Trie.java                 # Trie: insert, search, autocomplete, delete, LCP
│
├── Sorting/
│   ├── BubbleSort.java
│   ├── SelectionSort.java
│   ├── InsertionSort.java
│   ├── MergeSort.java
│   ├── QuickSort.java
│   ├── HeapSort.java
│   ├── CountingSort.java
│   ├── RadixSort.java
│   ├── BucketSort.java
│   └── ShellSort.java
│
├── ArrayReverse.java
├── EquilibriumIndex.java
├── Fibonacci.java
└── ReverseLinkedList.java
```

---

## 📊 Data Structures — Complexity Summary

| Data Structure       | Access  | Search  | Insert  | Delete  | Space  |
|----------------------|---------|---------|---------|---------|--------|
| Array (static)       | O(1)    | O(n)    | O(n)    | O(n)    | O(n)   |
| Dynamic Array        | O(1)    | O(n)    | O(1)*   | O(n)    | O(n)   |
| Singly Linked List   | O(n)    | O(n)    | O(1)†   | O(1)†   | O(n)   |
| Doubly Linked List   | O(n)    | O(n)    | O(1)†   | O(1)†   | O(n)   |
| Stack (array)        | O(n)    | O(n)    | O(1)    | O(1)    | O(n)   |
| Queue (circular)     | O(n)    | O(n)    | O(1)    | O(1)    | O(n)   |
| Min Heap             | O(1)‡   | O(n)    | O(log n)| O(log n)| O(n)   |
| Binary Search Tree   | O(log n)| O(log n)| O(log n)| O(log n)| O(n)   |
| HashMap (chaining)   | O(1)*   | O(1)*   | O(1)*   | O(1)*   | O(n)   |
| HashSet (probing)    | —       | O(1)*   | O(1)*   | O(1)*   | O(n)   |
| Trie                 | O(m)    | O(m)    | O(m)    | O(m)    | O(26n) |
| Graph (adj list)     | —       | O(V+E)  | O(1)    | O(deg)  | O(V+E) |

> \* amortised &nbsp;&nbsp; † at known position &nbsp;&nbsp; ‡ min only

---

## 📚 Algorithms & Programs

### Sorting Algorithms
| Algorithm     | Best     | Average  | Worst    | Space  | Stable |
|---------------|----------|----------|----------|--------|--------|
| Bubble Sort   | O(n)     | O(n²)    | O(n²)    | O(1)   | ✅     |
| Selection Sort| O(n²)    | O(n²)    | O(n²)    | O(1)   | ❌     |
| Insertion Sort| O(n)     | O(n²)    | O(n²)    | O(1)   | ✅     |
| Merge Sort    | O(n log n)| O(n log n)| O(n log n)| O(n)  | ✅     |
| Quick Sort    | O(n log n)| O(n log n)| O(n²)   | O(log n)| ❌    |
| Heap Sort     | O(n log n)| O(n log n)| O(n log n)| O(1) | ❌     |
| Counting Sort | O(n+k)   | O(n+k)   | O(n+k)   | O(k)   | ✅     |
| Radix Sort    | O(nk)    | O(nk)    | O(nk)    | O(n+k) | ✅     |
| Bucket Sort   | O(n+k)   | O(n+k)   | O(n²)    | O(n)   | ✅     |
| Shell Sort    | O(n log n)| O(n log²n)| O(n²)  | O(1)   | ❌     |

### Classic Problems
- **Fibonacci** – Naive recursion, memoization, tabulation, matrix exponentiation, Binet's formula
- **Equilibrium Index** – O(n) prefix-sum approach, find all equilibrium indices
- **Array Reversal** – In-place, using Stack, using recursion
- **Reverse Linked List** – Iterative, recursive, reverse in groups, palindrome check

---

## 🏷️ Key Concepts Demonstrated

- **Recursion & Backtracking** – Tree traversals, DFS, reverse operations
- **Two-pointer Technique** – Linked list cycle, middle finder
- **Sliding Window** – Deque-based O(n) window maximum
- **Union-Find (Disjoint Set)** – Cycle detection in undirected graphs
- **Floyd's Algorithms** – Cycle detection in linked lists, buildHeap in O(n)
- **Hashing** – Separate chaining vs open addressing, load factor management
- **Tree Properties** – Balance check, diameter, LCA, floor/ceiling in BST

---

## 🔧 How to Run

```bash
# Compile any file
javac DataStructures/arrays/ArrayOperations.java

# Run
java -cp DataStructures/arrays ArrayOperations
```

Or use an IDE like IntelliJ IDEA / VS Code with the Java extension.

---

## 📌 Author
**Ayush** — [@Ayush4307](https://github.com/Ayush4307)