# Campus Social Network - Data Structures Learning Project

[![Java](https://img.shields.io/badge/Java-11%2B-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Education](https://img.shields.io/badge/Purpose-Education-green.svg)]()

A comprehensive, hands-on Java project series designed to teach **Data Structures** through building a relatable campus social network system. Each module introduces fundamental data structures while maintaining project continuity, making complex concepts easy to understand through real-world applications.

## 📚 Table of Contents

- [Overview](#overview)
- [Why This Project?](#why-this-project)
- [Project Structure](#project-structure)
- [Modules](#modules)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Learning Path](#learning-path)
- [Key Concepts Covered](#key-concepts-covered)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Overview

This project teaches data structures by building features of a **Campus Social Network** that students interact with daily. Each module builds upon the previous one, creating a complete system by the end.

**What You'll Build:**
- 👤 Student profiles with type-safe data (Generics)
- 👥 Friend management systems (LinkedList & ArrayList)
- 💬 Real-time messaging (Queue & Stack)
- 🏢 Campus organization hierarchies (Trees)
- 🔍 Interest-based matching (HashMap)
- 🌐 Social network analysis (Graphs)

## 💡 Why This Project?

### Traditional Approach ❌
```
"Here's a LinkedList. It has nodes. It's good for insertion."
// Abstract, hard to remember, boring examples
```

### This Project ✅
```
"Let's build a friend list! 
- ArrayList for your activity feed (fast viewing)
- LinkedList for friend connections (easy add/remove)
See the difference? Feel the difference!"
```

**Benefits:**
- ✅ **Relatable**: Everyone uses social networks
- ✅ **Progressive**: Each module builds on previous work
- ✅ **Practical**: Real algorithms used by actual platforms
- ✅ **Complete**: Covers entire Data Structures curriculum
- ✅ **Hands-on**: Working code, not just theory

# 📁 Project Structure

```
campus-social-network/
│
├── Module1_Generics/
│   ├── src/
│   │   └── CampusSocialNetworkModule1.java
│   └── M1_README.md
│
├── Module2_Lists/
│   ├── src/
│   │   └── CampusSocialNetworkModule2.java
│   └── M2_README.md
│
├── Module3_StackQueue/
│   ├── src/
│   │   └── CampusSocialNetworkModule3.java
│   └── M3_README.md
│
├── Module4_Trees/
│   ├── src/
│   │   └── CampusSocialNetworkModule4.java
│   └── M4_README.md
│
├── Module5_HashMap/
│   ├── src/
│   │   └── CampusSocialNetworkModule5.java
│   └── M5_README.md
│
├── Module6_Graphs/
│   ├── src/
│   │   └── CampusSocialNetworkModule6.java
│   └── M6_README.md
│
├── README.md
└── LICENSE
```

## 📖 Modules

### Module 1: Java Generics & Enums
**What You'll Learn:**
- Generic classes and methods
- Type parameters (T, E, K, V)
- Wildcards (? extends, ? super, ?)
- Enumerated types with fields and methods

**What You'll Build:**
- Type-safe student profile system
- Flexible data containers
- Status and major enumerations

**Key Files:**
- `CampusSocialNetworkModule1.java` - Complete implementation
- `M1_README.md` - Detailed theory

[📘 View Module 1 Details](Module1_Generics/M1_README.md)

---

### Module 2: ArrayList & LinkedList
**What You'll Learn:**
- ArrayList operations (add, get, set, remove)
- Singly, Doubly, Circular LinkedLists
- Node structure and pointers
- Performance trade-offs

**What You'll Build:**
- Activity feed with ArrayList
- Friend management with LinkedList
- All 4 types of linked lists

**Key Files:**
- `CampusSocialNetworkModule2.java` - Complete implementation
- `M2_README.md` - Detailed theory

[📘 View Module 2 Details](Module2_Lists/M2_README.md)

---

### Module 3: Stack & Queue
**What You'll Learn:**
- Stack (LIFO) operations
- Queue (FIFO) operations
- Circular queue implementation
- Real-world applications

**What You'll Build:**
- Message inbox (Queue)
- Notification history (Stack)
- Circular queue for efficiency

**Key Files:**
- `CampusSocialNetworkModule3.java` - Complete implementation
- `M3_README.md` - Detailed theory

[📘 View Module 3 Details](Module3_StackQueue/M3_README.md)

---

### Module 4: Trees
**What You'll Learn:**
- Binary tree types (Full, Complete, Skewed, Balanced)
- Binary Search Trees (BST)
- AVL Trees (self-balancing)
- Tree traversals (In-order, Pre-order, Post-order, Level-order)

**What You'll Build:**
- Campus organization hierarchy
- Student GPA ranking system
- Self-balancing student registry

**Key Files:**
- `CampusSocialNetworkModule4.java` - Complete implementation
- `M4_README.md` - Detailed theory

[📘 View Module 4 Details](Module4_Trees/M4_README.md)

---

### Module 5: HashMap
**What You'll Learn:**
- Hash functions and collision handling
- HashMap operations (put, get, remove)
- keySet(), entrySet(), containsKey()
- O(1) average time complexity

**What You'll Build:**
- Course enrollment system
- Interest-based student matching
- Fast lookup systems

**Key Files:**
- `CampusSocialNetworkModule5.java` - Complete implementation
- `M5_README.md` - Detailed theory

[📘 View Module 5 Details](Module5_HashMap/M5_README.md)

---

### Module 6: Graphs
**What You'll Learn:**
- Directed vs Undirected graphs
- Weighted edges
- Adjacency Matrix & Adjacency List
- BFS and DFS traversals
- Shortest path algorithms

**What You'll Build:**
- Social network connections
- Friend recommendation system
- Connection analysis tools

**Key Files:**
- `CampusSocialNetworkModule6.java` - Complete implementation
- `M6_README.md` - Detailed theory

[📘 View Module 6 Details](Module6_Graphs/M6_README.md)

---

## 🔧 Prerequisites

### Required
- **Java JDK 8 or higher**
- **Basic Java knowledge**:
  - Variables and data types
  - Classes and objects
  - Methods
  - Arrays
  - Loops and conditionals

### Recommended
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code with Java extensions
- **Git**: For cloning the repository

### Check Your Java Version
```bash
java -version
```

Should show version 8 or higher (e.g., `java version "17.0.1"`)

## 📥 Installation

### Option 1: Clone the Repository
```bash
# Clone this repository
git clone https://github.com/yourusername/Data_Structure_Social_Network.git

# Navigate to the project
cd Data_Structure_Social_Network
```

### Option 2: Download ZIP
1. Click the green **Code** button above
2. Select **Download ZIP**
3. Extract the ZIP file
4. Open in your IDE

### Setting Up in IntelliJ IDEA

1. **Open IntelliJ IDEA**
2. **File → Open**
3. Navigate to the cloned/extracted folder
4. Select the **module folder** you want to run (e.g., `Module1_Generics`)
5. Click **OK**
6. Wait for IntelliJ to index the project
7. Navigate to `src/` folder
8. Right-click on the `.java` file → **Run**

### Setting Up in Eclipse

1. **Open Eclipse**
2. **File → Open Projects from File System**
3. Click **Directory** and select module folder
4. Click **Finish**
5. Right-click project → **Run As → Java Application**

### Setting Up in VS Code

1. **Open VS Code**
2. **File → Open Folder**
3. Select module folder
4. Install **Extension Pack for Java** (if not installed)
5. Open the `.java` file
6. Click **Run** button or press **F5**

## 🚀 Usage

### Running Individual Modules

Each module is **standalone** and can be run independently:

```bash
# Navigate to module directory
cd Module1_Generics/src

# Compile
javac CampusSocialNetworkModule1.java

# Run
java CampusSocialNetworkModule1
```

### Expected Output Example (Module 1)

```
=== 1. WITHOUT GENERICS vs WITH GENERICS ===

Without Generics (needs casting): Hello
With Generics (no casting): Hello

=== 2. PARAMETER NAMING CONVENTIONS ===

K (Key): StudentID
V (Value): 2024001
T: Alice, U: COMPUTER_SCIENCE
E (Element): 42

=== 3. GENERIC METHODS ===

Integer array: 1 2 3 4 5 
String array: Java Python C++ 
Sum: 7.5
...
```

### Progressive Learning Path

**Recommended Order:**
1. ✅ **Module 1** - Understand generics (foundation for all modules)
2. ✅ **Module 2** - Learn lists (most commonly used)
3. ✅ **Module 3** - Master Stack & Queue (LIFO vs FIFO)
4. ✅ **Module 4** - Explore Trees (hierarchical data)
5. ✅ **Module 5** - HashMap magic (O(1) operations)
6. ✅ **Module 6** - Graph algorithms (putting it all together)

### Experimentation Ideas

**Modify and extend the code:**

**Module 1:**
```java
// Create your own generic class
class Box<T> {
    private T item;
    // Add your methods
}
```

**Module 2:**
```java
// Add a "block friend" feature
friendList.removeFriend("Bob");
blockedList.add("Bob");
```

**Module 3:**
```java
// Implement priority queue
// High priority messages go to front
```

**Module 4:**
```java
// Add student deletion from AVL tree
// Maintain balance after deletion
```

**Module 5:**
```java
// Create a group chat system
HashMap<String, ArrayList<Message>> groupChats;
```

**Module 6:**
```java
// Implement friend suggestions
// (friends of friends not yet connected)
```

## 📚 Learning Path

### For Complete Beginners

**Week 1-2: Module 1 & 2**
- Day 1-3: Read Module 1 explanation, run code, experiment
- Day 4-7: Understand ArrayList vs LinkedList differences
- Day 8-14: Practice implementing your own versions

**Week 3-4: Module 3 & 4**
- Day 15-21: Master Stack & Queue, build your own queue
- Day 22-28: Understand tree types, implement BST operations

**Week 5-6: Module 5 & 6**
- Day 29-35: HashMap operations, build word counter
- Day 36-42: Graph traversals, implement your own BFS/DFS

### For Intermediate Learners

- **Day 1-2**: Skim all modules, identify weak areas
- **Day 3-7**: Deep dive into challenging topics
- **Day 8-14**: Extend projects with new features

### Study Tips

1. **Don't just read** - Type the code yourself
2. **Break things** - Remove code, see what errors appear
3. **Add features** - Extend beyond what's provided
4. **Draw diagrams** - Visualize data structures on paper
5. **Teach others** - Best way to solidify understanding

## 🎓 Key Concepts Covered

### Data Structures
- ✅ Generics & Type Safety
- ✅ ArrayList (Dynamic Arrays)
- ✅ LinkedList (4 types)
- ✅ Stack (LIFO)
- ✅ Queue (FIFO)
- ✅ Binary Trees (4 types)
- ✅ Binary Search Trees
- ✅ AVL Trees (Self-balancing)
- ✅ HashMap (Hash Tables)
- ✅ Graphs (Directed & Undirected)

### Algorithms
- ✅ Tree Traversals (4 types)
- ✅ Breadth-First Search (BFS)
- ✅ Depth-First Search (DFS)
- ✅ Shortest Path (unweighted)
- ✅ AVL Rotations (4 types)
- ✅ Hash Functions
- ✅ Collision Handling

### Time Complexity Analysis
- ✅ O(1) - Constant time
- ✅ O(log n) - Logarithmic
- ✅ O(n) - Linear
- ✅ O(n log n) - Linearithmic
- ✅ Understanding trade-offs

### Object-Oriented Programming
- ✅ Classes and Objects
- ✅ Inheritance
- ✅ Encapsulation
- ✅ Generic Programming
- ✅ Enumerations

## 🎯 Real-World Applications

Each module teaches concepts used in actual applications:

| Module | Real Application |
|--------|-----------------|
| **Generics** | Java Collections Framework, Type-safe APIs |
| **ArrayList** | Social media feeds, Shopping carts |
| **LinkedList** | Browser history, Music playlists, Undo/Redo |
| **Stack** | Expression evaluation, Function calls, Undo systems |
| **Queue** | Print spoolers, Task scheduling, Message brokers |
| **Trees** | File systems, Database indexes, Decision trees |
| **HashMap** | Caching, Databases, Symbol tables, Counting |
| **Graphs** | Social networks, GPS, Recommendation systems |

## 🤝 Contributing

Contributions are welcome! Here's how you can help:

### Reporting Issues
1. Check if issue already exists
2. Create a detailed bug report
3. Include steps to reproduce
4. Mention your Java version and IDE

### Suggesting Enhancements
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow Java naming conventions
- Add comments for complex logic
- Include documentation for new features
- Test your code before submitting

### Ideas for Contributions
- 🌍 Translate explanations to other languages
- 📝 Add more real-world examples
- 🎨 Create visual diagrams
- 🧪 Add unit tests
- 📹 Create video tutorials
- 🐛 Fix bugs
- ✨ Improve code efficiency

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

**What this means:**
- ✅ Use for personal learning
- ✅ Use for teaching in classrooms
- ✅ Modify and extend
- ✅ Share with others
- ⚠️ Provide attribution
- ❌ Hold author liable

## 👨‍🏫 For Educators

This project is **perfect for teaching**:

### Suggested Use in Curriculum

**Week-by-Week Plan:**
- Week 1-2: Generics & Type Safety
- Week 3-4: Lists & Their Applications  
- Week 5-6: Stack & Queue
- Week 7-9: Trees & Balancing
- Week 10-11: Hashing & HashMaps
- Week 12-14: Graphs & Algorithms

### Assessment Ideas
1. **Quizzes**: After each module on concepts
2. **Coding Assignments**: Extend modules with new features
3. **Project**: Combine all modules into complete social network
4. **Presentations**: Students explain data structure choices

### Classroom Activities
- **Live Coding**: Build modules together in class
- **Pair Programming**: Students work in pairs on extensions
- **Code Review**: Students review each other's modifications
- **Whiteboard Sessions**: Draw data structures before coding

## 🙏 Acknowledgments

### Inspiration
- Real social networks (Facebook, Twitter, LinkedIn)
- Need for relatable programming examples
- Student feedback on traditional data structure teaching

### Resources
- Java Documentation
- Classic data structure textbooks
- Online coding communities

### Special Thanks
- To all students who will use this to learn
- To educators who will teach with this
- To contributors who will improve this


### Stay Updated
- ⭐ Star this repository
- 👀 Watch for updates
- 🍴 Fork to customize

## 🗺️ Roadmap

### Planned Features
- [ ] Module 7: Advanced Trees (Red-Black, B-Trees)
- [ ] Module 8: Advanced Graphs (Dijkstra, A*)
- [ ] Module 9: String Algorithms (Trie, KMP)
- [ ] GUI version using JavaFX
- [ ] Database integration
- [ ] Unit tests for all modules
- [ ] Performance benchmarking tools

### Wish List
- Video tutorials for each module
- Interactive web version
- Mobile app version
- Gamification elements

---

## ⭐ Star History

If this project helped you learn, please ⭐ **star** this repository!

**Made with ❤️ for students learning Data Structures**

---

### 📊 Quick Stats

![Lines of Code](https://img.shields.io/badge/Lines%20of%20Code-5000%2B-brightgreen)
![Modules](https://img.shields.io/badge/Modules-6-blue)
![Concepts](https://img.shields.io/badge/Concepts-100%2B-orange)
![Examples](https://img.shields.io/badge/Examples-Real--World-purple)

---

**Happy Learning! 🚀**

*Remember: The best way to learn is by doing. Don't just read the code - modify it, break it, fix it, and make it yours!*
