# Module 1: Java Generics, Wildcards, and Enums - Detailed Explanation

## 🎯 Overview
This module teaches you how to write **flexible, reusable, and type-safe** code using Java Generics. Think of generics as creating a "template" that works with any data type while keeping your code safe from errors.

---

## 1. The Problem: Code Without Generics

### Why We Need Generics

**Look at this code:**
```java
NonGenericBox box1 = new NonGenericBox();
box1.setValue("Hello");
String str = (String) box1.getValue();  // Need to cast!
```

**Problems:**
1. ❌ **Need to cast** - You must remember what type you stored
2. ❌ **No compile-time safety** - Compiler can't catch mistakes
3. ❌ **Runtime errors possible** - Wrong cast = program crashes

**Example of what can go wrong:**
```java
NonGenericBox box = new NonGenericBox();
box.setValue("Hello");
Integer num = (Integer) box.getValue();  // Compiles fine, crashes at runtime!
```

### The Solution: Generics

**Same code with generics:**
```java
GenericBox<String> box2 = new GenericBox<>();
box2.setValue("Hello");
String str2 = box2.getValue();  // No casting needed!
```

**Benefits:**
1. ✅ **No casting** - Compiler knows the type
2. ✅ **Compile-time safety** - Errors caught before running
3. ✅ **Cleaner code** - More readable and maintainable

**This won't even compile (catches error early!):**
```java
GenericBox<String> box = new GenericBox<>();
box.setValue(123);  // Compiler error: cannot convert int to String
```

---

## 2. Understanding Generic Type Parameters

### Naming Conventions (Very Important!)

Java uses **single uppercase letters** by convention:

```java
T - Type (most common, general purpose)
E - Element (used in collections)
K - Key (used in maps)
V - Value (used in maps)
N - Number
S, U, V - Second, third, fourth types
```

**Why these conventions?**
- Makes code instantly recognizable as generic
- Matches Java's standard library (ArrayList<E>, HashMap<K,V>)
- Short names save space but still meaningful

### Examples from Code:

**T for Type:**
```java
class GenericBox<T> {
    private T value;  // T is a placeholder
    
    public void setValue(T value) { ... }
    public T getValue() { ... }
}

// When you use it:
GenericBox<String> stringBox = new GenericBox<>();  // T becomes String
GenericBox<Integer> intBox = new GenericBox<>();    // T becomes Integer
```

**K, V for Key-Value:**
```java
class DataEntry<K, V> {
    private K key;    // K for Key
    private V value;  // V for Value
}

// Usage:
DataEntry<String, Integer> entry = new DataEntry<>("StudentID", 2024001);
//        K=String  V=Integer
```

**Multiple Type Parameters:**
```java
class Pair<T, U> {  // Two different types
    private T first;
    private U second;
}

// Usage:
Pair<String, Major> studentMajor = new Pair<>("Alice", Major.COMPUTER_SCIENCE);
//   T=String U=Major
```

---

## 3. Generic Methods

### What Are Generic Methods?

Generic methods can work with **any type**, even if the class isn't generic!

**Syntax:**
```java
public static <T> void printArray(T[] array) {
//            ^^^  This declares T for this method only
```

The `<T>` **before** the return type declares this is a generic method.

### Example 1: Print Any Array

```java
public static <T> void printArray(T[] array) {
    for (T element : array) {  // T works for any type
        System.out.print(element + " ");
    }
}

// Works with any type:
Integer[] numbers = {1, 2, 3};
String[] words = {"Java", "Python"};
printArray(numbers);  // T becomes Integer
printArray(words);    // T becomes String
```

### Example 2: Generic Method with Bounds

**Bounded Type Parameter:** Forces T to be a specific type or subclass

```java
public static <N extends Number> double sum(N[] numbers) {
//            ^^^^^^^^^^^^^^^^  N must be Number or subclass
    double total = 0.0;
    for (N num : numbers) {
        total += num.doubleValue();  // Can use Number methods!
    }
    return total;
}

// Works with:
Integer[] ints = {1, 2, 3};
Double[] doubles = {1.5, 2.5};
sum(ints);     // N = Integer (extends Number ✓)
sum(doubles);  // N = Double (extends Number ✓)

// Won't work with:
String[] strings = {"a", "b"};
sum(strings);  // Compiler error: String doesn't extend Number
```

**Why bounds are useful:**
- You can call methods from the bound type (like `doubleValue()`)
- Provides safety while keeping flexibility

### Example 3: Multiple Type Parameters

```java
public static <K, V> void printMap(K key, V value) {
    System.out.println("Key: " + key + ", Value: " + value);
}

// Can mix any types:
printMap("Name", "Alice");      // K=String, V=String
printMap(101, 95.5);            // K=Integer, V=Double
printMap("Age", 20);            // K=String, V=Integer
```

