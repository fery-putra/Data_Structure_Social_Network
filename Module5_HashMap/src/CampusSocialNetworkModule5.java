// Module 5: HashMap - Student Interest Matching System
// HashMap provides O(1) average time for search, insert, and delete operations

import java.util.*;

// Student Profile with interests
class InterestStudent {
    private String name;
    private String studentId;
    private List<String> interests;
    private String major;

    public InterestStudent(String name, String studentId, String major) {
        this.name = name;
        this.studentId = studentId;
        this.major = major;
        this.interests = new ArrayList<>();
    }

    public void addInterest(String interest) {
        interests.add(interest);
    }

    public List<String> getInterests() {
        return interests;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMajor() {
        return major;
    }

    @Override
    public String toString() {
        return name + " (" + studentId + ", " + major + ") - Interests: " + interests;
    }
}

// Course Enrollment System using HashMap
class CourseEnrollment {
    // HashMap: courseCode -> List of enrolled students
    private HashMap<String, ArrayList<String>> courseStudents;

    // HashMap: studentId -> List of enrolled courses
    private HashMap<String, ArrayList<String>> studentCourses;

    // HashMap: courseCode -> course name
    private HashMap<String, String> courseNames;

    public CourseEnrollment() {
        courseStudents = new HashMap<>();
        studentCourses = new HashMap<>();
        courseNames = new HashMap<>();
    }

    // Add a new course
    public void addCourse(String courseCode, String courseName) {
        courseNames.put(courseCode, courseName);  // put(key, value)
        courseStudents.put(courseCode, new ArrayList<>());
        System.out.println("✓ Course added: " + courseName + " (" + courseCode + ")");
    }

    // Enroll student in course
    public void enrollStudent(String studentId, String studentName, String courseCode) {
        // Check if course exists using containsKey()
        if (!courseStudents.containsKey(courseCode)) {
            System.out.println("✗ Course " + courseCode + " does not exist!");
            return;
        }

        // Add student to course using get()
        courseStudents.get(courseCode).add(studentName);

        // Add course to student's list
        if (!studentCourses.containsKey(studentId)) {
            studentCourses.put(studentId, new ArrayList<>());
        }
        studentCourses.get(studentId).add(courseCode);

        System.out.println("✓ " + studentName + " enrolled in " +
                courseNames.get(courseCode));
    }

    // Get all students in a course
    public void displayCourseStudents(String courseCode) {
        if (!courseStudents.containsKey(courseCode)) {
            System.out.println("✗ Course not found!");
            return;
        }

        System.out.println("\n=== " + courseNames.get(courseCode) + " (" +
                courseCode + ") ===");
        ArrayList<String> students = courseStudents.get(courseCode);

        if (students.isEmpty()) {
            System.out.println("No students enrolled yet.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i));
            }
        }
    }

    // Get all courses for a student
    public void displayStudentCourses(String studentId, String studentName) {
        if (!studentCourses.containsKey(studentId)) {
            System.out.println("✗ Student not enrolled in any courses!");
            return;
        }

        System.out.println("\n=== Courses for " + studentName + " ===");
        ArrayList<String> courses = studentCourses.get(studentId);

        for (int i = 0; i < courses.size(); i++) {
            String code = courses.get(i);
            System.out.println((i + 1) + ". " + courseNames.get(code) + " (" + code + ")");
        }
    }

    // Remove student from course
    public void dropCourse(String studentId, String studentName, String courseCode) {
        if (courseStudents.containsKey(courseCode)) {
            courseStudents.get(courseCode).remove(studentName);
        }

        if (studentCourses.containsKey(studentId)) {
            studentCourses.get(studentId).remove(courseCode);
        }

        System.out.println("✓ " + studentName + " dropped " + courseNames.get(courseCode));
    }

