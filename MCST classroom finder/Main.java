/**
 * Author: TJ Wangchuk
 * Date: 01/02/26
 * Portfolio project
 *
 * MCVTS Teacher Directory System
 *
 */

// used ai and tutorials to make an array list for an optimal way to store and sort
//ai was also used for the java docs per what we disuccsed in
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a teacher with a name, email, and room number.
 */
class Teacher {
    String name;
    String email;
    String room;

    /**
     * Creates a Teacher object.
     *
     * @param n teacher's name
     * @param e teacher's email
     * @param r teacher's room number
     */
    public Teacher(String n, String e, String r) {
        name = n;
        email = e;
        room = r;
    }
}

/**
 * Main driver class for the MCVTS Teacher Directory System.
 */
public class Main {

    /**
     * Loads teacher data from a file into an ArrayList.
     *
     * @param filename name of the data file
     * @return ArrayList of Teacher objects, or null if file is not found
     */
    // Loads teacher data from a file into an ArrayList
    public static ArrayList<Teacher> loadTeacherData(String filename) {
        ArrayList<Teacher> teachers = new ArrayList<>();

        try {
            Scanner input = new Scanner(new File(filename));

            while (input.hasNextLine()) {
                String name = input.nextLine().trim();
                if (!input.hasNextLine()) break;

                String email = input.nextLine().trim();
                if (!input.hasNextLine()) break;

                String room = input.nextLine().trim();

                teachers.add(new Teacher(name, email, room));
            }

            input.close();
            System.out.println("Loaded " + teachers.size() + " teacher records.\n");

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return null;
        }

        return teachers;
    }

    /**
     * Displays the main menu options.
     * This method does not return a value.
     */
    // Displays main menu options
    public static void displayMenu() {
        System.out.println("========================================");
        System.out.println("   MCVTS Teacher Directory System");
        System.out.println("========================================");
        System.out.println("1. Search by Teacher Name");
        System.out.println("2. Search by Room Number");
        System.out.println("3. List Teachers by Building");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");
    }

    /**
     * Searches for a teacher by full name.
     *
     * @param teachers list of teachers
     * @param searchName name to search for
     * @return true if found, false otherwise
     */
    // Searches for a teacher by full name
    public static boolean searchByName(ArrayList<Teacher> teachers, String searchName) {
        for (Teacher t : teachers) {
            if (t.name.equalsIgnoreCase(searchName)) {
                System.out.println("\nName: " + t.name);
                System.out.println("Email: " + t.email);
                System.out.println("Room: " + t.room + "\n");
                return true;
            }
        }
        return false;
    }

    /**
     * Searches for teachers by room number.
     *
     * @param teachers list of teachers
     * @param roomNumber room number to search
     * @return number of teachers found
     */
    // Searches for teachers by room number
    public static int searchByRoom(ArrayList<Teacher> teachers, String roomNumber) {
        int count = 0;
        System.out.println("\nTeachers in room " + roomNumber + ":");

        for (Teacher t : teachers) {
            if (t.room.equalsIgnoreCase(roomNumber)) {
                System.out.println("- " + t.name);
                count++;
            }
        }

        if (count == 0) {
            System.out.println("No teachers found.");
        }

        System.out.println("Total found: " + count + "\n");
        return count;
    }

    /**
     * Lists teachers by building keyword.
     *
     * @param teachers list of teachers
     * @param building building name or keyword
     * @return number of teachers found
     */
    // Lists teachers by building keyword and calculates total
    public static int listByBuilding(ArrayList<Teacher> teachers, String building) {
        int count = 0;
        System.out.println("\nTeachers in " + building + ":");

        for (Teacher t : teachers) {
            if (t.room.toLowerCase().contains(building.toLowerCase())) {
                System.out.println("- " + t.name + " (Room " + t.room + ")");
                count++;
            }
        }

        if (count > 0) {
            System.out.println("Total teachers: " + count + "\n");
        } else {
            System.out.println("No teachers found.\n");
        }

        return count;
    }

    /**
     * Program entry point.
     * Controls menu navigation and user interaction.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Load teacher data from file
        ArrayList<Teacher> teachers = loadTeacherData("data.txt");
        if (teachers == null) return;

        Scanner userInput = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();

            if (!userInput.hasNextInt()) {
                System.out.println("\nInvalid input.\n");
                userInput.nextLine();
                continue;
            }

            int choice = userInput.nextInt();
            userInput.nextLine();

            // Handle menu choices
            switch (choice) {
                case 1:
                    // Case 1 Search by name
                    System.out.print("\nEnter teacher's full name: ");
                    if (!searchByName(teachers, userInput.nextLine())) {
                        System.out.println("\nTeacher not found.\n");
                    }
                    break;

                case 2:
                    // Case 2 Search by room
                    System.out.print("\nEnter room number: ");
                    searchByRoom(teachers, userInput.nextLine());
                    break;

                case 3:
                    // Case 3 List by building
                    System.out.print("\nEnter building name: ");
                    listByBuilding(teachers, userInput.nextLine());
                    break;

                case 4:
                    // Case 4 Exit program
                    System.out.println("Thank you for using the directory!");
                    running = false;
                    break;

                default:
                    // Invalid input case
                    System.out.println("\nInvalid choice.\n");
            }
        }

        // Close scanner to prevent resource leak
        userInput.close();
    }
}
