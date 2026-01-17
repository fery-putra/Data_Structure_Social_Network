# Module 5: HashMap - Detailed Explanation

## 🎯 Overview
HashMap is a **key-value storage** data structure that provides **O(1) average time** for insertions, deletions, and lookups. Think of it as a super-fast phonebook where you can instantly find anyone by their name.

---

## Part 1: Understanding HashMap

### The Phonebook Analogy

**Traditional Array (slow):**
```
Search for "Alice":
[Bob, Charlie, David, Alice, ...]
 ↑     ↑       ↑      ↑
Must check each one! O(n) time
```

**HashMap (fast):**
```
Search for "Alice":
"Alice" → hash function → index 3 → Found! O(1) time
                              ↓
                          [  ][  ][  ][Alice][  ]
```

**Key idea:** Use a **hash function** to convert key into array index directly!

### How HashMap Works Internally

**Structure:**
```java
class HashMap<K, V> {
    private Entry<K, V>[] table;  // Array of entries
    private int size;              // Number of key-value pairs
    
    class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;  // For collision handling
    }
}
```

**Visual:**
```
HashMap with capacity 10:

Index  Entry
  0  →  null
  1  →  ["CS101", "Intro to Programming"]
  2  →  ["DS201", "Data Structures"]
  3  →  null
  4  →  ["WEB301", "Web Development"]
  5  →  null
  ...
```

### Hash Function

**Purpose:** Convert key into array index

**Example:**
```java
// Simplified hash function
int hashCode = key.hashCode();  // e.g., "CS101" → 123456
int index = hashCode % capacity;  // 123456 % 10 = 6

// Store at index 6
table[6] = new Entry("CS101", "Intro to Programming");
```

**Good hash function:**
- ✅ **Deterministic:** Same key always produces same hash
- ✅ **Uniform:** Spreads keys evenly across array
- ✅ **Fast:** Quick to compute

**Real Java hashCode() for String:**
```java
"CS101".hashCode():
= 'C'*31^4 + 'S'*31^3 + '1'*31^2 + '0'*31^1 + '1'*31^0
= 67*923521 + 83*29791 + 49*961 + 48*31 + 49
= ...complex calculation...
```

### Collision Handling

**Problem:** Two keys hash to same index!

**Example:**
```
"CS101".hashCode() % 10 = 6
"DS201".hashCode() % 10 = 6  ← Collision!
```

**Solution: Separate Chaining (Linked List)**

```
Index  Entry
  6  →  ["CS101", "Intro..."] → ["DS201", "Data..."] → null
         ↑                       ↑
      First entry          Second entry (collision)
      
Each index stores a linked list of entries!
```

**Code:**
```java
class Entry<K, V> {
    K key;
    V value;
    Entry<K, V> next;  // Points to next entry in chain
}

// When adding with collision:
Entry<K, V> newEntry = new Entry<>(key, value);
newEntry.next = table[index];  // Point to current head
table[index] = newEntry;       // New entry becomes head
```

---

## Part 2: HashMap Operations Explained

### 1. put(key, value) - Add or Update

**Algorithm:**
```
1. Calculate hash: hashCode(key) % capacity
2. Go to that index
3. If empty: place entry there
4. If occupied: search chain for key
   - If key exists: update value
   - If key doesn't exist: add to chain
```

**Example: put("CS101", "Intro to Programming")**

```java
// Step 1: Calculate index
int index = "CS101".hashCode() % 10;  // = 6

// Step 2: Check if key exists
Entry current = table[6];
while (current != null) {
    if (current.key.equals("CS101")) {
        current.value = "Intro to Programming";  // Update
        return;
    }
    current = current.next;
}

// Step 3: Key doesn't exist, add new
Entry newEntry = new Entry("CS101", "Intro to Programming");
newEntry.next = table[6];
table[6] = newEntry;
```

**Visual:**
```
Before:
Index 6: ["DS201", "Data..."] → null

After put("CS101", "Intro..."):
Index 6: ["CS101", "Intro..."] → ["DS201", "Data..."] → null
         ↑ New entry added to front
```

**Time Complexity:**
- Best case: O(1) - no collision
- Average case: O(1) - short chains
- Worst case: O(n) - all keys hash to same index (rare with good hash function)

### 2. get(key) - Retrieve Value

**Algorithm:**
```
1. Calculate hash: hashCode(key) % capacity
2. Go to that index
3. Search chain for key
4. Return value if found, null if not
```

**Example: get("CS101")**

```java
// Step 1: Calculate index
int index = "CS101".hashCode() % 10;  // = 6

// Step 2: Search chain at index 6
Entry current = table[6];
while (current != null) {
    if (current.key.equals("CS101")) {
        return current.value;  // Found!
    }
    current = current.next;
}
return null;  // Not found
```

**Visual:**
```
Index 6: ["CS101", "Intro..."] → ["DS201", "Data..."] → null
          ↑ Found! Return "Intro to Programming"
```

**Time Complexity:** O(1) average

### 3. containsKey(key) - Check If Key Exists