    // Display all courses using keySet()
    public void displayAllCourses() {
        System.out.println("\n=== All Available Courses ===");
        Set<String> courseCodes = courseNames.keySet();  // keySet() returns all keys

        int count = 1;
        for (String code : courseCodes) {
            System.out.println(count + ". " + courseNames.get(code) + " (" + code +
                    ") - " + courseStudents.get(code).size() + " students");
            count++;
        }
    }

    // Get total number of courses using size()
    public int getTotalCourses() {
        return courseNames.size();
    }
}

// Interest Matching System using HashMap
class InterestMatcher {
    // HashMap: interest -> List of students who have that interest
    private HashMap<String, ArrayList<InterestStudent>> interestGroups;

    // HashMap: studentId -> Student object
    private HashMap<String, InterestStudent> studentRegistry;

    public InterestMatcher() {
        interestGroups = new HashMap<>();
        studentRegistry = new HashMap<>();
    }

    // Register a student
    public void registerStudent(InterestStudent student) {
        studentRegistry.put(student.getStudentId(), student);

        // Add student to interest groups
        for (String interest : student.getInterests()) {
            if (!interestGroups.containsKey(interest)) {
                interestGroups.put(interest, new ArrayList<>());
            }
            interestGroups.get(interest).add(student);
        }

        System.out.println("✓ Registered: " + student.getName());
    }

    // Find students with similar interests
    public void findMatches(String studentId) {
        if (!studentRegistry.containsKey(studentId)) {
            System.out.println("✗ Student not found!");
            return;
        }

        InterestStudent student = studentRegistry.get(studentId);
        System.out.println("\n=== Matches for " + student.getName() + " ===");

        // Count matches with other students
        HashMap<String, Integer> matchCounts = new HashMap<>();

        for (String interest : student.getInterests()) {
            ArrayList<InterestStudent> group = interestGroups.get(interest);

            for (InterestStudent other : group) {
                if (!other.getStudentId().equals(studentId)) {
                    String otherId = other.getStudentId();
                    matchCounts.put(otherId, matchCounts.getOrDefault(otherId, 0) + 1);
                }
            }
        }

        // Sort by match count (descending)
        List<Map.Entry<String, Integer>> sortedMatches = new ArrayList<>(matchCounts.entrySet());
        sortedMatches.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Display matches
        if (sortedMatches.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            for (Map.Entry<String, Integer> entry : sortedMatches) {
                InterestStudent match = studentRegistry.get(entry.getKey());
                System.out.println("• " + match.getName() + " - " +
                        entry.getValue() + " shared interest(s)");
            }
        }
    }

    // Display all students in an interest group
    public void displayInterestGroup(String interest) {
        if (!interestGroups.containsKey(interest)) {
            System.out.println("✗ No students with interest: " + interest);
            return;
        }

        System.out.println("\n=== Students interested in " + interest + " ===");
        ArrayList<InterestStudent> group = interestGroups.get(interest);

        for (int i = 0; i < group.size(); i++) {
            System.out.println((i + 1) + ". " + group.get(i).getName());
        }
    }

    // Display all interests using entrySet()
    public void displayAllInterests() {
        System.out.println("\n=== All Interest Groups ===");

        // entrySet() returns all key-value pairs
        Set<Map.Entry<String, ArrayList<InterestStudent>>> entries = interestGroups.entrySet();

        int count = 1;
        for (Map.Entry<String, ArrayList<InterestStudent>> entry : entries) {
            System.out.println(count + ". " + entry.getKey() + " (" +
                    entry.getValue().size() + " students)");
            count++;
        }
    }

    // Remove student's interest
    public void removeInterest(String studentId, String interest) {
        if (!studentRegistry.containsKey(studentId)) {
            System.out.println("✗ Student not found!");
            return;
        }

        InterestStudent student = studentRegistry.get(studentId);
        student.getInterests().remove(interest);

        if (interestGroups.containsKey(interest)) {
            interestGroups.get(interest).remove(student);

            // Remove empty interest groups
            if (interestGroups.get(interest).isEmpty()) {
                interestGroups.remove(interest);  // remove(key)
            }
        }

        System.out.println("✓ Removed interest '" + interest + "' from " + student.getName());
    }

