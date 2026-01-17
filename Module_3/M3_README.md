# Module 3: Stack and Queue - Detailed Explanation

## 🎯 Overview
Stack and Queue are **abstract data types** that define HOW data is accessed, not how it's stored. They're like rules for a game - you can store data in an array or linked list, but you must follow specific access rules.

---

## Part 1: Stack - Last In, First Out (LIFO)

### The Restaurant Plate Analogy

Think of a **stack of plates** in a cafeteria:

```
    [Plate 3] ← Most recent (TOP)
    [Plate 2]
    [Plate 1] ← Oldest
    ─────────
    Counter
```

**Rules:**
- ✅ Can only add to TOP
- ✅ Can only remove from TOP
- ❌ Can't access middle or bottom directly

**This is LIFO:** Last plate In is First plate Out

### Stack in Real Life

**Browser Back Button:**
```
You visit:
1. google.com    [google.com]
2. youtube.com   [youtube.com]  ← Push
                 [google.com]
                 
3. facebook.com  [facebook.com] ← Push
                 [youtube.com]
                 [google.com]

Click back:      [youtube.com]  ← Pop (facebook removed)
                 [google.com]
```

### How Stack Works Internally

**Array Implementation:**
```java
class NotificationStack {
    private Notification[] stack;
    private int top;  // Index of top element
    
    public NotificationStack(int capacity) {
        this.stack = new Notification[capacity];
        this.top = -1;  // -1 means empty
    }
}
```

**Visual of `top` pointer:**
```
Empty stack:          After push "A":        After push "B":
top = -1              top = 0               top = 1
                      
[___]  index 0        [_A_]  index 0        [_B_]  index 1
[___]  index 1        [___]  index 1        [_A_]  index 0
[___]  index 2        [___]  index 2        [___]  index 2
```

### Stack Operations Explained

#### 1. Push - Add to Top

```java
public void push(Notification notification) {
    if (isFull()) {
        System.out.println("Stack is full!");
        return;
    }
    top++;                      // Move top up
    stack[top] = notification;  // Place element
}
```

**Step-by-step:**
```
Before push("Notif C"):
top = 1
[Notif B]  index 1  ← top
[Notif A]  index 0
[______]   index 2

Step 1: top++  (top becomes 2)
Step 2: stack[2] = "Notif C"

After:
top = 2
[Notif C]  index 2  ← top
[Notif B]  index 1
[Notif A]  index 0
```

**Time Complexity:** O(1) - constant time, just increment and assign

**Why LIFO?** We can only access `stack[top]`, which is always the most recently added item.

#### 2. Pop - Remove from Top

```java
public Notification pop() {
    if (isEmpty()) {
        System.out.println("Stack is empty!");
        return null;
    }
    Notification notification = stack[top];  // Get top element
    stack[top] = null;                       // Clear reference
    top--;                                   // Move top down
    return notification;
}
```

**Step-by-step:**
```
Before pop():
top = 2
[Notif C]  index 2  ← top
[Notif B]  index 1
[Notif A]  index 0

Step 1: notification = stack[2]  (save "Notif C")
Step 2: stack[2] = null          (remove reference)
Step 3: top--                    (top becomes 1)

After:
top = 1
[______]   index 2
[Notif B]  index 1  ← top
[Notif A]  index 0

Return: "Notif C"
```

**Time Complexity:** O(1)

**Why remove the reference?**
- Allows garbage collector to free memory
- Prevents memory leaks in Java

#### 3. Peek - View Top Without Removing

```java
public Notification peek() {
    if (isEmpty()) {
        return null;
    }
    return stack[top];  // Just return, don't remove
}
```

**Use case:** Preview latest notification without dismissing it

```
State doesn't change:
top = 2
[Notif C]  index 2  ← top  (return this, but leave it)
[Notif B]  index 1
[Notif A]  index 0
```

**Time Complexity:** O(1)

#### 4. isEmpty and isFull

