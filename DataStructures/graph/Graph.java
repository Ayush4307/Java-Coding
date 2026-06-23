/**
 * Graph.java
 *
 * Undirected/Directed Graph using Adjacency List representation.
 *
 * Operations:
 *  - addVertex, addEdge, removeEdge
 *  - BFS (Breadth-First Search)
 *  - DFS (Depth-First Search, recursive + iterative)
 *  - Detect cycle (undirected graph using Union-Find)
 *  - Topological Sort (directed graph, DFS-based)
 *  - Shortest path (unweighted BFS)
 *  - Connected components count
 *  - Print adjacency list
 *
 * Time Complexities (V = vertices, E = edges):
 *  - addEdge     : O(1)
 *  - removeEdge  : O(degree)
 *  - BFS / DFS   : O(V + E)
 *  - Topo sort   : O(V + E)
 *  - Cycle detect: O(V + E)
 *
 * Space Complexity: O(V + E)
 */
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Graph {

    // ─── Fields ───────────────────────────────────────────────────────────────
    private final Map<Integer, List<Integer>> adjList;
    private final boolean directed;

    public Graph(boolean directed) {
        this.directed = directed;
        adjList = new HashMap<>();
    }

    // ─── Add Vertex ───────────────────────────────────────────────────────────
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    // ─── Add Edge ─────────────────────────────────────────────────────────────
    public void addEdge(int u, int v) {
        addVertex(u); addVertex(v);
        adjList.get(u).add(v);
        if (!directed) adjList.get(v).add(u);
    }

    // ─── Remove Edge ─────────────────────────────────────────────────────────
    public void removeEdge(int u, int v) {
        if (adjList.containsKey(u)) adjList.get(u).remove(Integer.valueOf(v));
        if (!directed && adjList.containsKey(v)) adjList.get(v).remove(Integer.valueOf(u));
    }

    // ─── BFS ─────────────────────────────────────────────────────────────────
    /** BFS from source. Returns nodes in visit order. O(V + E). */
    public List<Integer> bfs(int source) {
        List<Integer> visited  = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Queue<Integer> queue  = new LinkedList<>();
        queue.add(source); seen.add(source);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            visited.add(cur);
            List<Integer> neighbors = adjList.getOrDefault(cur, Collections.emptyList());
            Collections.sort(neighbors);   // deterministic order
            for (int nb : neighbors) {
                if (!seen.contains(nb)) { seen.add(nb); queue.add(nb); }
            }
        }
        return visited;
    }

    // ─── DFS (Recursive) ─────────────────────────────────────────────────────
    /** DFS from source. O(V + E). */
    public List<Integer> dfs(int source) {
        List<Integer> visited = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        dfsRec(source, seen, visited);
        return visited;
    }

    private void dfsRec(int v, Set<Integer> seen, List<Integer> visited) {
        seen.add(v); visited.add(v);
        List<Integer> neighbors = adjList.getOrDefault(v, Collections.emptyList());
        Collections.sort(neighbors);
        for (int nb : neighbors) {
            if (!seen.contains(nb)) dfsRec(nb, seen, visited);
        }
    }

    // ─── DFS (Iterative) ─────────────────────────────────────────────────────
    public List<Integer> dfsIterative(int source) {
        List<Integer> visited = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(source);
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            if (seen.contains(cur)) continue;
            seen.add(cur); visited.add(cur);
            List<Integer> neighbors = new ArrayList<>(adjList.getOrDefault(cur, Collections.emptyList()));
            Collections.sort(neighbors, Collections.reverseOrder());
            for (int nb : neighbors) if (!seen.contains(nb)) stack.push(nb);
        }
        return visited;
    }
    // ─── Connected Components ────────────────────────────────────────────────
    public int connectedComponents() {
        Set<Integer> seen = new HashSet<>();
        int count = 0;
        for (int v : adjList.keySet()) {
            if (!seen.contains(v)) { dfsRec(v, seen, new ArrayList<>()); count++; }
        }
        return count;
    }

    // ─── Cycle Detection (Undirected, Union-Find) ─────────────────────────────
    public boolean hasCycleUndirected() {
        Map<Integer, Integer> parent = new HashMap<>();
        for (int v : adjList.keySet()) parent.put(v, v);

        for (int u : adjList.keySet()) {
            for (int v : adjList.get(u)) {
                int pu = find(parent, u), pv = find(parent, v);
                if (pu == pv) return true;
                parent.put(pu, pv);  // union
            }
        }
        return false;
    }

    private int find(Map<Integer, Integer> parent, int x) {
        while (parent.get(x) != x) {
            parent.put(x, parent.get(parent.get(x)));  // path compression
            x = parent.get(x);
        }
        return x;
    }

    // ─── Topological Sort (DFS, directed acyclic graph) ──────────────────────
    public List<Integer> topologicalSort() {
        if (!directed) throw new IllegalStateException("Topological sort requires a directed graph");
        Deque<Integer> stack = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        for (int v : adjList.keySet())
            if (!visited.contains(v)) topoRec(v, visited, stack);
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) result.add(stack.pop());
        return result;
    }

    private void topoRec(int v, Set<Integer> visited, Deque<Integer> stack) {
        visited.add(v);
        for (int nb : adjList.getOrDefault(v, Collections.emptyList()))
            if (!visited.contains(nb)) topoRec(nb, visited, stack);
        stack.push(v);
    }

    // ─── Shortest Path (Unweighted BFS) ──────────────────────────────────────
    /**
     * Returns shortest path distance from source to target.
     * Returns -1 if no path exists.
     */
    public int shortestPath(int source, int target) {
        if (source == target) return 0;
        Map<Integer, Integer> dist = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source); dist.put(source, 0);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int nb : adjList.getOrDefault(cur, Collections.emptyList())) {
                if (!dist.containsKey(nb)) {
                    dist.put(nb, dist.get(cur) + 1);
                    if (nb == target) return dist.get(nb);
                    queue.add(nb);
                }
            }
        }
        return -1;
    }

    // ─── Print ────────────────────────────────────────────────────────────────
    public void printGraph() {
        System.out.println("Adjacency List (" + (directed ? "directed" : "undirected") + "):");
        List<Integer> vertices = new ArrayList<>(adjList.keySet());
        Collections.sort(vertices);
        for (int v : vertices) {
            List<Integer> neighbors = new ArrayList<>(adjList.get(v));
            Collections.sort(neighbors);
            System.out.println("  " + v + " -> " + neighbors);
        }
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        System.out.println("=== Undirected Graph ===");
        Graph g = new Graph(false);
        g.addEdge(0, 1); g.addEdge(0, 2);
        g.addEdge(1, 3); g.addEdge(2, 4);
        g.addEdge(3, 4); g.addEdge(4, 5);
        g.printGraph();

        System.out.println("BFS from 0  : " + g.bfs(0));
        System.out.println("DFS from 0  : " + g.dfs(0));
        System.out.println("DFS iter 0  : " + g.dfsIterative(0));
        System.out.println("Components  : " + g.connectedComponents());
        System.out.println("Has cycle?  : " + g.hasCycleUndirected());
        System.out.println("Shortest 0→5: " + g.shortestPath(0, 5));

        System.out.println("\n=== Directed Graph (DAG) ===");
        Graph dag = new Graph(true);
        dag.addEdge(5, 2); dag.addEdge(5, 0);
        dag.addEdge(4, 0); dag.addEdge(4, 1);
        dag.addEdge(2, 3); dag.addEdge(3, 1);
        dag.printGraph();
        System.out.println("Topo sort: " + dag.topologicalSort());
    }
}
