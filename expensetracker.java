import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

// Expense Class
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
            System.out.println("3. Delete Expense");
            System.out.println("4. Edit Expense");
            System.out.println("5. View Insights");
            System.out.println("6. Exit");

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
                    deleteExpense();
                    break;

                case 4:
                    editExpense();
                    break;

                case 5:
                    totalSpending();
                    categorySpending();
                    aiInsights();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 🔹 Add Expense
    static void addExpense() {

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        // ✅ Validation
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        expenses.add(new Expense(name, amount, category, date));
        saveExpenses();

        System.out.println("✅ Expense Added!");
    }

    // 🔹 View Expenses (with index)
    static void viewExpenses() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses yet.");
            return;
        }

        System.out.println("\n--- Expense List ---");

        for (int i = 0; i < expenses.size(); i++) {
            System.out.print(i + " -> ");
            expenses.get(i).display();
        }
    }

    // 🔹 Delete Expense
    static void deleteExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses to delete.");
            return;
        }

        viewExpenses();

        System.out.print("Enter index to delete: ");
        int index = sc.nextInt();

        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            saveExpenses();
            System.out.println("✅ Expense deleted!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    // 🔹 Edit Expense
    static void editExpense() {

        if (expenses.isEmpty()) {
            System.out.println("No expenses to edit.");
            return;
        }

        viewExpenses();

        System.out.print("Enter index to edit: ");
        int index = sc.nextInt();
        sc.nextLine();

        if (index >= 0 && index < expenses.size()) {

            Expense e = expenses.get(index);

            System.out.print("Enter new name: ");
            e.name = sc.nextLine();

            System.out.print("Enter new amount: ");
            e.amount = sc.nextDouble();
            sc.nextLine();

            System.out.print("Enter new category: ");
            e.category = sc.nextLine();

            System.out.print("Enter new date: ");
            e.date = sc.nextLine();

            saveExpenses();

            System.out.println("✅ Expense updated!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    // 🔹 Total Spending
    static void totalSpending() {

        double total = 0;

        for (Expense e : expenses) {
            total += e.amount;
        }

        System.out.println("\nTotal Spending: ₹" + total);
    }

    // 🔹 Category Spending
    static void categorySpending() {

        HashMap<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(e.category,
                    map.getOrDefault(e.category, 0.0) + e.amount);
        }

        System.out.println("\n--- Category Spending ---");

        for (String key : map.keySet()) {
            System.out.println(key + " : ₹" + map.get(key));
        }
    }

    // 🔹 AI Insights
    static void aiInsights() {

        HashMap<String, Double> map = new HashMap<>();

        for (Expense e : expenses) {
            map.put(e.category,
                    map.getOrDefault(e.category, 0.0) + e.amount);
        }

        String maxCategory = "";
        double maxAmount = 0;

        for (String key : map.keySet()) {
            if (map.get(key) > maxAmount) {
                maxAmount = map.get(key);
                maxCategory = key;
            }
        }

        System.out.println("\n--- AI Insight ---");
        System.out.println("You spend most on: " + maxCategory);

        if (maxAmount > 3000) {
            System.out.println("⚠ Try reducing spending on " + maxCategory);
        } else {
            System.out.println("✅ Your spending looks balanced");
        }
    }

    // 🔹 Save
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

    // 🔹 Load
    static void loadExpenses() {

        try {
            File file = new File("expenses.txt");

            if (!file.exists()) return;

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                expenses.add(new Expense(
                        data[0],
                        Double.parseDouble(data[1]),
                        data[2],
                        data[3]
                ));
            }

            fileScanner.close();

        } catch (Exception e) {
            System.out.println("Error loading file");
        }
    }
}