**Algorithm:**
```
Same as get(), but return true/false instead of value
```

**Code:**
```java
public boolean containsKey(String key) {
    int index = key.hashCode() % capacity;
    Entry current = table[index];
    
    while (current != null) {
        if (current.key.equals(key)) {
            return true;  // Found
        }
        current = current.next;
    }
    return false;  // Not found
}
```

**Time Complexity:** O(1) average

### 4. remove(key) - Delete Entry

**Algorithm:**
```
1. Calculate index
2. Search chain for key
3. If found: remove from chain
4. Return old value
```

**Example: remove("CS101")**

```java
int index = "CS101".hashCode() % 10;

// Special case: first in chain
if (table[index].key.equals("CS101")) {
    Entry removed = table[index];
    table[index] = table[index].next;  // Skip over it
    return removed.value;
}

// General case: in middle of chain
Entry current = table[index];
while (current.next != null) {
    if (current.next.key.equals("CS101")) {
        Entry removed = current.next;
        current.next = current.next.next;  // Skip over it
        return removed.value;
    }
    current = current.next;
}
```

**Visual:**
```
Before:
["DS201"] → ["CS101"] → ["WEB301"] → null
              ↑ Remove this

After:
["DS201"] → ["WEB301"] → null
     ↑           ↑
  current.next = current.next.next
```

**Time Complexity:** O(1) average

### 5. keySet() - Get All Keys

**Algorithm:**
```
1. Create empty set
2. Iterate through all indices
3. For each chain, add all keys to set
4. Return set
```

**Code:**
```java
public Set<String> keySet() {
    Set<String> keys = new HashSet<>();
    
    for (int i = 0; i < capacity; i++) {
        Entry current = table[i];
        while (current != null) {
            keys.add(current.key);
            current = current.next;
        }
    }
    return keys;
}
```

**Visual:**
```
Index 0: null
Index 1: ["CS101"] → null
Index 2: ["DS201"] → ["WEB301"] → null
Index 3: null

keySet() returns: {"CS101", "DS201", "WEB301"}
```

**Time Complexity:** O(n) - must visit all entries

### 6. entrySet() - Get All Key-Value Pairs

**Returns:** Set of Entry objects

```java
public Set<Map.Entry<K, V>> entrySet() {
    Set<Map.Entry<K, V>> entries = new HashSet<>();
    
    for (int i = 0; i < capacity; i++) {
        Entry current = table[i];
        while (current != null) {
            entries.add(current);  // Add entire entry
            current = current.next;
        }
    }
    return entries;
}
```

**Usage:**
```java
for (Map.Entry<String, String> entry : courseNames.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}

// Output:
// CS101 -> Intro to Programming
// DS201 -> Data Structures
// WEB301 -> Web Development
```

**Time Complexity:** O(n)

### 7. size() - Get Number of Entries

**Simple counter:**
```java
private int size = 0;

public void put(K key, V value) {
    // ... code to add entry ...
    size++;  // Increment on add
}

public V remove(K key) {
    // ... code to remove entry ...
    size--;  // Decrement on remove
    return oldValue;
}

public int size() {
    return size;  // O(1) - just return counter
}
```

### 8. clear() - Remove All Entries

**Algorithm:**
```
1. Set all indices to null
2. Reset size to 0
```

**Code:**
```java
public void clear() {
    for (int i = 0; i < capacity; i++) {
        table[i] = null;  // Clear each chain
    }
    size = 0;
}
```

**Time Complexity:** O(capacity)

---

## Part 3: Real-World Applications

### 1. Course Enrollment System

**Problem:** Track which students are in which courses

**Solution:**
```java
// courseCode → list of students
HashMap<String, ArrayList<String>> courseStudents;

// Add student to course
courseStudents.get("CS101").add("Alice");

// Get all students in course (instant!)
ArrayList<String> students = courseStudents.get("CS101");
```

**Why HashMap?**
- ✅ O(1) lookup by course code
- ✅ Efficient for thousands of courses
- ✅ Easy to check if course exists

### 2. Interest Matching

**Problem:** Find students with same interests

**Solution:**
```java
// interest → list of students with that interest
HashMap<String, ArrayList<Student>> interestGroups;

// Find all students interested in "Machine Learning"
ArrayList<Student> mlStudents = interestGroups.get("Machine Learning");
```

**Algorithm to find matches:**
```java
// Count shared interests
HashMap<String, Integer> matchCounts = new HashMap<>();

for (String interest : student.getInterests()) {
    ArrayList<Student> group = interestGroups.get(interest);
    
    for (Student other : group) {
        if (!other.equals(student)) {
            // Increment match count (default to 0 if not exists)
            matchCounts.put(other.getId(), 
                           matchCounts.getOrDefault(other.getId(), 0) + 1);
        }
    }
}
```

**Result:** HashMap of studentId → number of shared interests

### 3. Caching / Memoization

**Problem:** Avoid recalculating expensive operations

