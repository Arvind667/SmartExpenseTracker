 import java.io.File;
 import java.io.FileWriter;
 import java.io.PrintWriter;
 import java.util.*;

// Expense Class (OOP)
class Expense {

    String name;
    double amount;
    String category;
    String date;

    public Expense(String name, double amount, String category, String date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
          public String toFileString() {
           return name + "," + amount + "," + category + "," + date;
       }

    public void display() {
        System.out.println(name + " | ₹" + amount + " | " + category + " | " + date);
    }
}

// Main Class
public class expensetracker {

    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadExpenses();
        while (true) {

            System.out.println("\nSMART EXPENSE TRACKER");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    viewExpenses();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
        
    }
     static void saveExpenses() {

    try {
        PrintWriter writer = new PrintWriter(new FileWriter("expenses.txt"));

        for (Expense e : expenses) {
            writer.println(e.toFileString());
        }

        writer.close();

    } catch (Exception e) {
        System.out.println("Error saving file");
    }
 }
 static void loadExpenses() {

    try {
        File file = new File("expenses.txt");

        if (!file.exists()) return;

        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {

            String line = fileScanner.nextLine();
            String[] data = line.split(",");

            String name = data[0];
            double amount = Double.parseDouble(data[1]);
            String category = data[2];
            String date = data[3];

            expenses.add(new Expense(name, amount, category, date));
        }

        fileScanner.close();

    } catch (Exception e) {
        System.out.println("Error loading file");
    }
}

    static void addExpense() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        Expense e = new Expense(name, amount, category, date);
        expenses.add(e);
        saveExpenses();

        System.out.println("✅ Expense Added!");
    }
          

    static void viewExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }

        System.out.println("\n--- Expense List ---");

        for (Expense e : expenses) {
            e.display();
        }
    }
}
    

