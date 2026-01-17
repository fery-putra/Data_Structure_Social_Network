// Module 4: Trees - Campus Organization Structure
// Demonstrates Binary Tree types (Full, Complete, Skewed, Balanced), BST, and AVL Tree

import java.util.LinkedList;
import java.util.Queue;

// ========== BINARY TREE TYPES EXPLANATION ==========
/*
 * 1. FULL BINARY TREE: Every node has either 0 or 2 children (no node has 1 child)
 *        A
 *       / \
 *      B   C
 *     / \
 *    D   E
 *
 * 2. COMPLETE BINARY TREE: All levels filled except possibly last, filled left to right
 *        A
 *       / \
 *      B   C
 *     / \  /
 *    D  E F
 *
 * 3. SKEWED BINARY TREE: All nodes have only left child OR only right child
 *    Left Skewed:     Right Skewed:
 *        A                A
 *       /                  \
 *      B                    B
 *     /                      \
 *    C                        C
 *
 * 4. BALANCED BINARY TREE: Height difference between left and right subtrees ≤ 1
 *        A
 *       / \
 *      B   C
 *     / \
 *    D   E
 */

// Node for Student in organization hierarchy (Binary Tree)
class OrgNode {
    String name;
    String position;
    OrgNode left;   // Left child (e.g., vice president or department head)
    OrgNode right;  // Right child (e.g., secretary or another department head)

    public OrgNode(String name, String position) {
        this.name = name;
        this.position = position;
        this.left = null;
        this.right = null;
    }
}

// Binary Tree for Organization Structure
class OrganizationTree {
    private OrgNode root;

    public OrganizationTree(String presidentName) {
        this.root = new OrgNode(presidentName, "President");
    }

    public OrgNode getRoot() {
        return root;
    }

    // Add member manually (for demonstration)
    public void addVicePresident(String name) {
        root.left = new OrgNode(name, "Vice President");
    }

    public void addSecretary(String name) {
        root.right = new OrgNode(name, "Secretary");
    }

    // Check if tree is FULL (every node has 0 or 2 children)
    public boolean isFull() {
        return isFullRecursive(root);
    }

    private boolean isFullRecursive(OrgNode node) {
        if (node == null) return true;

        // If leaf node
        if (node.left == null && node.right == null) return true;

        // If both children exist, check recursively
        if (node.left != null && node.right != null) {
            return isFullRecursive(node.left) && isFullRecursive(node.right);
        }

        // One child exists (not full)
        return false;
    }

    // Check if tree is COMPLETE (all levels filled except last, filled left to right)
    public boolean isComplete() {
        if (root == null) return true;

        Queue<OrgNode> queue = new LinkedList<>();
        queue.add(root);
        boolean nullFound = false;

        while (!queue.isEmpty()) {
            OrgNode current = queue.poll();

            if (current == null) {
                nullFound = true;
            } else {
                if (nullFound) return false;  // Found node after null
                queue.add(current.left);
                queue.add(current.right);
            }
        }
        return true;
    }

    // Check if tree is BALANCED (height difference ≤ 1)
    public boolean isBalanced() {
        return checkBalance(root) != -1;
    }

    private int checkBalance(OrgNode node) {
        if (node == null) return 0;

        int leftHeight = checkBalance(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = checkBalance(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return Math.max(leftHeight, rightHeight) + 1;
    }

    // Check if tree is SKEWED (all left or all right)
    public String getSkewType() {
        if (root == null) return "Empty";

        boolean hasLeft = hasOnlyLeftChildren(root);
        boolean hasRight = hasOnlyRightChildren(root);

        if (hasLeft) return "Left Skewed";
        if (hasRight) return "Right Skewed";
        return "Not Skewed";
    }

    private boolean hasOnlyLeftChildren(OrgNode node) {
        if (node == null) return true;
        if (node.right != null) return false;
        return hasOnlyLeftChildren(node.left);
    }

    private boolean hasOnlyRightChildren(OrgNode node) {
        if (node == null) return true;
        if (node.left != null) return false;
        return hasOnlyRightChildren(node.right);
    }

    // In-order traversal (Left -> Root -> Right)
    public void inOrderTraversal(OrgNode node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.position + ": " + node.name);
            inOrderTraversal(node.right);
        }
    }