**Solution:**
```java
HashMap<String, Result> cache = new HashMap<>();

public Result expensiveOperation(String input) {
    // Check cache first
    if (cache.containsKey(input)) {
        return cache.get(input);  // O(1) - instant return!
    }
    
    // Calculate result (slow)
    Result result = doExpensiveCalculation(input);
    
    // Store in cache for next time
    cache.put(input, result);
    
    return result;
}
```

---

## Part 4: HashMap vs Other Data Structures

### Performance Comparison

| Operation | ArrayList | LinkedList | BST | HashMap |
|-----------|-----------|------------|-----|---------|
| Insert | O(n) | O(1) | O(log n) | **O(1)** ⚡ |
| Search by value | O(n) | O(n) | O(log n) | **O(1)** ⚡ |
| Delete by value | O(n) | O(n) | O(log n) | **O(1)** ⚡ |
| Get by index | **O(1)** ⚡ | O(n) | N/A | N/A |
| Ordered iteration | ✓ | ✓ | ✓ | ✗ |

### When to Use HashMap

**✅ Use HashMap when:**
- Need fast lookup by key
- Keys are unique
- Order doesn't matter
- Frequent insertions/deletions
- Need to count occurrences

**Example: Word frequency counter**
```java
HashMap<String, Integer> wordCount = new HashMap<>();

for (String word : words) {
    wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
}

// Instantly check frequency
int frequency = wordCount.get("hello");  // O(1)!
```

**❌ Don't use HashMap when:**
- Need sorted order (use TreeMap instead)
- Need to access by index (use ArrayList)
- Need smallest/largest key (use TreeMap)
- Memory is very limited (HashMap uses extra space)

---

## Part 5: Load Factor and Rehashing

### Load Factor

**Definition:** `Load Factor = size / capacity`

**Example:**
```
capacity = 10
size = 7
Load Factor = 7/10 = 0.7 = 70% full
```

**Why it matters:**
- **Low load factor** → More empty space, fewer collisions, faster
- **High load factor** → Less space wasted, more collisions, slower

**Java's default:** 0.75 (75% full triggers resize)

### Rehashing

**When load factor exceeds threshold (0.75):**
```
1. Create new array (usually 2x capacity)
2. Rehash all entries (recalculate indices)
3. Transfer to new array
4. Replace old array
```

**Example:**
```
Old array (capacity 10, size 8):
Index 0: ["A"] → null
Index 1: ["B"] → ["C"] → null
Index 2: ["D"] → null
...
Load factor = 8/10 = 0.8 > 0.75 → RESIZE!

New array (capacity 20):
Index 0: ["A"] → null  (recalculated hash)
Index 3: ["B"] → null  (different index!)
Index 7: ["C"] → null
Index 9: ["D"] → null
...
```

**Why recalculate?**
```
"CS101".hashCode() % 10 = 6  (old)
"CS101".hashCode() % 20 = 13 (new) ← Different index!
```

**Cost:** O(n) for rehashing, but rare (amortized O(1))

---

## 🎓 Key Concepts Summary

### 1. Hash Function
- **Purpose:** Convert key to array index
- **Requirements:** Deterministic, uniform, fast
- **Java:** Uses `hashCode()` method

### 2. Collision Handling
- **Separate Chaining:** Each index stores linked list
- **Alternative:** Open addressing (find next empty slot)

### 3. O(1) Magic
**Why HashMap is O(1):**
```
Traditional search:  [A][B][C][D][E] → Check each: O(n)
HashMap:            key → hash → index → Found: O(1)!
```

### 4. Trade-offs
**Advantages:**
- ✅ Extremely fast operations (O(1) average)
- ✅ Flexible keys (any hashable type)
- ✅ Dynamic sizing

**Disadvantages:**
- ❌ No ordering
- ❌ Extra memory for array + chains
- ❌ Hash function overhead

---

## 💡 Common Patterns

### 1. Counting Pattern
```java
HashMap<String, Integer> counts = new HashMap<>();
for (String item : items) {
    counts.put(item, counts.getOrDefault(item, 0) + 1);
}
```

### 2. Grouping Pattern
```java
HashMap<String, ArrayList<Item>> groups = new HashMap<>();
for (Item item : items) {
    String key = item.getCategory();
    if (!groups.containsKey(key)) {
        groups.put(key, new ArrayList<>());
    }
    groups.get(key).add(item);
}
```

### 3. Lookup Table Pattern
```java
HashMap<String, String> lookup = new HashMap<>();
lookup.put("CS", "Computer Science");
lookup.put("DS", "Data Science");

String fullName = lookup.get("CS");  // "Computer Science"
```

### 4. Two-Sum Pattern (Interview Favorite!)
```java
// Find two numbers that sum to target
HashMap<Integer, Integer> seen = new HashMap<>();
for (int i = 0; i < nums.length; i++) {
    int complement = target - nums[i];
    if (seen.containsKey(complement)) {
        return new int[]{seen.get(complement), i};
    }
    seen.put(nums[i], i);
}
```

Remember: HashMap is your go-to for **fast key-based lookups**! 🗝️⚡
