package edu.ccrm.util;

public class RecursiveUtil {

    public static int factorial(int n) {
        if (n <= 1) return 1;
        else return n * factorial(n - 1);
    }

    public static int fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
