import java.util.Scanner;
import java.lang.Math;

public class BhaskaraCalculator {
    // Class method, can we change the name or it has to be main?
    public static void main(String args[]) {
      double delta, x1, x2, a, b, c;

      // Instantiating the Scanner object, which will be called to read the variable from the user
      Scanner scanObj = new Scanner(System.in);
      System.out.println("Enter the coefficient a: ");

      // Read user input and store it to a variable
      // Double input on the console HAS TO BE using comma, otherwise an exception will be thrown: 2,0 and not 2.0
      //try {
      a = scanObj.nextDouble();
      System.out.println("Enter the coefficient b: ");
      b = scanObj.nextDouble();
      System.out.println("Enter the coefficient c: ");
      c = scanObj.nextDouble();
      //}

      /*catch(java.util.InputMismatchException e) {
        System.out.println("Use comma instead of points or integers! Exception caught: " + e);
      }
      */

      System.out.printf("\nQuadratic equation is: %f*x^2 %fx + %f = 0\n", a, b, c);

      delta = Math.pow(b,2) - 4*a*c;

      if (delta >= 0) {
        System.out.println("Delta: " + delta);
        x1 = (-b + Math.sqrt(delta))/(2*a);
        x2 = (-b - Math.sqrt(delta))/(2*a);
        System.out.printf("Roots are: %f and %f \n", x1, x2);
      }

      else {
        System.out.println("\nThe given coefficients results on imaginary roots. Exiting program...");
        System.exit(0);
      }

    }
}