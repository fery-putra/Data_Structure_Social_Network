# Module 6: Graphs - Detailed Explanation

## 🎯 Overview
Graphs are the **most flexible data structure**, used to model relationships between things: social networks, maps, dependencies, recommendations, and more. If trees are hierarchies, graphs are **networks**.

---

## Part 1: Graph Fundamentals

### What Is a Graph?

**Definition:** A collection of **vertices** (nodes) connected by **edges** (links)

**Real-world examples:**
- Social network: People (vertices) and friendships (edges)
- Maps: Cities (vertices) and roads (edges)
- Web: Pages (vertices) and hyperlinks (edges)
- Computer network: Devices (vertices) and connections (edges)

**Visual:**
```
    Alice ——— Bob
      |         |
    Charlie — Diana
```
- Vertices: {Alice, Bob, Charlie, Diana}
- Edges: {Alice-Bob, Alice-Charlie, Bob-Diana, Charlie-Diana}

### Graph vs Tree

**Tree:**
```
      A
     / \
    B   C
   / \
  D   E

- ONE path between any two nodes
- NO cycles
- Hierarchical
```

**Graph:**
```
    A --- B
   / \   / \
  C   D-E   F
   \ /
    G

- MULTIPLE paths possible
- Can have cycles (A-B-D-A)
- Network structure
```

**Key difference:** Graphs are **more general** - trees are just special graphs (connected, no cycles)

---

## Part 2: Graph Types

### 1. Undirected vs Directed

#### Undirected Graph

**Definition:** Edges have **no direction** (two-way relationship)

**Example: Facebook Friendship**
```
Alice —— Bob
  ↕      ↕
Mutual friendship (both ways)
```

**Visual:**
```
    A —— B
    |    |
    C —— D
```
If A connects to B, then B connects to A

**Use cases:**
- Social networks (mutual friends)
- Road networks (can drive both ways)
- Collaboration networks

#### Directed Graph (Digraph)

**Definition:** Edges have **direction** (one-way relationship)

**Example: Twitter Following**
```
Alice → Bob
  ↓     
Only Alice follows Bob
Bob doesn't follow Alice
```

**Visual:**
```
    A → B
    ↑   ↓
    C ← D
```
A points to B, but B doesn't point back

**Use cases:**
- Twitter (follower/following)
- Web pages (hyperlinks)
- Task dependencies
- One-way streets

### 2. Weighted vs Unweighted

#### Unweighted Graph