---

## 4. Wildcards: The Flexible Generics

Wildcards (`?`) let you write methods that work with **unknown types**.

### Upper Bounded Wildcard: `? extends Type`

**Use when:** You want to **READ** from a structure (Producer)

```java
public static double sumNumbers(List<? extends Number> numbers) {
//                                   ^^^^^^^^^^^^^^^^
//                                   "Any type that IS-A Number"
    double sum = 0.0;
    for (Number num : numbers) {  // Can read as Number
        sum += num.doubleValue();
    }
    return sum;
}

// Works with:
List<Integer> ints = Arrays.asList(1, 2, 3);
List<Double> doubles = Arrays.asList(1.1, 2.2);
sumNumbers(ints);     // Integer extends Number ✓
sumNumbers(doubles);  // Double extends Number ✓
```

**Why can't you add?**
```java
public static void broken(List<? extends Number> numbers) {
    numbers.add(5);  // Compiler error!
}
```

**Reason:** The list could be `List<Integer>`, `List<Double>`, etc. If it's `List<Integer>`, you can't add a `Double`. Java prevents this error!

**Memory Trick:** "extends" = "export" = read/produce data

### Lower Bounded Wildcard: `? super Type`

**Use when:** You want to **WRITE** to a structure (Consumer)

```java
public static void addIntegers(List<? super Integer> list) {
//                                 ^^^^^^^^^^^^^^^
//                                 "Any type that is Integer or ABOVE"
    list.add(1);    // Can add Integers
    list.add(2);
    list.add(3);
}

// Works with:
List<Integer> intList = new ArrayList<>();
List<Number> numList = new ArrayList<>();
List<Object> objList = new ArrayList<>();

addIntegers(intList);  // Integer ✓
addIntegers(numList);  // Number is super type of Integer ✓
addIntegers(objList);  // Object is super type of Integer ✓
```

**Why can you add Integers?**
- If it's `List<Integer>`, Integer fits ✓
- If it's `List<Number>`, Integer is a Number ✓
- If it's `List<Object>`, Integer is an Object ✓

**Why can't you read with correct type?**
```java
public static void broken(List<? super Integer> list) {
    Integer x = list.get(0);  // Compiler error!
    Object x = list.get(0);   // Only this works
}
```

**Reason:** Could be `List<Number>` or `List<Object>`. You can only guarantee it's an Object.

**Memory Trick:** "super" = "sink" = write/consume data

### Unbounded Wildcard: `?`

**Use when:** You don't care about the type or only use Object methods

```java
public static void printList(List<?> list) {
//                               ^^
//                               "Any type at all"
    for (Object element : list) {  // Can only read as Object
        System.out.println(element);
    }
}

// Works with absolutely any list:
printList(Arrays.asList(1, 2, 3));           // List<Integer>
printList(Arrays.asList("a", "b"));          // List<String>
printList(Arrays.asList(true, false));       // List<Boolean>
```

**Limitations:**
- Can't add anything (except null)
- Can only read as Object

### PECS Principle: Producer Extends, Consumer Super

**Remember this:**
- **Producer** (reading/getting): Use `? extends T`
- **Consumer** (writing/adding): Use `? super T`
- **Both**: Use exact type `T`

**Example from code:**
```java
// Producer: returns data to you
public static void printGPA(StudentProfile<? extends Number> profile) {
    System.out.println(profile.getGpa());  // Reading GPA
}

// Consumer: you give it data
public static void addToCollection(List<? super StudentProfile<Double>> list, 
                                  StudentProfile<Double> profile) {
    list.add(profile);  // Writing to list
}
```

---

## 5. Enumerated Types (Enums)

### What Are Enums?

Enums define a **fixed set of constants** (options that never change).

**Instead of this (BAD):**
```java
String status = "junior";  // Could be typo: "junoir", "Junior", "jr"
```

**Use this (GOOD):**
```java
StudentStatus status = StudentStatus.JUNIOR;  // No typos possible!
```

### Simple Enum

```java
enum StudentStatus {
    FRESHMAN,    // Constant 1
    SOPHOMORE,   // Constant 2
    JUNIOR,      // Constant 3
    SENIOR,      // Constant 4
    GRADUATED    // Constant 5
}

// Usage:
StudentStatus status = StudentStatus.JUNIOR;
System.out.println(status);  // Prints: JUNIOR
```

