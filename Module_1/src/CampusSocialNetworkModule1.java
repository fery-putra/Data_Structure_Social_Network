// Module 1: Java Generics - Student Profile System
// This demonstrates how generics make code reusable and type-safe

// ========== DIFFERENCE: CODE WITHOUT GENERICS vs WITH GENERICS ==========

// WITHOUT GENERICS (Old way - requires casting and not type-safe)
class NonGenericBox {
    private Object value;  // Can store anything, but need casting

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;  // Returns Object, need to cast
    }
}

// WITH GENERICS (New way - type-safe, no casting needed)
class GenericBox<T> {  // T is a type parameter
    private T value;  // T will be replaced with actual type

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;  // Returns exact type, no casting needed
    }
}

// ========== PARAMETER NAMING CONVENTIONS FOR GENERICS ==========
// Single uppercase letters are used by convention:
// E - Element (used in collections like ArrayList<E>)
// K - Key (used in maps like HashMap<K, V>)
// V - Value (used in maps like HashMap<K, V>)
// N - Number
// T - Type (most common generic type)
// S, U, V - For 2nd, 3rd, 4th type parameters

// Example using standard naming conventions
class DataEntry<K, V> {  // K for Key, V for Value
    private K key;
    private V value;

    public DataEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Generic Pair class - can hold any two types of data
class Pair<T, U> {  // T and U are type parameters
    private T first;
    private U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }

    public void setFirst(T first) { this.first = first; }
    public void setSecond(U second) { this.second = second; }
}

// ========== GENERIC METHODS ==========
// Generic methods can be declared in non-generic classes too

class ArrayUtils {
    // Generic method with type parameter <T>
    // The <T> before return type declares this is a generic method
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // Generic method with bounded type parameter
    public static <N extends Number> double sum(N[] numbers) {
        double total = 0.0;
        for (N num : numbers) {
            total += num.doubleValue();
        }
        return total;
    }

    // Generic method with multiple type parameters
    public static <K, V> void printMap(K key, V value) {
        System.out.println("Key: " + key + ", Value: " + value);
    }
}

// Generic Container with generic method
class Container<E> {  // E for Element
    private E element;

    public Container(E element) {
        this.element = element;
    }

    public E getElement() { return element; }

    // Generic method within generic class
    public <T> void displayWithMessage(T message) {
        System.out.println(message + ": " + element);
    }

    // Generic method with Comparable constraint
    public <V extends Comparable<V>> boolean isGreaterThan(V a, V b) {
        return a.compareTo(b) > 0;
    }
}

// ========== WILDCARDS ==========

// 1. UPPER BOUNDED WILDCARD (? extends Type)
// Use when you want to READ from a structure (producer)
class WildcardExamples {

    // Upper bounded: accepts List of Number or any subclass (Integer, Double, etc.)
    public static double sumNumbers(java.util.List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number num : numbers) {  // Can READ as Number
            sum += num.doubleValue();
        }
        // Cannot add to list: numbers.add(5); // Compile error!
        return sum;
    }

    // 2. LOWER BOUNDED WILDCARD (? super Type)
    // Use when you want to WRITE to a structure (consumer)
    public static void addIntegers(java.util.List<? super Integer> list) {
        list.add(1);      // Can ADD Integers
        list.add(2);
        list.add(3);
        // Can only read as Object: Object obj = list.get(0);
    }

    // 3. UNBOUNDED WILDCARD (?)
    // Use when you only need Object methods or don't care about type
    public static void printList(java.util.List<?> list) {
        for (Object element : list) {  // Can only READ as Object
            System.out.println(element);
        }
        // Cannot add: list.add("test"); // Compile error!
    }

    // No wildcard - when you need both read AND write
    public static void processAndAdd(java.util.List<Integer> list) {
        int value = list.get(0);  // Can READ
        list.add(value + 1);       // Can WRITE
    }
}

// Demonstrating wildcard usage in class design
class ProfileManager<T> {
    // Upper bounded wildcard - can work with any Number type
    public static void printGPA(StudentProfile<? extends Number> profile) {
        System.out.println(profile.getName() + "'s GPA: " + profile.getGpa());
    }

    // Unbounded wildcard - works with any StudentProfile type
    public static void printBasicInfo(StudentProfile<?> profile) {
        System.out.println("Student: " + profile.getName());
    }

