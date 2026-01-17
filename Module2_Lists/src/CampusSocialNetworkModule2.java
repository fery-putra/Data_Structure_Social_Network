// Module 2: ArrayList and LinkedList - Friend Management System
// Demonstrates ArrayList and ALL types of custom LinkedLists

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Post class for activity feed
class Post {
    private String author;
    private String content;
    private LocalDateTime timestamp;

    public Post(String author, String content) {
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "[" + timestamp.format(formatter) + "] " + author + ": " + content;
    }

    public String getAuthor() { return author; }
}

// ========== NODE CLASSES FOR DIFFERENT LINKED LIST TYPES ==========

// Node for Singly Linked List (has only 'next' pointer)
class SinglyNode {
    String data;           // Data stored in node
    SinglyNode next;       // Pointer to next node

    public SinglyNode(String data) {
        this.data = data;
        this.next = null;  // Initially points to nothing
    }
}

// Node for Doubly Linked List (has 'next' and 'previous' pointers)
class DoublyNode {
    String data;           // Data stored in node
    DoublyNode next;       // Pointer to next node
    DoublyNode previous;   // Pointer to previous node

    public DoublyNode(String data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }
}

// ========== 1. SINGLY LINKED LIST ==========
// Each node points only to the NEXT node
// head -> [A|next] -> [B|next] -> [C|next] -> null

