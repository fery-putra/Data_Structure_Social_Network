// Module 6: Graphs - Campus Social Network
// Demonstrates directed/undirected graphs, weighted edges, adjacency matrix, adjacency list, BFS, DFS

import java.util.*;

// Edge class for weighted graphs
class Edge {
    String destination;
    int weight;  // Represents closeness or interaction frequency

    public Edge(String destination, int weight) {
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return destination + " (weight: " + weight + ")";
    }
}

// ========== ADJACENCY MATRIX REPRESENTATION ==========
// Graph represented as 2D array where matrix[i][j] = weight of edge from i to j
// 0 means no edge exists

class AdjacencyMatrixGraph {
    private int[][] matrix;
    private String[] vertices;
    private int vertexCount;
    private boolean isDirected;

    public AdjacencyMatrixGraph(String[] vertices, boolean isDirected) {
        this.vertices = vertices;
        this.vertexCount = vertices.length;
        this.matrix = new int[vertexCount][vertexCount];
        this.isDirected = isDirected;

        // Initialize with 0 (no edges)
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    // Get index of vertex
    private int getIndex(String vertex) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equals(vertex)) return i;
        }
        return -1;
    }

    // Add edge
    public void addEdge(String from, String to, int weight) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);

        if (fromIndex == -1 || toIndex == -1) {
            System.out.println("✗ Vertex not found!");
            return;
        }

        matrix[fromIndex][toIndex] = weight;

        if (!isDirected) {
            matrix[toIndex][fromIndex] = weight;  // Undirected: both ways
        }

        String direction = isDirected ? " -> " : " <-> ";
        System.out.println("✓ Edge: " + from + direction + to + " (weight: " + weight + ")");
    }

    // Display matrix
    public void displayMatrix() {
        System.out.println("\n=== Adjacency Matrix ===");
        System.out.print("     ");
        for (String v : vertices) {
            System.out.printf("%-4s", v);
        }
        System.out.println();

        for (int i = 0; i < vertexCount; i++) {
            System.out.printf("%-4s ", vertices[i]);
            for (int j = 0; j < vertexCount; j++) {
                System.out.printf("%-4d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    // Get degree of vertex
    public int getDegree(String vertex) {
        int index = getIndex(vertex);
        if (index == -1) return 0;

        int degree = 0;
        for (int j = 0; j < vertexCount; j++) {
            if (matrix[index][j] > 0) degree++;  // Outgoing edges
        }

        if (!isDirected) return degree;

        // For directed, this is out-degree
        return degree;
    }

    // Get in-degree (for directed graphs)
    public int getInDegree(String vertex) {
        int index = getIndex(vertex);
        if (index == -1) return 0;

        int inDegree = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (matrix[i][index] > 0) inDegree++;  // Incoming edges
        }
        return inDegree;
    }

    // Check if edge exists
    public boolean hasEdge(String from, String to) {
        int fromIndex = getIndex(from);
        int toIndex = getIndex(to);

        if (fromIndex == -1 || toIndex == -1) return false;

        return matrix[fromIndex][toIndex] > 0;
    }
}

// ========== ADJACENCY LIST REPRESENTATION ==========
// Graph represented as HashMap where each vertex maps to its list of edges
// More space-efficient for sparse graphs

class SocialGraph {
    // Adjacency List: each student has a list of connections (edges)
    private HashMap<String, ArrayList<Edge>> adjacencyList;
    private boolean isDirected;  // True for directed, false for undirected

    public SocialGraph(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.isDirected = isDirected;
    }

    // Add vertex (student)
    public void addVertex(String student) {
        if (!adjacencyList.containsKey(student)) {
            adjacencyList.put(student, new ArrayList<>());
            System.out.println("✓ Added student: " + student);
        }
    }

    // Add edge (connection between students)
    public void addEdge(String from, String to, int weight) {
        // Ensure both vertices exist
        addVertex(from);
        addVertex(to);

        // Add edge from 'from' to 'to'
        adjacencyList.get(from).add(new Edge(to, weight));

        // If undirected, add reverse edge
        if (!isDirected) {
            adjacencyList.get(to).add(new Edge(from, weight));
        }

        String direction = isDirected ? " -> " : " <-> ";
        System.out.println("✓ Connection: " + from + direction + to +
                " (weight: " + weight + ")");
    }

    // Display graph as adjacency list
    public void displayGraph() {
        System.out.println("\n=== Social Network Graph ===");
        System.out.println("Type: " + (isDirected ? "Directed" : "Undirected"));
        System.out.println();

        for (Map.Entry<String, ArrayList<Edge>> entry : adjacencyList.entrySet()) {
            String student = entry.getKey();
            ArrayList<Edge> connections = entry.getValue();

            System.out.print(student + " -> ");
            if (connections.isEmpty()) {
                System.out.println("(no connections)");
            } else {
                for (int i = 0; i < connections.size(); i++) {
                    System.out.print(connections.get(i));
                    if (i < connections.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }
    }

    // Get degree of a vertex (number of connections)
    public int getDegree(String student) {
        if (!adjacencyList.containsKey(student)) {
            return 0;
        }

        int degree = adjacencyList.get(student).size();

        // For directed graphs, this is out-degree
        // For undirected graphs, this is the total degree
        return degree;
    }

    // Get in-degree for directed graphs
    public int getInDegree(String student) {
        if (!isDirected) {
            return getDegree(student);  // Same as out-degree for undirected
        }

        int inDegree = 0;
        for (ArrayList<Edge> edges : adjacencyList.values()) {
            for (Edge edge : edges) {
                if (edge.destination.equals(student)) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    // Calculate total degree (sum of all degrees)
    public int getTotalDegree() {
        int total = 0;
        for (String student : adjacencyList.keySet()) {
            total += getDegree(student);
        }
        return total;
    }

    // Check if path exists between two students
    public boolean hasPath(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }

        Set<String> visited = new HashSet<>();
        return hasPathDFS(from, to, visited);
    }

    private boolean hasPathDFS(String current, String target, Set<String> visited) {
        if (current.equals(target)) {
            return true;
        }

        visited.add(current);

        for (Edge edge : adjacencyList.get(current)) {
            if (!visited.contains(edge.destination)) {
                if (hasPathDFS(edge.destination, target, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Check if graph is connected (for undirected graphs)
    public boolean isConnected() {
        if (adjacencyList.isEmpty()) {
            return true;
        }

        String start = adjacencyList.keySet().iterator().next();
        Set<String> visited = new HashSet<>();
        dfsConnectivity(start, visited);

        return visited.size() == adjacencyList.size();
    }

    private void dfsConnectivity(String current, Set<String> visited) {
        visited.add(current);

        for (Edge edge : adjacencyList.get(current)) {
            if (!visited.contains(edge.destination)) {
                dfsConnectivity(edge.destination, visited);
            }
        }
    }

    // Check for cycle in graph
    public boolean hasCycle() {
        Set<String> visited = new HashSet<>();
        Set<String> recStack = new HashSet<>();

        for (String student : adjacencyList.keySet()) {
            if (hasCycleDFS(student, visited, recStack, null)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycleDFS(String current, Set<String> visited,
                                Set<String> recStack, String parent) {
        visited.add(current);
        recStack.add(current);

        for (Edge edge : adjacencyList.get(current)) {
            String neighbor = edge.destination;

            if (!visited.contains(neighbor)) {
                if (hasCycleDFS(neighbor, visited, recStack, current)) {
                    return true;
                }
            } else if (recStack.contains(neighbor)) {
                // For undirected graphs, skip parent
                if (!isDirected && neighbor.equals(parent)) {
                    continue;
                }
                return true;
            }
        }

        recStack.remove(current);
        return false;
    }

    // Breadth-First Search (BFS) - Level-by-level exploration
    public void bfs(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("✗ Student not found!");
            return;
        }

        System.out.println("\n=== BFS Traversal from " + start + " ===");

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        int level = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + ": ");

            for (int i = 0; i < levelSize; i++) {
                String current = queue.poll();
                System.out.print(current);
                if (i < levelSize - 1) System.out.print(", ");

                // Add all unvisited neighbors to queue
                for (Edge edge : adjacencyList.get(current)) {
                    if (!visited.contains(edge.destination)) {
                        queue.add(edge.destination);
                        visited.add(edge.destination);
                    }
                }
            }
            System.out.println();
            level++;
        }
    }

    // Depth-First Search (DFS) - Go deep before exploring neighbors
    public void dfs(String start) {
        if (!adjacencyList.containsKey(start)) {
            System.out.println("✗ Student not found!");
            return;
        }

        System.out.println("\n=== DFS Traversal from " + start + " ===");

        Set<String> visited = new HashSet<>();
        System.out.print("Order: ");
        dfsRecursive(start, visited, true);
        System.out.println();
    }

    private void dfsRecursive(String current, Set<String> visited, boolean first) {
        visited.add(current);

        if (!first) System.out.print(" -> ");
        System.out.print(current);

        for (Edge edge : adjacencyList.get(current)) {
            if (!visited.contains(edge.destination)) {
                dfsRecursive(edge.destination, visited, false);
            }
        }
    }

    // Find shortest path using BFS (unweighted)
    public void findShortestPath(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            System.out.println("✗ Student not found!");
            return;
        }

        Map<String, String> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(from);
        visited.add(from);
        parent.put(from, null);

        boolean found = false;
        while (!queue.isEmpty() && !found) {
            String current = queue.poll();

            if (current.equals(to)) {
                found = true;
                break;
            }

            for (Edge edge : adjacencyList.get(current)) {
                if (!visited.contains(edge.destination)) {
                    queue.add(edge.destination);
                    visited.add(edge.destination);
                    parent.put(edge.destination, current);
                }
            }
        }

        if (!found) {
            System.out.println("\n✗ No path exists from " + from + " to " + to);
            return;
        }

        // Reconstruct path
        LinkedList<String> path = new LinkedList<>();
        String current = to;
        while (current != null) {
            path.addFirst(current);
            current = parent.get(current);
        }

        System.out.println("\n=== Shortest Path from " + from + " to " + to + " ===");
        System.out.println("Path: " + String.join(" -> ", path));
        System.out.println("Distance: " + (path.size() - 1) + " connections");
    }
}

public class CampusSocialNetworkModule6 {
    public static void main(String[] args) {
        System.out.println("=== GRAPH REPRESENTATIONS ===\n");

        System.out.println("--- 1. ADJACENCY MATRIX ---\n");

        // Create graph with adjacency matrix
        String[] students = {"Alice", "Bob", "Charlie", "Diana"};
        AdjacencyMatrixGraph matrixGraph = new AdjacencyMatrixGraph(students, false);

        // Add edges
        matrixGraph.addEdge("Alice", "Bob", 5);
        matrixGraph.addEdge("Alice", "Charlie", 3);
        matrixGraph.addEdge("Bob", "Diana", 4);
        matrixGraph.addEdge("Charlie", "Diana", 5);

        // Display matrix
        matrixGraph.displayMatrix();

        // Check properties
        System.out.println("\nMatrix Properties:");
        System.out.println("  Alice's degree: " + matrixGraph.getDegree("Alice"));
        System.out.println("  Diana's degree: " + matrixGraph.getDegree("Diana"));
        System.out.println("  Edge Alice-Bob exists? " + matrixGraph.hasEdge("Alice", "Bob"));
        System.out.println("  Edge Alice-Diana exists? " + matrixGraph.hasEdge("Alice", "Diana"));

        System.out.println("\n--- 2. ADJACENCY LIST ---\n");
        System.out.println("=== UNDIRECTED GRAPH: Mutual Friendships ===\n");

        // Create undirected graph (mutual friendships)
        SocialGraph friendships = new SocialGraph(false);

        // Add students and friendships
        friendships.addEdge("Alice", "Bob", 5);      // Very close friends (5)
        friendships.addEdge("Alice", "Charlie", 3);  // Regular friends (3)
        friendships.addEdge("Bob", "Diana", 4);
        friendships.addEdge("Charlie", "Diana", 5);
        friendships.addEdge("Diana", "Eve", 3);
        friendships.addEdge("Eve", "Frank", 4);
        friendships.addEdge("Charlie", "Frank", 2);  // Acquaintances (2)

        friendships.displayGraph();

        // Degree analysis
        System.out.println("\n=== Degree Analysis ===");
        System.out.println("Alice's degree: " + friendships.getDegree("Alice"));
        System.out.println("Diana's degree: " + friendships.getDegree("Diana"));
        System.out.println("Total degree: " + friendships.getTotalDegree());

        // Graph properties
        System.out.println("\nIs connected? " + friendships.isConnected());
        System.out.println("Has cycle? " + friendships.hasCycle());
        System.out.println("Path Alice to Frank? " + friendships.hasPath("Alice", "Frank"));

        // BFS traversal
        friendships.bfs("Alice");

        // DFS traversal
        friendships.dfs("Alice");

        // Find shortest path
        friendships.findShortestPath("Alice", "Frank");
        friendships.findShortestPath("Bob", "Eve");

        System.out.println("\n=== DIRECTED GRAPH: Follow Relationships ===\n");

        // Create directed graph (follow/follower relationships)
        SocialGraph follows = new SocialGraph(true);

        // Add follow relationships (A follows B means directed edge A -> B)
        follows.addEdge("Alice", "Bob", 1);
        follows.addEdge("Alice", "Charlie", 1);
        follows.addEdge("Bob", "Charlie", 1);
        follows.addEdge("Charlie", "Diana", 1);
        follows.addEdge("Diana", "Alice", 1);  // Creates a cycle
        follows.addEdge("Eve", "Alice", 1);

        follows.displayGraph();

        // Directed graph analysis
        System.out.println("\n=== Directed Graph Analysis ===");
        System.out.println("Alice's out-degree (following): " + follows.getDegree("Alice"));
        System.out.println("Alice's in-degree (followers): " + follows.getInDegree("Alice"));
        System.out.println("Has cycle? " + follows.hasCycle());

        // Traversals
        follows.bfs("Alice");
        follows.dfs("Alice");

        System.out.println("\n=== GRAPH CONCEPTS SUMMARY ===");
        System.out.println("\nGraph Components:");
        System.out.println("  • Vertex (Node): Student in the network");
        System.out.println("  • Edge: Connection between two students");
        System.out.println("  • Undirected: Mutual friendship (both ways)");
        System.out.println("  • Directed: Follow relationship (one way)");
        System.out.println("  • Weighted: Strength/cost of connection");

        System.out.println("\nGraph Properties:");
        System.out.println("  • Degree: Number of edges connected to a vertex");
        System.out.println("    - Out-degree: Edges going out (directed)");
        System.out.println("    - In-degree: Edges coming in (directed)");
        System.out.println("  • Total Degree: Sum of all vertex degrees");
        System.out.println("  • Path: Sequence of vertices connected by edges");
        System.out.println("  • Cycle: Path that starts and ends at same vertex");
        System.out.println("  • Connectivity: All vertices reachable from any vertex");

        System.out.println("\nGraph Representations:");
        System.out.println("  • Adjacency Matrix:");
        System.out.println("    - 2D array: matrix[i][j] = weight");
        System.out.println("    - Space: O(V²) - good for dense graphs");
        System.out.println("    - Edge check: O(1)");
        System.out.println("  • Adjacency List:");
        System.out.println("    - HashMap of vertex → list of edges");
        System.out.println("    - Space: O(V + E) - good for sparse graphs");
        System.out.println("    - Edge check: O(degree)");

        System.out.println("\nGraph Traversals:");
        System.out.println("  • BFS (Breadth-First Search):");
        System.out.println("    - Explores layer by layer using Queue");
        System.out.println("    - Finds shortest path in unweighted graphs");
        System.out.println("    - Time: O(V + E)");
        System.out.println("  • DFS (Depth-First Search):");
        System.out.println("    - Explores deeply before backtracking");
        System.out.println("    - Uses recursion or Stack");
        System.out.println("    - Good for cycle detection, topological sort");
        System.out.println("    - Time: O(V + E)");
    }
}