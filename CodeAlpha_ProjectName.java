import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeManager {

    // Define Student class to store name and grade together
    static class Student {
        String name;
        double grade;

        Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

       
        System.out.println("     STUDENT GRADE MANAGEMENT      ");
       
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Student");
            System.out.println("2. View Summary Report");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStudent(scanner, students);
                case 2 -> displayReport(students);
                case 3 -> {
                    System.out.println("\nExiting program... Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Add student and grade
    public static void addStudent(Scanner scanner, ArrayList<Student> students) {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter grade for " + name + ": ");
        double grade = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        students.add(new Student(name, grade));
        System.out.println("✅ Student added successfully!");
    }

    // Display report and statistics
    public static void displayReport(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\n No students found! Add some first.");
            return;
        }

        System.out.println("\n========== Student Grade Report ==========");
        System.out.printf("%-20s %-10s%n", "Student Name", "Grade");
        System.out.println("------------------------------------------");

        double total = 0;
        double highest = students.get(0).grade;
        double lowest = students.get(0).grade;
        String highestName = students.get(0).name;
        String lowestName = students.get(0).name;

        for (Student s : students) {
            System.out.printf("%-20s %-10.2f%n", s.name, s.grade);
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                highestName = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowestName = s.name;
            }
        }

        double average = total / students.size();

        System.out.println("\n------------ Statistics --------------");
        System.out.printf("Average Grade: %.2f%n", average);
        System.out.printf("Highest Grade: %.2f (%s)%n", highest, highestName);
        System.out.printf("Lowest Grade : %.2f (%s)%n", lowest, lowestName);
        System.out.println("--------------------------------------");
    }
}