```java
public boolean isEmpty() {
    return top == -1;  // top == -1 means no elements
}

public boolean isFull() {
    return top == capacity - 1;  // top at last index
}
```

**Visual:**
```
Empty:           Full (capacity = 3):
top = -1         top = 2
[___]            [Notif C]
[___]            [Notif B]
[___]            [Notif A]
```

### Real Use Case: Notification History

**Why Stack?** You want to see **most recent** notifications first!

```java
// User gets notifications
notifications.push(new Notification("Alice liked your post", "like"));
notifications.push(new Notification("Bob commented", "comment"));
notifications.push(new Notification("New message from Charlie", "message"));

// Display notifications (most recent first)
notifications.displayStack();
// Output:
// 1. New message from Charlie (most recent)
// 2. Bob commented
// 3. Alice liked your post

// Dismiss latest notification
notifications.pop();  // Removes "New message from Charlie"
```

**Why LIFO makes sense:**
- Recent notifications are more relevant
- Like reading from top of a newspaper
- Natural for undo/redo functionality

---

## Part 2: Queue - First In, First Out (FIFO)

### The Line at a Cafe Analogy

Think of people **waiting in line** for coffee:

```
Exit ← [Person 1] [Person 2] [Person 3] ← Entry
       (front)                 (rear)
       ↑ Served first         ↑ Just joined
```

**Rules:**
- ✅ New people join at REAR (back of line)
- ✅ People leave from FRONT (served first)
- ❌ Can't cut in line (no middle access)

**This is FIFO:** First person In is First person Out (fair!)

### Queue in Real Life

**Print Queue:**
```
You send print jobs:
1. Document1.pdf    [Document1.pdf]  ← front (prints first)
2. Image.jpg        [Image.jpg]      ← enqueue
                    [Document1.pdf]
3. Report.docx      [Report.docx]    ← enqueue
                    [Image.jpg]
                    [Document1.pdf]

Printer finishes:   [Report.docx]    ← dequeue Document1
                    [Image.jpg]      ← front (prints next)
```

### How Queue Works Internally

**Circular Array Implementation:**
```java
class MessageQueue {
    private Message[] queue;
    private int front;   // Index of first element
    private int rear;    // Index of last element
    private int size;    // Current number of elements
}
```

**Why Circular?** To reuse space efficiently!

**Visual:**
```
Regular array (wastes space):
[___][___][___][A][B][C]  ← front at 3, rear at 5
 ↑ Wasted space

Circular array (efficient):
[B][C][___][___][___][A]
 ↑rear                ↑front
Wraps around!
```

### Queue Operations Explained

#### 1. Enqueue - Add to Rear

```java
public void enqueue(Message message) {
    if (isFull()) {
        System.out.println("Queue is full!");
        return;
    }
    rear = (rear + 1) % capacity;  // Circular increment
    queue[rear] = message;
    size++;
}
```

**Why `(rear + 1) % capacity`?**

**Example with capacity = 5:**
```
rear = 4
(4 + 1) % 5 = 5 % 5 = 0  ← Wraps to start!

Visual:
       ┌─────────┐
       ↓         │
[0][1][2][3][4]  │
                 │
rear starts here ┘
```

**Step-by-step enqueue:**
```
Initial state:
front = 0, rear = -1, size = 0
[___][___][___][___][___]

Enqueue "Msg A":
rear = (−1 + 1) % 5 = 0
[Msg A][___][___][___][___]
 ↑front,rear

Enqueue "Msg B":
rear = (0 + 1) % 5 = 1
[Msg A][Msg B][___][___][___]
 ↑front  ↑rear

Enqueue "Msg C":
rear = (1 + 1) % 5 = 2
[Msg A][Msg B][Msg C][___][___]
 ↑front         ↑rear
```

**Time Complexity:** O(1)

#### 2. Dequeue - Remove from Front

```java
public Message dequeue() {
    if (isEmpty()) {
        return null;
    }
    Message message = queue[front];  // Get front
    queue[front] = null;             // Clear
    front = (front + 1) % capacity;  // Move front
    size--;
    return message;
}
```