    // Pre-order traversal (Root -> Left -> Right)
    public void preOrderTraversal(OrgNode node) {
        if (node != null) {
            System.out.println(node.position + ": " + node.name);
            preOrderTraversal(node.left);
            preOrderTraversal(node.right);
        }
    }

    // Post-order traversal (Left -> Right -> Root)
    public void postOrderTraversal(OrgNode node) {
        if (node != null) {
            postOrderTraversal(node.left);
            postOrderTraversal(node.right);
            System.out.println(node.position + ": " + node.name);
        }
    }

    // Level-order traversal (Breadth-first)
    public void levelOrderTraversal() {
        if (root == null) return;

        Queue<OrgNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            OrgNode current = queue.poll();
            System.out.println(current.position + ": " + current.name);

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }
}

// Node for Student GPA BST
class StudentNode {
    String name;
    double gpa;
    StudentNode left;
    StudentNode right;

    public StudentNode(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
        this.left = null;
        this.right = null;
    }
}

// Binary Search Tree for Students sorted by GPA
class StudentBST {
    private StudentNode root;

    public StudentBST() {
        this.root = null;
    }

    // Insert student (BST property: left < root < right)
    public void insert(String name, double gpa) {
        root = insertRecursive(root, name, gpa);
        System.out.println("✓ Inserted: " + name + " (GPA: " + gpa + ")");
    }

    private StudentNode insertRecursive(StudentNode node, String name, double gpa) {
        // Base case: empty position found
        if (node == null) {
            return new StudentNode(name, gpa);
        }

        // BST property: smaller GPA goes left, larger goes right
        if (gpa < node.gpa) {
            node.left = insertRecursive(node.left, name, gpa);
        } else if (gpa > node.gpa) {
            node.right = insertRecursive(node.right, name, gpa);
        }
        // If equal, we could handle duplicates differently

        return node;
    }

    // Search for student by GPA
    public StudentNode search(double gpa) {
        return searchRecursive(root, gpa);
    }

    private StudentNode searchRecursive(StudentNode node, double gpa) {
        // Base cases: not found or found
        if (node == null || node.gpa == gpa) {
            return node;
        }

        // Search left or right based on BST property
        if (gpa < node.gpa) {
            return searchRecursive(node.left, gpa);
        }
        return searchRecursive(node.right, gpa);
    }

    // In-order traversal gives sorted order (ascending GPA)
    public void inOrderTraversal() {
        System.out.println("\n=== Students by GPA (Ascending) ===");
        inOrderRecursive(root);
    }

    private void inOrderRecursive(StudentNode node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.println(node.name + ": " + node.gpa);
            inOrderRecursive(node.right);
        }
    }

    // Find minimum GPA (leftmost node)
    public StudentNode findMin() {
        if (root == null) return null;
        StudentNode current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Find maximum GPA (rightmost node)
    public StudentNode findMax() {
        if (root == null) return null;
        StudentNode current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }
}

// Node for AVL Tree
class AVLNode {
    String name;
    int studentId;
    int height;
    AVLNode left;
    AVLNode right;

    public AVLNode(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
        this.height = 1;  // New node is at height 1
        this.left = null;
        this.right = null;
    }
}

// AVL Tree (Self-balancing BST) for Students by ID
class StudentAVL {
    private AVLNode root;

    public StudentAVL() {
        this.root = null;
    }