    // Clear all data
    public void clearAll() {
        studentRegistry.clear();  // clear() removes all entries
        interestGroups.clear();
        System.out.println("✓ All data cleared!");
    }

    // Check if student exists
    public boolean hasStudent(String studentId) {
        return studentRegistry.containsKey(studentId);  // containsKey()
    }

    // Get student count
    public int getStudentCount() {
        return studentRegistry.size();  // size()
    }
}

public class CampusSocialNetworkModule5 {
    public static void main(String[] args) {
        System.out.println("=== HASHMAP DEMO 1: Course Enrollment ===\n");

        CourseEnrollment enrollment = new CourseEnrollment();

        // Add courses
        enrollment.addCourse("CS101", "Introduction to Programming");
        enrollment.addCourse("DS201", "Data Structures");
        enrollment.addCourse("WEB301", "Web Development");

        System.out.println();

        // Enroll students
        enrollment.enrollStudent("2024001", "Alice", "CS101");
        enrollment.enrollStudent("2024001", "Alice", "DS201");
        enrollment.enrollStudent("2024002", "Bob", "CS101");
        enrollment.enrollStudent("2024002", "Bob", "WEB301");
        enrollment.enrollStudent("2024003", "Charlie", "DS201");
        enrollment.enrollStudent("2024003", "Charlie", "WEB301");

        // Display course students
        enrollment.displayCourseStudents("CS101");
        enrollment.displayCourseStudents("DS201");

        // Display student courses
        enrollment.displayStudentCourses("2024001", "Alice");
        enrollment.displayStudentCourses("2024002", "Bob");

        // Display all courses
        enrollment.displayAllCourses();

        System.out.println("\nTotal courses: " + enrollment.getTotalCourses());

        System.out.println("\n=== HASHMAP DEMO 2: Interest Matching ===\n");

        InterestMatcher matcher = new InterestMatcher();

        // Create students with interests
        InterestStudent alice = new InterestStudent("Alice Johnson", "2024001", "CS");
        alice.addInterest("Machine Learning");
        alice.addInterest("Web Development");
        alice.addInterest("Gaming");

        InterestStudent bob = new InterestStudent("Bob Smith", "2024002", "DS");
        bob.addInterest("Machine Learning");
        bob.addInterest("Data Science");
        bob.addInterest("Gaming");

        InterestStudent charlie = new InterestStudent("Charlie Brown", "2024003", "SE");
        charlie.addInterest("Web Development");
        charlie.addInterest("Mobile Apps");
        charlie.addInterest("Gaming");

        InterestStudent diana = new InterestStudent("Diana Prince", "2024004", "CS");
        diana.addInterest("Machine Learning");
        diana.addInterest("Robotics");

        // Register students
        matcher.registerStudent(alice);
        matcher.registerStudent(bob);
        matcher.registerStudent(charlie);
        matcher.registerStudent(diana);

        // Display all interests
        matcher.displayAllInterests();

        // Find matches for Alice
        matcher.findMatches("2024001");

        // Find matches for Bob
        matcher.findMatches("2024002");

        // Display interest groups
        matcher.displayInterestGroup("Machine Learning");
        matcher.displayInterestGroup("Gaming");

        System.out.println("\nTotal students: " + matcher.getStudentCount());

        System.out.println("\n=== HASHMAP KEY CONCEPTS ===");
        System.out.println("• put(key, value): Add or update entry");
        System.out.println("• get(key): Retrieve value by key");
        System.out.println("• containsKey(key): Check if key exists");
        System.out.println("• remove(key): Delete entry");
        System.out.println("• keySet(): Get all keys");
        System.out.println("• entrySet(): Get all key-value pairs");
        System.out.println("• size(): Get number of entries");
        System.out.println("• clear(): Remove all entries");
        System.out.println("• Average time complexity: O(1) for all operations!");
    }
}