    // Lower bounded wildcard example
    public static void addToCollection(java.util.List<? super StudentProfile<Double>> list,
                                       StudentProfile<Double> profile) {
        list.add(profile);  // Can add StudentProfile<Double>
    }
}

// ========== ENUMERATED TYPES ==========

// Enum for Student Status
enum StudentStatus {
    FRESHMAN,     // Year 1
    SOPHOMORE,    // Year 2
    JUNIOR,       // Year 3
    SENIOR,       // Year 4
    GRADUATED;

    // Enum can have methods
    public String getDescription() {
        switch(this) {
            case FRESHMAN: return "First Year Student";
            case SOPHOMORE: return "Second Year Student";
            case JUNIOR: return "Third Year Student";
            case SENIOR: return "Fourth Year Student";
            case GRADUATED: return "Alumni";
            default: return "Unknown";
        }
    }
}

// Enum with fields and constructor
enum Major {
    COMPUTER_SCIENCE("CS", "Computer Science"),
    INFORMATION_SYSTEMS("IS", "Information Systems"),
    SOFTWARE_ENGINEERING("SE", "Software Engineering"),
    DATA_SCIENCE("DS", "Data Science");

    private String code;
    private String fullName;

    // Enum constructor (always private)
    Major(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public String getCode() { return code; }
    public String getFullName() { return fullName; }
}

// Enum for Grade
enum Grade {
    A_PLUS(4.0),
    A(4.0),
    A_MINUS(3.7),
    B_PLUS(3.3),
    B(3.0),
    B_MINUS(2.7),
    C_PLUS(2.3),
    C(2.0),
    D(1.0),
    F(0.0);

    private double gradePoint;

    Grade(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public double getGradePoint() {
        return gradePoint;
    }
}

// ========== DECLARATION OF CLASS WITH GENERIC METHOD ==========

// Student Profile using Generics with Bounded Type Parameter
class StudentProfile<T extends Number> {  // T must be Number or subclass
    private String studentId;
    private String name;
    private T gpa;  // Can be Float, Double, Integer, etc.
    private StudentStatus status;
    private Major major;

    public StudentProfile(String studentId, String name, T gpa,
                          StudentStatus status, Major major) {
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
        this.status = status;
        this.major = major;
    }

    // Regular method
    public void displayProfile() {
        System.out.println("=== Student Profile ===");
        System.out.println("ID: " + studentId);
        System.out.println("Name: " + name);
        System.out.println("GPA: " + gpa);
        System.out.println("Status: " + status + " - " + status.getDescription());
        System.out.println("Major: " + major.getFullName() + " (" + major.getCode() + ")");
    }

    // Generic method in generic class
    public <U> void printWithLabel(U label) {
        System.out.println(label + " -> " + name);
    }

    // Generic method with multiple parameters
    public <K, V> void displayAttribute(K attributeName, V attributeValue) {
        System.out.println(attributeName + ": " + attributeValue);
    }

    public T getGpa() { return gpa; }
    public String getName() { return name; }
    public StudentStatus getStatus() { return status; }
    public Major getMajor() { return major; }
}

// Main class to demonstrate all concepts
public class CampusSocialNetworkModule1 {
    public static void main(String[] args) {
        System.out.println("=== 1. WITHOUT GENERICS vs WITH GENERICS ===\n");

        // Without Generics - requires casting, not type-safe
        NonGenericBox box1 = new NonGenericBox();
        box1.setValue("Hello");
        String str = (String) box1.getValue();  // Need to cast!
        System.out.println("Without Generics (needs casting): " + str);

        // With Generics - no casting, type-safe
        GenericBox<String> box2 = new GenericBox<>();
        box2.setValue("Hello");
        String str2 = box2.getValue();  // No casting needed!
        System.out.println("With Generics (no casting): " + str2);

        System.out.println("\n=== 2. PARAMETER NAMING CONVENTIONS ===\n");

        // K, V convention for key-value pairs
        DataEntry<String, Integer> entry = new DataEntry<>("StudentID", 2024001);
        System.out.println("K (Key): " + entry.getKey());
        System.out.println("V (Value): " + entry.getValue());

        // T, U for generic pairs
        Pair<String, Major> studentMajor = new Pair<>("Alice", Major.COMPUTER_SCIENCE);
        System.out.println("T: " + studentMajor.getFirst() + ", U: " + studentMajor.getSecond());

        // E for elements
        Container<Integer> container = new Container<>(42);
        System.out.println("E (Element): " + container.getElement());

        System.out.println("\n=== 3. GENERIC METHODS ===\n");

        // Generic method for arrays
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"Java", "Python", "C++"};