**Definition:** Edges have **no values** (just exist or don't)

```
A —— B —— C
```
Connection exists, no additional info

#### Weighted Graph

**Definition:** Edges have **weights** (cost, distance, strength, etc.)

**Example: Road Network**
```
      5
  A —— B
  |    |
 3|    |8
  |    |
  C —— D
      2
```
Numbers represent distances in km

**Example: Social Network**
```
      Closeness: 5
  Alice ——— Bob
    |          |
  2 |          | 4
    |          |
  Charlie ——— Diana
      Closeness: 3
```
Numbers represent relationship strength

**Use cases:**
- GPS (road distances)
- Flight routes (costs)
- Social networks (interaction frequency)
- Network latency

---

## Part 3: Graph Properties

### Vertex and Edge

**Vertex (Node):**
```java
class Vertex {
    String name;
    // Can store any data
}
```

**Edge:**
```java
class Edge {
    Vertex from;
    Vertex to;
    int weight;  // For weighted graphs
}
```

### Degree

**Definition:** Number of edges connected to a vertex

**Undirected Graph:**
```
    A —— B
    |    |
    C —— D

Degree(A) = 2 (connected to B and C)
Degree(B) = 2 (connected to A and D)
```

**Directed Graph:**
```
    A → B
    ↓   ↓
    C ← D

Out-degree(A) = 2 (A points to B and C)
In-degree(A) = 0 (nothing points to A)

Out-degree(B) = 1 (B points to D)
In-degree(B) = 1 (A points to B)
```

**Code:**
```java
public int getDegree(String student) {
    return adjacencyList.get(student).size();  // Count connections
}
```

### Total Degree

**Definition:** Sum of all vertex degrees

**Theorem:** Total degree = 2 × number of edges

**Why?** Each edge contributes to TWO vertices' degrees

**Example:**
```
A —— B
|    |
C —— D

Degrees: A=2, B=2, C=2, D=2
Total = 2+2+2+2 = 8
Edges = 4
8 = 2 × 4 ✓
```

### Path

**Definition:** Sequence of vertices connected by edges

**Example:**
```
    A —— B —— C
    |         |
    D —— E —— F

Path from A to F: A → B → C → F
Another path:     A → D → E → F
```

**Path length:** Number of edges (A → B → C has length 2)

### Cycle

**Definition:** Path that starts and ends at **same vertex**

**Example:**
```
    A —— B
    |    |
    C —— D

Cycle: A → B → D → C → A
      (back to start)
```

**Acyclic graph:** No cycles (e.g., trees!)

### Connectivity

**Connected Graph (Undirected):**
```
Definition: There's a path between EVERY pair of vertices

    A —— B
    |    |
    C —— D

Connected: Can reach any vertex from any other
```

**Disconnected Graph:**
```
    A —— B    C —— D
    
Two separate components!
```

**Strongly Connected (Directed):**
```
    A → B
    ↑   ↓
    C ← D

Can reach any vertex from any other FOLLOWING directions
```

---

## Part 4: Graph Representations

### 1. Adjacency Matrix

**Definition:** 2D array where `matrix[i][j]` represents edge from vertex i to vertex j

**Example:**
```
Graph:
    A —— B
    |    |
    C —— D

Vertices: A=0, B=1, C=2, D=3

Matrix (undirected):
     A  B  C  D
  A [0, 1, 1, 0]
  B [1, 0, 0, 1]
  C [1, 0, 0, 1]
  D [0, 1, 1, 0]

matrix[0][1] = 1  (A connects to B)
matrix[1][0] = 1  (B connects to A, undirected)
matrix[0][3] = 0  (A doesn't connect to D)
```

**For weighted graph:**
```
    A —5— B
    |     |
   3|     |8
    |     |
    C —2— D

Matrix:
     A  B  C  D
  A [0, 5, 3, 0]
  B [5, 0, 0, 8]
  C [3, 0, 0, 2]
  D [0, 8, 2, 0]

0 = no edge
Number = weight of edge
```

**Code:**
```java
class AdjacencyMatrixGraph {
    private int[][] matrix;
    private String[] vertices;
    
    public void addEdge(String from, String to, int weight) {
        int i = getIndex(from);
        int j = getIndex(to);
        matrix[i][j] = weight;
        
        if (!isDirected) {
            matrix[j][i] = weight;  // Both directions
        }
    }
    
    public boolean hasEdge(String from, String to) {
        int i = getIndex(from);
        int j = getIndex(to);
        return matrix[i][j] > 0;  // O(1) lookup!
    }
}
```

**Pros:**
- ✅ **O(1) edge lookup** - just check `matrix[i][j]`
- ✅ **Simple** to understand and implement
- ✅ **Good for dense graphs** (many edges)

**Cons:**
- ❌ **O(V²) space** - wastes memory for sparse graphs
- ❌ **O(V²) initialization** - must create entire matrix
- ❌ **Slow to find all neighbors** - must scan entire row

**When to use:**
- Dense graphs (many edges)
- Need to quickly check if edge exists
- Graph size is small-medium

### 2. Adjacency List

**Definition:** HashMap where each vertex maps to list of its neighbors

**Example:**
```
Graph:
    A —— B
    |    |
    C —— D

Adjacency List:
A → [B, C]
B → [A, D]
C → [A, D]
D → [B, C]
```

**For weighted graph:**
```
    A —5— B
    |     |
   3|     |8
    |     |
    C —2— D

Adjacency List:
A → [(B,5), (C,3)]
B → [(A,5), (D,8)]
C → [(A,3), (D,2)]
D → [(B,8), (C,2)]
```

**Code:**
```java
class SocialGraph {
    // vertex → list of edges
    private HashMap<String, ArrayList<Edge>> adjacencyList;
    
    public void addEdge(String from, String to, int weight) {
        adjacencyList.get(from).add(new Edge(to, weight));
        
        if (!isDirected) {
            adjacencyList.get(to).add(new Edge(from, weight));
        }
    }
    
    public void displayGraph() {
        for (String vertex : adjacencyList.keySet()) {
            System.out.print(vertex + " → ");
            for (Edge edge : adjacencyList.get(vertex)) {
                System.out.print(edge + " ");
            }
            System.out.println();
        }
    }
}
```

**Pros:**
- ✅ **O(V + E) space** - only stores existing edges
- ✅ **Fast neighbor iteration** - just loop through list
- ✅ **Good for sparse graphs** (few edges)
- ✅ **Dynamic** - easy to add/remove vertices

**Cons:**
- ❌ **O(degree) edge lookup** - must search list
- ❌ **More complex** than matrix

**When to use:**
- Sparse graphs (few edges) ← **Most real-world graphs!**
- Need to iterate through neighbors often
- Graph changes frequently

### Comparison

| Feature | Adjacency Matrix | Adjacency List |
|---------|------------------|----------------|
| Space | O(V²) | O(V + E) |
| Check if edge exists | O(1) | O(degree) |
| Get all neighbors | O(V) | O(degree) |
| Add vertex | O(V²) | O(1) |
| Add edge | O(1) | O(1) |
| Remove vertex | O(V²) | O(V + E) |
| Best for | Dense graphs | Sparse graphs |

---

## Part 5: Graph Traversals

### 1. Breadth-First Search (BFS)

**Concept:** Explore **level by level**, like ripples in water

**Uses:** Queue (FIFO)

**Visual:**
```
Start at A:

Level 0:    A
           / \
Level 1:  B   C
         / \ / \
Level 2: D E F  G

Order: A (level 0), then B,C (level 1), then D,E,F,G (level 2)
```

**Algorithm:**
```
1. Start at source vertex
2. Add to queue
3. While queue not empty:
   a. Remove front vertex
   b. Process it
   c. Add all unvisited neighbors to queue
```

**Code:**
```java
public void bfs(String start) {
    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();
    
    queue.add(start);
    visited.add(start);
    
    while (!queue.isEmpty()) {
        String current = queue.poll();  // Remove from front
        System.out.println(current);    // Process
        
        // Add all unvisited neighbors
        for (Edge edge : adjacencyList.get(current)) {
            if (!visited.contains(edge.destination)) {
                queue.add(edge.destination);
                visited.add(edge.destination);
            }
        }
    }
}
```

**Step-by-step example:**
```
Graph:    A --- B
          |     |
          C --- D

BFS from A:

Step 1: Queue: [A], Visited: {A}
        Process A, add neighbors B, C
        
Step 2: Queue: [B, C], Visited: {A, B, C}
        Process B, add neighbor D
        
Step 3: Queue: [C, D], Visited: {A, B, C, D}
        Process C (D already visited)
        
Step 4: Queue: [D], Visited: {A, B, C, D}
        Process D (all neighbors visited)

Output: A, B, C, D
```

**Properties:**
- ✅ Finds **shortest path** in unweighted graph
- ✅ Visits vertices in **distance order**
- ✅ Time: O(V + E)
- ✅ Space: O(V) for queue

**Applications:**
- Shortest path finding
- Social network (friends within N degrees)
- Web crawler
- GPS navigation (unweighted)

### 2. Depth-First Search (DFS)

**Concept:** Explore **as deep as possible** before backtracking

**Uses:** Recursion (implicitly uses stack) or explicit stack

**Visual:**
```
Start at A:

    A
   / \
  B   C
 / \
D   E

Order: A → B (go deep) → D (go deep) → backtrack → E → backtrack → C
```

**Algorithm:**
```
1. Start at source vertex
2. Mark as visited
3. For each unvisited neighbor:
   Recursively call DFS
```

**Code:**
```java
public void dfs(String start) {
    Set<String> visited = new HashSet<>();
    dfsRecursive(start, visited);
}

private void dfsRecursive(String current, Set<String> visited) {
    visited.add(current);
    System.out.println(current);  // Process
    
    // Visit all unvisited neighbors
    for (Edge edge : adjacencyList.get(current)) {
        if (!visited.contains(edge.destination)) {
            dfsRecursive(edge.destination, visited);
        }
    }
}
```

**Step-by-step example:**
```
Graph:    A --- B
          |     |
          C --- D

DFS from A:

Call dfs(A):
  Visit A
  Neighbors: B, C
  
  Call dfs(B):  (first neighbor)
    Visit B
    Neighbors: A (visited), D
    
    Call dfs(D):  (first unvisited)
      Visit D
      Neighbors: B (visited), C
      
      Call dfs(C):  (first unvisited)
        Visit C
        Neighbors: A (visited), D (visited)
        Return
      Return
    Return
  Return

Output: A, B, D, C
```

**Properties:**
- ✅ Uses **less memory** than BFS (recursion stack vs queue)
- ✅ Good for **detecting cycles**
- ✅ Good for **topological sorting**
- ✅ Time: O(V + E)
- ✅ Space: O(V) for recursion stack

**Applications:**
- Cycle detection
- Path finding (any path, not shortest)
- Topological sorting
- Maze solving
- Sudoku solving (backtracking)

### BFS vs DFS Comparison

| Aspect | BFS | DFS |
|--------|-----|-----|
| Data structure | Queue | Stack (recursion) |
| Exploration | Level by level | Deep first |
| Shortest path | ✓ Finds it | ✗ May not find it |
| Memory | More (all neighbors in queue) | Less (stack depth) |
| Cycle detection | Harder | Easier |
| Complete | Always (if finite) | May get stuck in infinite path |

**Visual comparison:**
```
BFS:              DFS:
  1                1
 / \              / \
2   3            2   5
|   |            |
4   5            3
                 |
                 4

BFS order: 1,2,3,4,5
DFS order: 1,2,3,4,5
(Different exploration order!)
```

---

## Part 6: Finding Shortest Path

### Shortest Path with BFS (Unweighted)

**Why BFS finds shortest path:**
- Visits vertices in **distance order**
- First time we reach destination = shortest path

**Algorithm:**
```
1. Track parent of each vertex
2. Run BFS
3. When destination found, backtrack using parents
```

**Code:**
```java
public void findShortestPath(String from, String to) {
    Map<String, String> parent = new HashMap<>();
    Set<String> visited = new HashSet<>();
    Queue<String> queue = new LinkedList<>();
    
    queue.add(from);
    visited.add(from);
    parent.put(from, null);  // Start has no parent
    
    while (!queue.isEmpty()) {
        String current = queue.poll();
        
        if (current.equals(to)) {
            // Found! Reconstruct path
            reconstructPath(parent, from, to);
            return;
        }
        
        for (Edge edge : adjacencyList.get(current)) {
            if (!visited.contains(edge.destination)) {
                queue.add(edge.destination);
                visited.add(edge.destination);
                parent.put(edge.destination, current);
            }
        }
    }
}

private void reconstructPath(Map<String, String> parent, String from, String to) {
    LinkedList<String> path = new LinkedList<>();
    String current = to;
    
    while (current != null) {
        path.addFirst(current);  // Build path backwards
        current = parent.get(current);
    }
    
    System.out.println("Path: " + String.join(" → ", path));
}
```

**Example:**
```
Graph:  A --- B --- D
        |           |
        C ----------+

Find path A to D:

BFS:
Visit A: parent[A] = null
Visit B: parent[B] = A
Visit C: parent[C] = A
Visit D: parent[D] = B (first time reached)

Reconstruct:
D ← parent ← B ← parent ← A ← parent ← null
Path: [A, B, D]
```

---

## 🎓 Key Concepts Summary

### Graph Components
- **Vertex:** Node/point in graph
- **Edge:** Connection between vertices
- **Degree:** Number of connections
- **Path:** Sequence of vertices
- **Cycle:** Path returning to start

### Graph Types
| Type | Meaning | Example |
|------|---------|---------|
| Undirected | Two-way edges | Facebook friends |
| Directed | One-way edges | Twitter follows |
| Weighted | Edges have values | Road distances |
| Unweighted | Edges just exist | Friendship (yes/no) |

### Representations
- **Matrix:** Good for dense, fast edge lookup
- **List:** Good for sparse, memory efficient

### Traversals
- **BFS:** Level-by-level, finds shortest path
- **DFS:** Deep first, good for cycles

---

## 💡 Real-World Applications

### 1. Social Networks
```
Vertices: Users
Edges: Friendships/Follows
Algorithms:
- BFS: Find friends within N degrees
- DFS: Detect communities
- Shortest path: Connection suggestion
```

### 2. Navigation (GPS)
```
Vertices: Intersections
Edges: Roads (with distances)
Algorithms:
- Dijkstra: Shortest path with weights
- BFS: Shortest path by # of turns
```

### 3. Web Crawler
```
Vertices: Web pages
Edges: Hyperlinks (directed)
Algorithms:
- BFS: Level-by-level crawling
- DFS: Deep exploration
```

### 4. Dependency Resolution
```
Vertices: Tasks/Packages
Edges: Dependencies (directed)
Algorithms:
- DFS: Topological sort
- Cycle detection: Circular dependencies
```

Remember: Graphs model relationships - they're everywhere! 🌐🔗
