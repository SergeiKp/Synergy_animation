package newPackage;
import java.util.Scanner;

public class NewCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String input = scanner.nextLine();
        String[] parts = input.trim().split(" ");
        if (parts.length != 3) {
            System.out.println("Ошибка: неверный формат ввода!");
            return;
        }

        double num1 = Double.parseDouble(parts[0]);
        char operation = parts[1].charAt(0);
        double num2 = Double.parseDouble(parts[2]);
        double result = switch (operation) {
            case '+' -> num1 + num2;
            case '-' -> num1 - num2;
            case '*' -> num1 * num2;
            case '/' -> (num2 == 0) ? Double.NaN : num1 / num2;
            default -> {
                System.out.println("Ошибка: неизвестная операция!");
                yield Double.NaN;
            }
        };
        if (Double.isNaN(result)) {
            System.out.println("Ошибка в вычислении.");
        } else {
            System.out.println("Результат: " + result);
        }
        scanner.close();
    }
}

