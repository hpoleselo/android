public class Calculator {
    // Method returns a double, using the access modifier as public in order to this method to be available
    // Static because this method can be called even though the object hasn't been constructed yet (i.e: without instantiating )
    public static double sum(double x, double y) {
        return x + y;
    }

    public static double minus(double x, double y) {
        return x - y;
    }

    public static double multiplication (double x, double y) {
        return x*y;
    }

    public static double division (double x, double y) {
        // Add exception
        return x/y;
    }


    // Main method has always to be present, it compiles but when running it will complain
    public static void main2(String[] args) {
        double z;
        z = sum(10,20);
        System.out.println("Result of sum is: " + z);
        z = minus(10,20);
        System.out.println("Result of minus is: " + z);
        z = multiplication(10, 20);
        System.out.println("Result of multiplication is: " + z);
        z = division(10, 20);
        System.out.println("Result of division is: " + z);
    }


}