    // Get height of node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Calculate balance factor (difference between left and right subtree heights)
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Update height of node
    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    // Right rotation (for Left-Left imbalance)
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        System.out.println("  → Right rotation performed");
        return x;
    }

    // Left rotation (for Right-Right imbalance)
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        System.out.println("  → Left rotation performed");
        return y;
    }

    // Insert student and balance tree
    public void insert(String name, int studentId) {
        root = insertRecursive(root, name, studentId);
        System.out.println("✓ Inserted: " + name + " (ID: " + studentId + ")");
    }

    private AVLNode insertRecursive(AVLNode node, String name, int studentId) {
        // Standard BST insertion
        if (node == null) {
            return new AVLNode(name, studentId);
        }

        if (studentId < node.studentId) {
            node.left = insertRecursive(node.left, name, studentId);
        } else if (studentId > node.studentId) {
            node.right = insertRecursive(node.right, name, studentId);
        } else {
            return node;  // Duplicate IDs not allowed
        }

        // Update height
        updateHeight(node);

        // Get balance factor
        int balance = getBalance(node);

        // Check for imbalance conditions and rotate

        // Left-Left case (balance factor > 1 and new node in left subtree)
        if (balance > 1 && studentId < node.left.studentId) {
            System.out.println("  ⚖ Left-Left imbalance detected");
            return rotateRight(node);
        }

        // Right-Right case (balance factor < -1 and new node in right subtree)
        if (balance < -1 && studentId > node.right.studentId) {
            System.out.println("  ⚖ Right-Right imbalance detected");
            return rotateLeft(node);
        }

        // Left-Right case (balance factor > 1 and new node in right of left subtree)
        if (balance > 1 && studentId > node.left.studentId) {
            System.out.println("  ⚖ Left-Right imbalance detected");
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // Right-Left case (balance factor < -1 and new node in left of right subtree)
        if (balance < -1 && studentId < node.right.studentId) {
            System.out.println("  ⚖ Right-Left imbalance detected");
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // In-order traversal
    public void inOrderTraversal() {
        System.out.println("\n=== Students by ID (Sorted) ===");
        inOrderRecursive(root);
    }

    private void inOrderRecursive(AVLNode node) {
        if (node != null) {
            inOrderRecursive(node.left);
            System.out.println("ID: " + node.studentId + " - " + node.name +
                    " (Height: " + node.height + ", Balance: " + getBalance(node) + ")");
            inOrderRecursive(node.right);
        }
    }

    // Display tree structure (for visualization)
    public void displayTreeStructure() {
        System.out.println("\n=== Tree Structure ===");
        displayStructureRecursive(root, "", true);
    }

    private void displayStructureRecursive(AVLNode node, String prefix, boolean isLeft) {
        if (node != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") +
                    node.studentId + " (" + node.name + ") [BF: " + getBalance(node) + "]");
            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    displayStructureRecursive(node.left, prefix + (isLeft ? "│   " : "    "), true);
                }
                if (node.right != null) {
                    displayStructureRecursive(node.right, prefix + (isLeft ? "│   " : "    "), false);
                }
            }
        }
    }
}