**Step-by-step:**
```
Before dequeue:
front = 0, rear = 2, size = 3
[Msg A][Msg B][Msg C][___][___]
 ↑front         ↑rear

Step 1: message = queue[0]    (save "Msg A")
Step 2: queue[0] = null
Step 3: front = (0 + 1) % 5 = 1
Step 4: size--  (size becomes 2)

After:
front = 1, rear = 2, size = 2
[______][Msg B][Msg C][___][___]
         ↑front  ↑rear

Return: "Msg A"
```

**Time Complexity:** O(1)

**Why it's FIFO:** Always remove from `front`, which is the oldest element!

#### 3. Peek - View Front Without Removing

```java
public Message peek() {
    if (isEmpty()) {
        return null;
    }
    return queue[front];  // Just view, don't remove
}
```

**Use case:** Check next message without processing it

```
State doesn't change:
[___][Msg B][Msg C][___]
      ↑front
      Return this, but keep it in queue
```

#### 4. Circular Queue in Action

**Problem without circular:**
```
After many enqueue/dequeue operations:
[___][___][___][A][B][C]
                ↑front
                
Can't enqueue! But there's space at start!
```

**Solution with circular:**
```
rear = (5 + 1) % 6 = 0  ← Wraps!
[D][___][___][A][B][C]
 ↑rear          ↑front

Can reuse space at beginning!
```

**Visual of circular:**
```
    [0]
 [5]   [1]
[4]     [2]
   [3]
   
When rear is at [5]:
rear = (5 + 1) % 6 = 0
Goes to [0]! (full circle)
```

### Real Use Case: Message Inbox

**Why Queue?** Messages should be read in **order received** (fair)!

```java
// Alice receives messages
inbox.enqueue(new Message("Bob", "Alice", "Hey!"));
inbox.enqueue(new Message("Charlie", "Alice", "Meeting at 3pm"));
inbox.enqueue(new Message("Diana", "Alice", "Thanks!"));

// Alice reads messages (in order received)
Message first = inbox.dequeue();  // Bob's message (came first)
System.out.println(first);

Message second = inbox.dequeue(); // Charlie's message (came second)
System.out.println(second);
```

**Why FIFO makes sense:**
- Fair ordering (first come, first served)
- Natural message flow
- Like real-life conversations

---

## Comparison: Stack vs Queue

### Visual Comparison

**Stack (LIFO):**
```
         Push ↓
            [C] ← Top (add/remove here)
            [B]
            [A] ← Bottom
         Pop ↑
```

**Queue (FIFO):**
```
    Enqueue →  [A][B][C]  → Dequeue
            (rear) (front)
```

### When to Use What

| Scenario | Use | Why |
|----------|-----|-----|
| Undo/Redo | Stack | Most recent action first |
| Browser history | Stack | Go back to recent pages |
| Function calls | Stack | Return to caller (recursion) |
| Expression evaluation | Stack | Process operators correctly |
| Print jobs | Queue | Fair, first requested first printed |
| Customer service | Queue | First caller gets helped first |
| Breadth-First Search | Queue | Level-by-level exploration |
| Task scheduling | Queue | Fair round-robin scheduling |

### Operation Comparison

| Operation | Stack | Queue |
|-----------|-------|-------|
| Add | push() - at top | enqueue() - at rear |
| Remove | pop() - from top | dequeue() - from front |
| View | peek() - top | peek() - front |
| Order | LIFO | FIFO |
| Time (all ops) | O(1) | O(1) |

---

## Implementation Comparison

### Array vs Linked List Implementation

**Array-based (used in code):**
```java
// Stack
private Notification[] stack;
private int top;

// Queue  
private Message[] queue;
private int front, rear;
```

**Pros:**
- ✅ Fast access: O(1)
- ✅ Cache-friendly (contiguous memory)
- ✅ Simple implementation

**Cons:**
- ❌ Fixed size (must specify capacity)
- ❌ Wasted space in queue