        System.out.print("Integer array: ");
        ArrayUtils.printArray(intArray);
        System.out.print("String array: ");
        ArrayUtils.printArray(strArray);

        // Generic method with bounded type
        Double[] doubleArray = {1.5, 2.5, 3.5};
        System.out.println("Sum: " + ArrayUtils.sum(doubleArray));

        // Generic method with multiple type parameters
        ArrayUtils.printMap("Name", "Alice");
        ArrayUtils.printMap(101, 95.5);

        // Generic method in generic class
        container.displayWithMessage("Container holds");
        container.displayWithMessage(123);  // Different type parameter

        System.out.println("\n=== 4. WILDCARDS ===\n");

        // Upper bounded wildcard (? extends Number)
        java.util.List<Integer> intList = java.util.Arrays.asList(1, 2, 3, 4, 5);
        java.util.List<Double> doubleList = java.util.Arrays.asList(1.1, 2.2, 3.3);

        System.out.println("Sum of integers: " + WildcardExamples.sumNumbers(intList));
        System.out.println("Sum of doubles: " + WildcardExamples.sumNumbers(doubleList));

        // Lower bounded wildcard (? super Integer)
        java.util.List<Number> numberList = new java.util.ArrayList<>();
        WildcardExamples.addIntegers(numberList);
        System.out.println("After adding integers: " + numberList);

        java.util.List<Object> objectList = new java.util.ArrayList<>();
        WildcardExamples.addIntegers(objectList);
        System.out.println("Added to Object list: " + objectList);

        // Unbounded wildcard (?)
        System.out.print("Print any list: ");
        WildcardExamples.printList(intList);

        System.out.println("\n=== 5. ENUMERATED TYPES ===\n");

        // Using enums
        StudentStatus status = StudentStatus.JUNIOR;
        System.out.println("Status: " + status);
        System.out.println("Description: " + status.getDescription());

        Major major = Major.COMPUTER_SCIENCE;
        System.out.println("Major Code: " + major.getCode());
        System.out.println("Major Full Name: " + major.getFullName());

        Grade grade = Grade.A_MINUS;
        System.out.println("Grade: " + grade + " = " + grade.getGradePoint() + " points");

        // Iterate through enum values
        System.out.println("\nAll majors:");
        for (Major m : Major.values()) {
            System.out.println("  " + m.getCode() + " - " + m.getFullName());
        }

        System.out.println("\n=== 6. STUDENT PROFILE WITH GENERICS ===\n");

        // Create student profiles with different numeric types for GPA
        StudentProfile<Double> alice = new StudentProfile<>(
                "2024001", "Alice Johnson", 3.85,
                StudentStatus.JUNIOR, Major.COMPUTER_SCIENCE
        );

        StudentProfile<Float> bob = new StudentProfile<>(
                "2024002", "Bob Smith", 3.7f,
                StudentStatus.SOPHOMORE, Major.DATA_SCIENCE
        );

        // Display profiles
        alice.displayProfile();
        System.out.println();
        bob.displayProfile();
        System.out.println();

        // Using wildcards with StudentProfile
        ProfileManager.printGPA(alice);
        ProfileManager.printGPA(bob);
        ProfileManager.printBasicInfo(alice);

        // Generic methods in StudentProfile
        alice.printWithLabel("Top Student");
        alice.printWithLabel(2024);
        alice.displayAttribute("Favorite Subject", "Data Structures");
        alice.displayAttribute("Credits", 90);

        System.out.println("\n=== SUMMARY ===");
        System.out.println("✓ Generics provide type safety and eliminate casting");
        System.out.println("✓ Naming conventions: T, E, K, V, N for type parameters");
        System.out.println("✓ Upper bounded (? extends): for reading (producer)");
        System.out.println("✓ Lower bounded (? super): for writing (consumer)");
        System.out.println("✓ Unbounded (?): when type doesn't matter");
        System.out.println("✓ Enums provide type-safe constants with methods");
    }
}