**Benefits:**
- ✅ Type-safe (can't assign wrong values)
- ✅ Compile-time checking
- ✅ Auto-complete in IDE
- ✅ Can't create invalid values

### Enum with Methods

Enums can have **methods** just like classes!

```java
enum StudentStatus {
    FRESHMAN,
    SOPHOMORE,
    JUNIOR,
    SENIOR,
    GRADUATED;
    
    // Enum can have methods!
    public String getDescription() {
        switch(this) {
            case FRESHMAN: return "First Year Student";
            case SOPHOMORE: return "Second Year Student";
            // ... etc
        }
    }
}

// Usage:
StudentStatus status = StudentStatus.JUNIOR;
System.out.println(status.getDescription());  // "Third Year Student"
```

### Enum with Fields and Constructor

Enums can store **data** for each constant!

```java
enum Major {
    COMPUTER_SCIENCE("CS", "Computer Science"),
    //                ^^^   ^^^^^^^^^^^^^^^^^
    //                These are passed to constructor
    DATA_SCIENCE("DS", "Data Science");
    
    private String code;      // Field 1
    private String fullName;  // Field 2
    
    // Constructor (always private in enums)
    Major(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }
    
    // Getter methods
    public String getCode() { return code; }
    public String getFullName() { return fullName; }
}

// Usage:
Major major = Major.COMPUTER_SCIENCE;
System.out.println(major.getCode());      // "CS"
System.out.println(major.getFullName());  // "Computer Science"
```

**How it works:**
1. When you create `COMPUTER_SCIENCE("CS", "Computer Science")`
2. Java calls `Major("CS", "Computer Science")` constructor
3. Stores these values in the fields
4. You can access them via getters

### Iterating Through Enum Values

```java
// Get all enum constants
for (Major m : Major.values()) {
    System.out.println(m.getCode() + " - " + m.getFullName());
}

// Output:
// CS - Computer Science
// IS - Information Systems
// SE - Software Engineering
// DS - Data Science
```

### Enum with Numeric Values

```java
enum Grade {
    A_PLUS(4.0),
    A(4.0),
    B_PLUS(3.3),
    B(3.0);
    
    private double gradePoint;
    
    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }
    
    public double getGradePoint() {
        return gradePoint;
    }
}

// Usage:
Grade grade = Grade.A_MINUS;
System.out.println(grade.getGradePoint());  // 3.7
```

---

## 6. Putting It All Together: StudentProfile

### The Complete Generic Class

```java
class StudentProfile<T extends Number> {
//                   ^^^^^^^^^^^^^^^^^
//                   T must be a Number type (Float, Double, Integer, etc.)
    
    private T gpa;  // Can be any Number type
    private StudentStatus status;  // Enum type
    private Major major;           // Enum type
    
    // Generic method in generic class
    public <U> void printWithLabel(U label) {
    //     ^^^
    //     U is different from T! This method's own type parameter
        System.out.println(label + " -> " + name);
    }
}
```

**Why `<T extends Number>`?**
- GPA should be numeric (3.85, 3.7, etc.)
- Prevents: `StudentProfile<String>` (doesn't make sense!)
- Allows: `StudentProfile<Double>`, `StudentProfile<Float>`

**Usage:**
```java
StudentProfile<Double> alice = new StudentProfile<>(
    "2024001", "Alice", 3.85,  // 3.85 is Double
    StudentStatus.JUNIOR,       // Enum for status
    Major.COMPUTER_SCIENCE      // Enum for major
);

// Generic method with different type
alice.printWithLabel("Top Student");  // U = String
alice.printWithLabel(2024);           // U = Integer
```

---

## 🎓 Key Takeaways

### 1. Generics Benefits
- **Type Safety**: Errors caught at compile-time
- **No Casting**: Cleaner, more readable code
- **Reusability**: One class/method works for many types

### 2. When to Use What

| Scenario | Use |
|----------|-----|
| Create reusable container | `class Box<T>` |
| Method works with any type | `public <T> void method()` |
| Read from collection | `<? extends T>` |
| Write to collection | `<? super T>` |
| Fixed set of constants | `enum` |

### 3. Common Patterns

**Pattern 1: Generic Collection**
```java
class Box<T> {
    private T item;
    public void set(T item) { this.item = item; }
    public T get() { return item; }
}
```

**Pattern 2: Generic Method**
```java
public static <T> T getFirst(List<T> list) {
    return list.get(0);
}
```

**Pattern 3: Bounded Generic**
```java
class NumberBox<N extends Number> {
    public double asDouble(N number) {
        return number.doubleValue();  // Can use Number methods
    }
}
```

---

## 💡 Real-World Applications

**1. Collections** - ArrayList, HashMap use generics
```java
ArrayList<String> names = new ArrayList<>();  // Type-safe list
```

**2. APIs** - Return any type safely
```java
public <T> T parseJSON(String json, Class<T> type) { ... }
User user = parseJSON(jsonString, User.class);
```

**3. Database Results** - Map to any object type
```java
public <T> List<T> query(String sql, Class<T> type) { ... }
```

**4. Enums** - Days of week, months, error codes, status values
```java
enum HttpStatus { OK, NOT_FOUND, SERVER_ERROR }
```

This foundation is used everywhere in Java programming!