**Linked List-based (alternative):**
```java
// Stack
private Node top;  // Just need top reference

// Queue
private Node front, rear;  // Need both ends
```

**Pros:**
- ✅ Dynamic size (grows as needed)
- ✅ No wasted space

**Cons:**
- ❌ Extra memory for pointers
- ❌ Slower (pointer chasing)

---

## 🎓 Key Concepts Summary

### 1. Abstract Data Type (ADT)

**Definition:** Defines WHAT operations are allowed, not HOW they're implemented

**Stack ADT:**
- Operations: push, pop, peek, isEmpty
- Rule: LIFO access only
- Implementation: Can use array OR linked list

**Queue ADT:**
- Operations: enqueue, dequeue, peek, isEmpty
- Rule: FIFO access only
- Implementation: Can use array OR linked list

### 2. LIFO vs FIFO

**LIFO (Stack):**
```
Add:    [3][2][1]
Remove: [3] first (most recent)
```
Think: **Pile of books** - top book first

**FIFO (Queue):**
```
Add:    [1][2][3]
Remove: [1] first (oldest)
```
Think: **Line at store** - first person first

### 3. Circular Array

**Purpose:** Efficient space usage in queue

**Without circular:**
```
[___][___][A][B][C]  ← Can't add, but space at start!
```

**With circular:**
```
[D][___][A][B][C]  ← Wraps around, uses all space
 ↑rear    ↑front
```

**Formula:** `index = (index + 1) % capacity`

---

## 💡 Real-World Applications

### Stack Applications

1. **Function Call Stack**
```java
void main() {
    functionA();  // Push main's context
}

void functionA() {
    functionB();  // Push A's context
}

void functionB() {
    // When B finishes, pop to A
    // When A finishes, pop to main
}
```

2. **Expression Evaluation**
```
Expression: 2 + 3 * 4
Stack: [2][+][3][*][4]
Process: Follows operator precedence
```

3. **Text Editor Undo**
```
User types: "Hello"
Stack: [type 'H'][type 'e'][type 'l'][type 'l'][type 'o']
Undo: Pop operations in reverse order
```

### Queue Applications

1. **CPU Task Scheduling**
```
Tasks: [Process A][Process B][Process C]
Each gets time slice in order received
```

2. **Breadth-First Search**
```
Explore graph level by level:
Queue: [neighbors of start] then [their neighbors] etc.
```

3. **Message Systems**
```
Email server:
Queue: [Email 1][Email 2][Email 3]
Send in order received
```

4. **Printer Queue**
```
Print jobs: [Doc1.pdf][Doc2.docx][Image.jpg]
Print in submission order
```

---

## 🔍 Common Pitfalls

### 1. Stack Overflow
```java
while (true) {
    stack.push(item);  // ❌ Eventually full!
}
```
**Solution:** Always check `isFull()` before push

### 2. Stack Underflow
```java
stack.pop();  // ❌ If stack is empty, error!
```
**Solution:** Always check `isEmpty()` before pop

### 3. Queue Not Circular
```java
// Without circular logic:
rear++;  // ❌ Eventually goes out of bounds!

// With circular:
rear = (rear + 1) % capacity;  // ✅ Wraps around
```

### 4. Forgetting to Update Size
```java
public void push(Item item) {
    stack[++top] = item;
    // ❌ Forgot: size++;
}
```
**Solution:** Always maintain size counter

### 5. Not Clearing References
```java
public Item pop() {
    return stack[top--];  // ❌ Reference still exists!
}

// ✅ Correct:
public Item pop() {
    Item item = stack[top];
    stack[top] = null;  // Clear for GC
    top--;
    return item;
}
```

---

## 🎯 Practice Mental Model

**Stack = Pile:**
- Add on top
- Remove from top
- Can't see/touch bottom

**Queue = Tunnel:**
- Enter one end
- Exit other end
- First in reaches exit first

Remember: Stack for **recent-first**, Queue for **fair-order**! 📚➡️📥