public class CampusSocialNetworkModule4 {
    public static void main(String[] args) {
        System.out.println("=== BINARY TREE: Organization Structure ===\n");

        // Create organization tree
        OrganizationTree csClub = new OrganizationTree("Alice Johnson");
        csClub.addVicePresident("Bob Smith");
        csClub.addSecretary("Charlie Brown");

        // Add more members to create a FULL binary tree
        csClub.getRoot().left.left = new OrgNode("Diana Prince", "Events Head");
        csClub.getRoot().left.right = new OrgNode("Eve Wilson", "Tech Head");
        csClub.getRoot().right.left = new OrgNode("Frank Miller", "Treasurer");
        csClub.getRoot().right.right = new OrgNode("Grace Lee", "PR Head");

        // Check tree properties
        System.out.println("Tree Type Analysis:");
        System.out.println("  Is Full? " + csClub.isFull());
        System.out.println("  Is Complete? " + csClub.isComplete());
        System.out.println("  Is Balanced? " + csClub.isBalanced());
        System.out.println("  Skew Type: " + csClub.getSkewType());
        System.out.println();

        // Demonstrate SKEWED tree
        OrganizationTree skewedTree = new OrganizationTree("A");
        skewedTree.getRoot().left = new OrgNode("B", "Member");
        skewedTree.getRoot().left.left = new OrgNode("C", "Member");
        skewedTree.getRoot().left.left.left = new OrgNode("D", "Member");

        System.out.println("Skewed Tree Analysis:");
        System.out.println("  Is Full? " + skewedTree.isFull());
        System.out.println("  Is Balanced? " + skewedTree.isBalanced());
        System.out.println("  Skew Type: " + skewedTree.getSkewType());
        System.out.println();
        System.out.println("Pre-order (Top-down hierarchy):");
        csClub.preOrderTraversal(csClub.getRoot());

        System.out.println("\nIn-order:");
        csClub.inOrderTraversal(csClub.getRoot());

        System.out.println("\nLevel-order (By level):");
        csClub.levelOrderTraversal();

        System.out.println("\n=== BINARY SEARCH TREE: Students by GPA ===\n");

        StudentBST honorRoll = new StudentBST();
        honorRoll.insert("Alice", 3.85);
        honorRoll.insert("Bob", 3.70);
        honorRoll.insert("Charlie", 3.90);
        honorRoll.insert("Diana", 3.65);
        honorRoll.insert("Eve", 3.95);

        honorRoll.inOrderTraversal();

        System.out.println("\nSearching for GPA 3.90:");
        StudentNode found = honorRoll.search(3.90);
        if (found != null) {
            System.out.println("✓ Found: " + found.name);
        }

        StudentNode min = honorRoll.findMin();
        StudentNode max = honorRoll.findMax();
        System.out.println("\nLowest GPA: " + min.name + " (" + min.gpa + ")");
        System.out.println("Highest GPA: " + max.name + " (" + max.gpa + ")");

        System.out.println("\n=== AVL TREE: Self-Balancing Student Registry ===\n");

        StudentAVL registry = new StudentAVL();

        // Insert students (insertions will trigger rotations if needed)
        registry.insert("Alice", 2024001);
        registry.insert("Bob", 2024002);
        registry.insert("Charlie", 2024003);  // Will cause imbalance
        registry.insert("Diana", 2024000);
        registry.insert("Eve", 2024004);
        registry.insert("Frank", 2024005);    // Will cause imbalance

        registry.inOrderTraversal();
        registry.displayTreeStructure();

        System.out.println("\n=== TREE CONCEPTS SUMMARY ===");
        System.out.println("\nBinary Tree Types:");
        System.out.println("  • Full Binary Tree: Every node has 0 or 2 children");
        System.out.println("  • Complete Binary Tree: All levels filled except last (filled left-to-right)");
        System.out.println("  • Skewed Binary Tree: All nodes lean left or right (worst case)");
        System.out.println("  • Balanced Binary Tree: Height difference between subtrees ≤ 1");

        System.out.println("\nBinary Search Tree (BST):");
        System.out.println("  • Left < Root < Right property");
        System.out.println("  • Fast search O(log n) average, O(n) worst case");
        System.out.println("  • In-order traversal gives sorted order");

        System.out.println("\nAVL Tree (Self-Balancing BST):");
        System.out.println("  • Always balanced, guaranteed O(log n) operations");
        System.out.println("  • Balance Factor = height(left) - height(right)");
        System.out.println("  • BF must be -1, 0, or 1 (else rotate)");
        System.out.println("  • 4 Imbalance Conditions:");
        System.out.println("    1. Left-Left (LL) → Right Rotation");
        System.out.println("    2. Right-Right (RR) → Left Rotation");
        System.out.println("    3. Left-Right (LR) → Left then Right Rotation");
        System.out.println("    4. Right-Left (RL) → Right then Left Rotation");

        System.out.println("\nTraversals:");
        System.out.println("  • Pre-order (Root-Left-Right): Top-down processing");
        System.out.println("  • In-order (Left-Root-Right): Sorted order for BST");
        System.out.println("  • Post-order (Left-Right-Root): Bottom-up processing");
        System.out.println("  • Level-order (BFS): Level by level");
    }
}