class SinglyLinkedList {
    private SinglyNode head;  // Pointer to first node
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Insertion at end
    public void add(String data) {
        SinglyNode newNode = new SinglyNode(data);

        if (head == null) {
            head = newNode;
        } else {
            SinglyNode current = head;
            while (current.next != null) {  // Traverse to end
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Insertion at beginning
    public void addFirst(String data) {
        SinglyNode newNode = new SinglyNode(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    // Deletion by value
    public void remove(String data) {
        if (head == null) return;

        if (head.data.equals(data)) {
            head = head.next;
            size--;
            return;
        }

        SinglyNode current = head;
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }

    // Traversal - display all elements
    public void display() {
        System.out.print("Singly Linked List: ");
        SinglyNode current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print(" -> ");
            current = current.next;
        }
        System.out.println(" -> null");
    }

    // Searching
    public boolean search(String data) {
        SinglyNode current = head;
        while (current != null) {
            if (current.data.equals(data)) return true;
            current = current.next;
        }
        return false;
    }

    public int getSize() { return size; }
}

// ========== 2. DOUBLY LINKED LIST ==========
// Each node points to BOTH next and previous nodes
// null <- [prev|A|next] <-> [prev|B|next] <-> [prev|C|next] -> null

class DoublyLinkedList {
    private DoublyNode head;  // Pointer to first node
    private DoublyNode tail;  // Pointer to last node
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Insertion at end
    public void add(String data) {
        DoublyNode newNode = new DoublyNode(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    // Insertion at specific position
    public void addAtPosition(String data, int position) {
        if (position < 0 || position > size) {
            System.out.println("Invalid position!");
            return;
        }

        if (position == 0) {
            addFirst(data);
            return;
        }

        if (position == size) {
            add(data);
            return;
        }

        DoublyNode newNode = new DoublyNode(data);
        DoublyNode current = head;

        for (int i = 0; i < position - 1; i++) {
            current = current.next;
        }

        newNode.next = current.next;
        newNode.previous = current;
        current.next.previous = newNode;
        current.next = newNode;
        size++;
    }

    // Insertion at beginning
    public void addFirst(String data) {
        DoublyNode newNode = new DoublyNode(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    // Deletion by value
    public void remove(String data) {
        if (head == null) return;

        DoublyNode current = head;
        while (current != null && !current.data.equals(data)) {
            current = current.next;
        }

        if (current == null) return;

        if (current == head) {
            head = head.next;
            if (head != null) head.previous = null;
            else tail = null;
        } else if (current == tail) {
            tail = tail.previous;
            tail.next = null;
        } else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }
        size--;
    }

    // Forward traversal
    public void displayForward() {
        System.out.print("Forward: null <- ");
        DoublyNode current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print(" <-> ");
            current = current.next;
        }
        System.out.println(" -> null");
    }

    // Backward traversal (advantage of doubly linked list!)
    public void displayBackward() {
        System.out.print("Backward: null <- ");
        DoublyNode current = tail;
        while (current != null) {
            System.out.print(current.data);
            if (current.previous != null) System.out.print(" <-> ");
            current = current.previous;
        }
        System.out.println(" -> null");
    }

    // Updating node data
    public void update(String oldData, String newData) {
        DoublyNode current = head;
        while (current != null) {
            if (current.data.equals(oldData)) {
                current.data = newData;
                System.out.println("✓ Updated: " + oldData + " -> " + newData);
                return;
            }
            current = current.next;
        }
        System.out.println("✗ Data not found: " + oldData);
    }

    public int getSize() { return size; }
}

// ========== 3. SINGLY CIRCULAR LINKED LIST ==========
// Last node points back to first node (forms a circle)
// head -> [A|next] -> [B|next] -> [C|next] -+
//          ^                                 |
//          +---------------------------------+

class SinglyCircularLinkedList {
    private SinglyNode head;
    private int size;

    public SinglyCircularLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Insertion
    public void add(String data) {
        SinglyNode newNode = new SinglyNode(data);

        if (head == null) {
            head = newNode;
            newNode.next = head;  // Points to itself
        } else {
            SinglyNode current = head;
            while (current.next != head) {  // Stop when we reach the node before head
                current = current.next;
            }
            current.next = newNode;
            newNode.next = head;  // New node points back to head
        }
        size++;
    }

    // Deletion
    public void remove(String data) {
        if (head == null) return;

        if (head.data.equals(data)) {
            if (head.next == head) {  // Only one node
                head = null;
            } else {
                SinglyNode current = head;
                while (current.next != head) {
                    current = current.next;
                }
                head = head.next;
                current.next = head;
            }
            size--;
            return;
        }

        SinglyNode current = head;
        while (current.next != head && !current.next.data.equals(data)) {
            current = current.next;
        }

        if (current.next != head) {
            current.next = current.next.next;
            size--;
        }
    }

    // Traversal (must be careful not to infinite loop!)
    public void display() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        System.out.print("Singly Circular: ");
        SinglyNode current = head;
        do {
            System.out.print(current.data);
            current = current.next;
            if (current != head) System.out.print(" -> ");
        } while (current != head);
        System.out.println(" -> (back to " + head.data + ")");
    }

    public int countNodes() { return size; }
}

// ========== 4. DOUBLY CIRCULAR LINKED LIST ==========
// Both ends connect: last.next = first, first.previous = last
//          +---------------------------------------------+
//          v                                             |
// head -> [prev|A|next] <-> [prev|B|next] <-> [prev|C|next]
//          ^                                             |
//          +---------------------------------------------+

class DoublyCircularLinkedList {
    private DoublyNode head;
    private DoublyNode tail;
    private int size;

    public DoublyCircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Insertion
    public void add(String data) {
        DoublyNode newNode = new DoublyNode(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head;
            newNode.previous = tail;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            newNode.next = head;
            head.previous = newNode;
            tail = newNode;
        }
        size++;
    }

    // Deletion
    public void remove(String data) {
        if (head == null) return;

        DoublyNode current = head;
        do {
            if (current.data.equals(data)) {
                if (size == 1) {
                    head = null;
                    tail = null;
                } else if (current == head) {
                    head = head.next;
                    head.previous = tail;
                    tail.next = head;
                } else if (current == tail) {
                    tail = tail.previous;
                    tail.next = head;
                    head.previous = tail;
                } else {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                }
                size--;
                return;
            }
            current = current.next;
        } while (current != head);
    }

    // Forward traversal
    public void displayForward() {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        System.out.print("Doubly Circular (Forward): ");
        DoublyNode current = head;
        do {
            System.out.print(current.data);
            current = current.next;
            if (current != head) System.out.print(" <-> ");
        } while (current != head);
        System.out.println(" <-> (back to " + head.data + ")");
    }

    // Backward traversal
    public void displayBackward() {
        if (tail == null) {
            System.out.println("List is empty");
            return;
        }

        System.out.print("Doubly Circular (Backward): ");
        DoublyNode current = tail;
        do {
            System.out.print(current.data);
            current = current.previous;
            if (current != tail) System.out.print(" <-> ");
        } while (current != tail);
        System.out.println(" <-> (back to " + tail.data + ")");
    }

    // Searching
    public boolean search(String data) {
        if (head == null) return false;

        DoublyNode current = head;
        do {
            if (current.data.equals(data)) return true;
            current = current.next;
        } while (current != head);

        return false;
    }

    // Updating
    public void update(String oldData, String newData) {
        if (head == null) {
            System.out.println("List is empty");
            return;
        }

        DoublyNode current = head;
        do {
            if (current.data.equals(oldData)) {
                current.data = newData;
                System.out.println("✓ Updated: " + oldData + " -> " + newData);
                return;
            }
            current = current.next;
        } while (current != head);

        System.out.println("✗ Data not found: " + oldData);
    }

    public int countNodes() { return size; }
}

// ========== ARRAYLIST DEMONSTRATION ==========

class Student {
    private String name;
    private String studentId;
    private ArrayList<Post> activityFeed;  // Using ArrayList for posts

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.activityFeed = new ArrayList<>();  // Declaration
    }

    // ArrayList operations
    public void createPost(String content) {
        Post newPost = new Post(this.name, content);
        activityFeed.add(newPost);  // add(element) - adds to end
        System.out.println("✓ Post created!");
    }

    public void createPostAtPosition(String content, int index) {
        Post newPost = new Post(this.name, content);
        activityFeed.add(index, newPost);  // add(index, element)
        System.out.println("✓ Post inserted at position " + index);
    }

    public void getPostAtIndex(int index) {
        if (index >= 0 && index < activityFeed.size()) {
            Post post = activityFeed.get(index);  // get(index)
            System.out.println("Post at index " + index + ": " + post);
        }
    }

    public void updatePost(int index, String newContent) {
        if (index >= 0 && index < activityFeed.size()) {
            Post updatedPost = new Post(this.name, newContent);
            activityFeed.set(index, updatedPost);  // set(index, element)
            System.out.println("✓ Post updated at index " + index);
        }
    }

    public void deletePost(int index) {
        if (index >= 0 && index < activityFeed.size()) {
            activityFeed.remove(index);  // remove(index)
            System.out.println("✓ Post deleted at index " + index);
        }
    }

    public int getPostCount() {
        return activityFeed.size();  // size()
    }

    public boolean hasNoPosts() {
        return activityFeed.isEmpty();  // isEmpty()
    }

    public void clearAllPosts() {
        activityFeed.clear();  // clear() - removes all elements
        System.out.println("✓ All posts cleared!");
    }

    public void displayPosts() {
        if (activityFeed.isEmpty()) {
            System.out.println("No posts yet.");
            return;
        }

        System.out.println("\n=== Activity Feed for " + name + " ===");
        for (int i = 0; i < activityFeed.size(); i++) {
            System.out.println((i + 1) + ". " + activityFeed.get(i));
        }
    }
}

public class CampusSocialNetworkModule2 {
    public static void main(String[] args) {
        System.out.println("========== ARRAYLIST DEMONSTRATION ==========\n");

        Student alice = new Student("Alice", "2024001");

        // add(element)
        alice.createPost("Joined the CS club!");
        alice.createPost("Study session today was great.");
        alice.createPost("Looking forward to hackathon!");

        alice.displayPosts();

        // add(index, element)
        alice.createPostAtPosition("Don't forget the project deadline!", 1);
        alice.displayPosts();

        // get(index) and get index with element
        System.out.println();
        alice.getPostAtIndex(0);
        alice.getPostAtIndex(2);

        // set(index, element)
        System.out.println();
        alice.updatePost(0, "Excited to be in the CS club!");
        alice.displayPosts();

        // remove(index)
        System.out.println();
        alice.deletePost(2);
        alice.displayPosts();

        // size()
        System.out.println("\nTotal posts: " + alice.getPostCount());

        // isEmpty()
        System.out.println("Has no posts? " + alice.hasNoPosts());

        // clear()
        System.out.println();
        alice.clearAllPosts();
        System.out.println("Has no posts? " + alice.hasNoPosts());

        System.out.println("\n========== 1. SINGLY LINKED LIST ==========\n");

        SinglyLinkedList friends1 = new SinglyLinkedList();
        friends1.add("Bob");
        friends1.add("Charlie");
        friends1.add("Diana");
        friends1.addFirst("Alice");  // Add at beginning
        friends1.display();

        System.out.println("Search 'Charlie': " + friends1.search("Charlie"));
        System.out.println("Search 'Eve': " + friends1.search("Eve"));

        friends1.remove("Charlie");
        friends1.display();
        System.out.println("Size: " + friends1.getSize());

        System.out.println("\n========== 2. DOUBLY LINKED LIST ==========\n");

        DoublyLinkedList friends2 = new DoublyLinkedList();
        friends2.add("Bob");
        friends2.add("Charlie");
        friends2.add("Diana");
        friends2.addFirst("Alice");
        friends2.addAtPosition("Eve", 2);

        friends2.displayForward();
        friends2.displayBackward();  // Can traverse both ways!

        friends2.update("Bob", "Robert");
        friends2.displayForward();

        friends2.remove("Eve");
        friends2.displayForward();
        System.out.println("Size: " + friends2.getSize());

        System.out.println("\n========== 3. SINGLY CIRCULAR LINKED LIST ==========\n");

        SinglyCircularLinkedList friends3 = new SinglyCircularLinkedList();
        friends3.add("Alice");
        friends3.add("Bob");
        friends3.add("Charlie");
        friends3.add("Diana");
        friends3.display();

        friends3.remove("Bob");
        friends3.display();
        System.out.println("Node count: " + friends3.countNodes());

        System.out.println("\n========== 4. DOUBLY CIRCULAR LINKED LIST ==========\n");

        DoublyCircularLinkedList friends4 = new DoublyCircularLinkedList();
        friends4.add("Alice");
        friends4.add("Bob");
        friends4.add("Charlie");
        friends4.add("Diana");

        friends4.displayForward();
        friends4.displayBackward();

        System.out.println("Search 'Charlie': " + friends4.search("Charlie"));

        friends4.update("Alice", "Alicia");
        friends4.displayForward();

        friends4.remove("Bob");
        friends4.displayForward();
        System.out.println("Node count: " + friends4.countNodes());

        System.out.println("\n========== SUMMARY ==========");
        System.out.println("\nArrayList:");
        System.out.println("  ✓ Fast random access: O(1)");
        System.out.println("  ✓ Slow insertion/deletion in middle: O(n)");
        System.out.println("  ✓ Best for: Reading elements frequently");

        System.out.println("\nSingly Linked List:");
        System.out.println("  ✓ One-way traversal (next only)");
        System.out.println("  ✓ Less memory than doubly");
        System.out.println("  ✓ Can't traverse backward");

        System.out.println("\nDoubly Linked List:");
        System.out.println("  ✓ Two-way traversal (next and previous)");
        System.out.println("  ✓ More memory (2 pointers per node)");
        System.out.println("  ✓ Easier deletion (no need to track previous)");

        System.out.println("\nSingly Circular:");
        System.out.println("  ✓ Last node points to first");
        System.out.println("  ✓ Useful for round-robin scheduling");
        System.out.println("  ✓ Can start from any node and visit all");

        System.out.println("\nDoubly Circular:");
        System.out.println("  ✓ Bidirectional circular");
        System.out.println("  ✓ Can traverse forever in both directions");
        System.out.println("  ✓ Best for: Music playlists, browser history");
    }
}