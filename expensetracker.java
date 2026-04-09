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
            System.out.println("3. View Insights");
            System.out.println("4. Exit");

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
                    totalSpending();
                    categorySpending();
                    aiInsights();
                    break;

                case 4:
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

        System.out.print("Enter Category: ");
        String category = sc.nextLine();

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        Expense e = new Expense(name, amount, category, date);
        expenses.add(e);
        saveExpenses();

        System.out.println("✅ Expense Added!");
    }

    // 🔹 View Expenses
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

    // 🔹 Save to File
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

    // 🔹 Load from File
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
}