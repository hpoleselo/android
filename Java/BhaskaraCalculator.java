import java.util.Scanner;

public class BhaskaraCalculator {
    // Class method, can we change the name or it has to be main?
    public static void main(String args[]) {
      // Instantiating the Scanner object, which will be called to read the variable from the user
      Scanner scanObj = new Scanner(System.in);
      System.out.println("Enter the coefficient a: ");
      // Read user input and store it to a variable
      int a = scanObj.nextInt();
      System.out.println("Enter the coefficient b: ");
      int b = scanObj.nextInt();
      System.out.println("Enter the coefficient c: ");
      int c = scanObj.nextInt();
      System.out.printf("Coefficients: a = %d, b = %d and c = %d \n", a, b, c);
      
    }
}