package org.primes;

import java.util.Scanner;

import static org.primes.PrimeNumbers.isPrime;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        var UI = new UserInterface(scanner);
        UI.run();

    }

}
