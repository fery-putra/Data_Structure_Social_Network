# Module 4: Trees - Detailed Explanation

## 🎯 Overview
Trees are **hierarchical data structures** that organize data in parent-child relationships, like a family tree or organization chart. They're essential for fast searching, sorting, and representing hierarchical data.

---

## Part 1: Understanding Tree Basics

### What Is a Tree?

**Visual:**
```
        CEO (Root)
       /   \
   VP-Eng  VP-Sales (Children of CEO)
   /  \       \
 Dev  QA    Sales Rep (Leaves - no children)
```

**Tree Terminology:**
- **Node:** Each element (CEO, VP-Eng, etc.)
- **Root:** Top node with no parent (CEO)
- **Parent:** Node with children (CEO is parent of VP-Eng)
- **Child:** Node with a parent (VP-Eng is child of CEO)
- **Leaf:** Node with no children (Dev, QA, Sales Rep)
- **Edge:** Connection between nodes (line from CEO to VP-Eng)
- **Subtree:** Tree formed by any node and its descendants

**Tree Properties:**
- ✅ **ONE root** (exactly one top node)
- ✅ **No cycles** (can't go in circles)
- ✅ **Connected** (every node reachable from root)
- ✅ **Hierarchical** (clear parent-child relationships)

---

## Part 2: Binary Tree Types

### What Is a Binary Tree?

**Definition:** Each node has **at most 2** children (left and right)

**Basic Structure:**
```java
class OrgNode {
    String name;        // Data
    OrgNode left;       // Left child pointer
    OrgNode right;      // Right child pointer
}
```

**Visual:**
```
       Alice
      /     \
    Bob    Charlie
   /  \
Diana  Eve
```

### 1. Full Binary Tree

**Definition:** Every node has **exactly 0 or 2** children (no node has just 1 child)

**✅ Full:**
```
       A
      / \
     B   C
    / \
   D   E
```
Every node: either 0 children (D, E, C) or 2 children (A, B)

**❌ Not Full:**
```
       A
      / \
     B   C
    /        ← B has only 1 child (not full!)
   D
```

**Why it matters:**
- Maximizes nodes for given height
- Memory efficient
- Predictable structure

**Code check:**
```java
private boolean isFullRecursive(OrgNode node) {
    if (node == null) return true;  // Empty is full
    
    // Leaf node (0 children)
    if (node.left == null && node.right == null) return true;
    
    // Both children exist (2 children)
    if (node.left != null && node.right != null) {
        return isFullRecursive(node.left) && isFullRecursive(node.right);
    }
    
    // One child exists (not full!)
    return false;
}
```

### 2. Complete Binary Tree

**Definition:** All levels are **completely filled** except possibly the last, which is **filled from left to right**

**✅ Complete:**
```
       A
      / \
     B   C
    / \  /
   D  E F
```
Levels 0-1 full, level 2 filled left-to-right

**❌ Not Complete:**
```
       A
      / \
     B   C
    /     \     ← Gap! Should fill left first
   D       F
```

**Why it matters:**
- Used in heaps (priority queues)
- Can be efficiently stored in array
- No wasted space

**Array representation:**
```
Index: 0  1  2  3  4  5
Data: [A][B][C][D][E][F]

For node at index i:
- Left child:  2*i + 1
- Right child: 2*i + 2
- Parent:      (i-1) / 2
```

**Code check:**
```java
public boolean isComplete() {
    Queue<OrgNode> queue = new LinkedList<>();
    queue.add(root);
    boolean nullFound = false;
    
    while (!queue.isEmpty()) {
        OrgNode current = queue.poll();
        
        if (current == null) {
            nullFound = true;  // Found a null
        } else {
            if (nullFound) return false;  // Node after null = not complete!
            queue.add(current.left);
            queue.add(current.right);
        }
    }
    return true;
}
```

### 3. Skewed Binary Tree

**Definition:** All nodes lean **only left** or **only right** (like a linked list)

**Left Skewed:**
```
    A
   /
  B
 /
C
```
All nodes have only left child

**Right Skewed:**
```
A
 \
  B
   \
    C
```
All nodes have only right child

**Why it matters:**
- **WORST CASE** for binary search tree
- Search becomes O(n) instead of O(log n)
- Essentially a linked list
- Should be avoided!

**Code check:**
```java
public String getSkewType() {
    boolean hasLeft = hasOnlyLeftChildren(root);
    boolean hasRight = hasOnlyRightChildren(root);
    
    if (hasLeft) return "Left Skewed";
    if (hasRight) return "Right Skewed";
    return "Not Skewed";
}

private boolean hasOnlyLeftChildren(OrgNode node) {
    if (node == null) return true;
    if (node.right != null) return false;  // Has right child = not left skewed
    return hasOnlyLeftChildren(node.left);
}
```

### 4. Balanced Binary Tree

**Definition:** For **every node**, height difference between left and right subtrees is **≤ 1**

**✅ Balanced:**
```
       A (height diff = 1)
      / \
     B   C (heights: 1 vs 0)
    / \
   D   E
```
- A: left height = 2, right height = 1, diff = 1 ✓
- B: left height = 1, right height = 1, diff = 0 ✓

**❌ Not Balanced:**
```
       A (height diff = 2!)
      / \
     B   C (heights: 2 vs 0)
    /
   D
  /
 E
```
- A: left height = 3, right height = 1, diff = 2 ✗

**Why it matters:**
- Guarantees **O(log n)** operations
- Used in AVL trees, Red-Black trees
- Self-balancing trees maintain this property

**Code check:**
```java
public boolean isBalanced() {
    return checkBalance(root) != -1;
}

private int checkBalance(OrgNode node) {
    if (node == null) return 0;
    
    int leftHeight = checkBalance(node.left);
    if (leftHeight == -1) return -1;  // Left unbalanced
    
    int rightHeight = checkBalance(node.right);
    if (rightHeight == -1) return -1;  // Right unbalanced
    
    if (Math.abs(leftHeight - rightHeight) > 1) return -1;  // This node unbalanced
    
    return Math.max(leftHeight, rightHeight) + 1;  // Return height
}
```

**Returns -1 if unbalanced, otherwise returns height**

---

## Part 3: Binary Search Tree (BST)

### The BST Property

**RULE:** For every node:
- **ALL** values in left subtree < node value
- **ALL** values in right subtree > node value

**Example with GPAs:**
```
       3.5
      /   \
    3.0    3.8
   /  \      \
 2.5  3.2    4.0

All left of 3.5 are < 3.5
All right of 3.5 are > 3.5
```

**Why this matters:**
- Enables **binary search** (like searching a phone book)
- Fast operations: O(log n) average
- In-order traversal gives **sorted order**

### BST Operations

#### 1. Insertion

**Algorithm:**
```
Start at root
If value < current: go left
If value > current: go right
Repeat until you find empty spot
```

**Example: Insert 3.7**
```
       3.5 (3.7 > 3.5, go right)
      /   \
    3.0    3.8 (3.7 < 3.8, go left)
              / \
           null null ← Insert here!

Result:
       3.5
      /   \
    3.0    3.8
          /
        3.7
```

**Code:**
```java
private StudentNode insertRecursive(StudentNode node, String name, double gpa) {
    // Found empty spot
    if (node == null) {
        return new StudentNode(name, gpa);
    }
    
    // Go left or right based on BST property
    if (gpa < node.gpa) {
        node.left = insertRecursive(node.left, name, gpa);
    } else if (gpa > node.gpa) {
        node.right = insertRecursive(node.right, name, gpa);
    }
    
    return node;
}
```

**Time:** O(log n) average, O(n) worst case (skewed tree)

#### 2. Searching

**Algorithm:**
```
Start at root
If value == current: found!
If value < current: search left
If value > current: search right
If hit null: not found
```

**Example: Search for 3.2**
```
       3.5 (3.2 < 3.5, go left)
      /   \
    3.0    3.8 (3.2 > 3.0, go right)
   /  \
 2.5  3.2 ← Found!
```

**Code:**
```java
private StudentNode searchRecursive(StudentNode node, double gpa) {
    // Not found or found
    if (node == null || node.gpa == gpa) {
        return node;
    }
    
    // Search left or right
    if (gpa < node.gpa) {
        return searchRecursive(node.left, gpa);
    }
    return searchRecursive(node.right, gpa);
}
```

**Why it's fast:**
- Each comparison **eliminates half** the remaining nodes
- Like binary search in sorted array
- Time: O(log n) average

#### 3. Finding Min/Max

**Minimum:** Go left until you can't
```
       3.5
      /   \
    3.0    3.8
   /  \
 2.5  3.2
 ↑
Leftmost = minimum (2.5)
```

**Maximum:** Go right until you can't
```
       3.5
      /   \
    3.0    3.8
          /  \
        3.7  4.0
              ↑
        Rightmost = maximum (4.0)
```

**Code:**
```java
public StudentNode findMin() {
    StudentNode current = root;
    while (current.left != null) {
        current = current.left;  // Keep going left
    }
    return current;
}
```

---

## Part 4: AVL Tree (Self-Balancing BST)

### The Problem with Regular BST

**Worst case: Insert in sorted order**
```
Insert: 1, 2, 3, 4, 5

Result:
1
 \
  2
   \
    3
     \
      4
       \
        5

This is a SKEWED tree! O(n) operations!
```

**Solution: AVL Tree auto-balances!**

### Balance Factor

**Definition:** `Balance Factor = height(left subtree) - height(right subtree)`

**AVL Property:** Balance factor must be **-1, 0, or 1**

**Example:**
```
       3 (BF = 0)
      / \
     2   4
Left height = 1, Right height = 1
BF = 1 - 1 = 0 ✓

       3 (BF = 1)
      /
     2
Left height = 1, Right height = 0
BF = 1 - 0 = 1 ✓

       3 (BF = 2) ← IMBALANCE!
      /
     2
    /
   1
Left height = 2, Right height = 0
BF = 2 - 0 = 2 ✗ Needs rotation!
```

### The 4 Imbalance Conditions

#### 1. Left-Left (LL) - Right Rotation

**Problem:**
```
       Z (BF = 2)
      /
     Y
    /
   X
```
New node inserted in **left-left** position

**Solution: Right rotation around Z**
```
Before:          After:
    Z              Y
   /              / \
  Y              X   Z
 /
X

Steps:
1. Y becomes new root
2. Z becomes Y's right child
3. Y's old right becomes Z's left
```

**Code:**
```java
private AVLNode rotateRight(AVLNode z) {
    AVLNode y = z.left;
    AVLNode T2 = y.right;  // Save y's right subtree
    
    // Perform rotation
    y.right = z;
    z.left = T2;
    
    // Update heights
    updateHeight(z);
    updateHeight(y);
    
    return y;  // New root
}
```

#### 2. Right-Right (RR) - Left Rotation

**Problem:**
```
Z (BF = -2)
 \
  Y
   \
    X
```
New node inserted in **right-right** position

**Solution: Left rotation around Z**
```
Before:          After:
Z                  Y
 \                / \
  Y              Z   X
   \
    X
```

#### 3. Left-Right (LR) - Left then Right Rotation

**Problem:**
```
    Z (BF = 2)
   /
  Y
   \
    X
```
New node in **left-right** position (zigzag)

**Solution: Two rotations**
```
Step 1: Left rotation on Y
    Z
   /
  X
 /
Y

Step 2: Right rotation on Z
  X
 / \
Y   Z
```

**Code:**
```java
if (balance > 1 && gpa > node.left.gpa) {  // Left-Right case
    node.left = rotateLeft(node.left);     // First: left rotation
    return rotateRight(node);              // Then: right rotation
}
```

#### 4. Right-Left (RL) - Right then Left Rotation

**Problem:**
```
Z (BF = -2)
 \
  Y
 /
X
```
New node in **right-left** position (zigzag)

**Solution: Two rotations**
```
Step 1: Right rotation on Y
Z
 \
  X
   \
    Y

Step 2: Left rotation on Z
  X
 / \
Z   Y
```

### How AVL Auto-Balances

**Example: Insert 1, 2, 3 (creates skewed tree in normal BST)**

```
Insert 1:
  1 (BF = 0)

Insert 2:
  1 (BF = -1) ✓
   \
    2

Insert 3:
  1 (BF = -2) ✗ IMBALANCE!
   \
    2 (BF = -1)
     \
      3

Detect: Right-Right case
Rotate left around 1:

    2 (BF = 0) ✓
   / \
  1   3

BALANCED! O(log n) guaranteed!
```

**Why AVL is powerful:**
- ✅ Guarantees O(log n) for all operations
- ✅ Never degrades to O(n)
- ✅ Automatic balancing
- ❌ More complex implementation
- ❌ More rotations than Red-Black trees

---

## Part 5: Tree Traversals

### Why Traverse?

**Different orders give different results:**
- Process organization top-down
- Get sorted list from BST
- Delete entire tree bottom-up
- Display tree level-by-level

### 1. In-Order Traversal (Left-Root-Right)

**Algorithm:**
```
1. Visit left subtree
2. Visit root
3. Visit right subtree
```

**Example:**
```
       4
      / \
     2   6
    / \ / \
   1  3 5  7

In-order: 1, 2, 3, 4, 5, 6, 7
         ← Sorted order!
```

**Code:**
```java
public void inOrderRecursive(StudentNode node) {
    if (node != null) {
        inOrderRecursive(node.left);   // Left
        System.out.println(node.name); // Root
        inOrderRecursive(node.right);  // Right
    }
}
```

**Use case:** Get sorted data from BST

### 2. Pre-Order Traversal (Root-Left-Right)

**Algorithm:**
```
1. Visit root
2. Visit left subtree
3. Visit right subtree
```

**Example:**
```
       4
      / \
     2   6
    / \ / \
   1  3 5  7

Pre-order: 4, 2, 1, 3, 6, 5, 7
          ← Root first, good for copying tree
```

**Use case:** 
- Create copy of tree
- Prefix expression evaluation
- Tree serialization

### 3. Post-Order Traversal (Left-Right-Root)

**Algorithm:**
```
1. Visit left subtree
2. Visit right subtree
3. Visit root
```

**Example:**
```
       4
      / \
     2   6
    / \ / \
   1  3 5  7

Post-order: 1, 3, 2, 5, 7, 6, 4
           ← Root last, good for deletion
```

**Use case:**
- Delete tree (delete children before parent)
- Postfix expression evaluation
- Calculate directory sizes

### 4. Level-Order Traversal (Breadth-First)

**Algorithm:**
```
Visit level-by-level, left to right
Uses a queue!
```

**Example:**
```
       4
      / \
     2   6
    / \ / \
   1  3 5  7

Level-order: 4, 2, 6, 1, 3, 5, 7
            ← Level 0, then level 1, then level 2
```

**Code:**
```java
public void levelOrder() {
    Queue<OrgNode> queue = new LinkedList<>();
    queue.add(root);
    
    while (!queue.isEmpty()) {
        OrgNode current = queue.poll();
        System.out.println(current.name);
        
        if (current.left != null) queue.add(current.left);
        if (current.right != null) queue.add(current.right);
    }
}
```

**Use case:**
- Print tree by levels
- Find shortest path in tree
- Serialize tree level-by-level

---

## 🎓 Key Takeaways

### Tree Type Selection

| Need | Use |
|------|-----|
| Hierarchical organization | Binary Tree |
| Fast searching | BST |
| Guaranteed O(log n) | AVL Tree |
| Range queries | BST |
| Sorted traversal | BST with in-order |

### Time Complexities

| Operation | BST (avg) | BST (worst) | AVL |
|-----------|-----------|-------------|-----|
| Search | O(log n) | O(n) | O(log n) |
| Insert | O(log n) | O(n) | O(log n) |
| Delete | O(log n) | O(n) | O(log n) |
| Min/Max | O(log n) | O(n) | O(log n) |

### When Balanced Matters

**Balanced tree (height = log n):**
- 1000 nodes → 10 levels → 10 comparisons max

**Skewed tree (height = n):**
- 1000 nodes → 1000 levels → 1000 comparisons max

**100x difference!**

Remember: Trees are powerful for hierarchical data and fast searching! 🌳
