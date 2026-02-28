import java.util.ArrayList;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> expenses = new ArrayList<>();

        System.out.println("===== SMART EXPENSE TRACKER =====");
        System.out.println("enter the monthly income:");
        double income = scanner.nextDouble();

        System.out.print("Enter number of expenses you want to add: ");
        int count = scanner.nextInt();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter expense amount: ");
            double amount = scanner.nextDouble();
            expenses.add(amount);
        }

        double totalExpense = 0;

        for (double expense : expenses) {
            totalExpense += expense;
        }

        System.out.println("Total expense: " + totalExpense);

        double saving = income-totalExpense;
        System.out.println("the saving amount of a month is:"  + saving );

        double percent = saving/(income)*100;
        System.out.println("the percentage of saving is:" +percent);

        if(percent>40){
            System.out.println("best financial saving");
        }else if(percent<40){
            System.out.println("good saving");
        }else{
            System.out.println("improve your savings");
        }

        scanner.close();
    }
}