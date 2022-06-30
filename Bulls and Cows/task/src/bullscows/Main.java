package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        String lengthString = scanner.nextLine();

        try {
            int length = Integer.parseInt(lengthString);
        } catch (Exception e) {
            System.out.println("Error: " + "\"" + lengthString + "\" isn't a valid number.");
            System.exit(0);
        }

        int length = Integer.parseInt(lengthString);

        if (length > 36) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique symbols.");
            System.exit(0);
        } else if (length < 1) {
            System.out.println("Error: you have to enter a positive number for the secret code's length.");
            System.exit(0);
        }

        System.out.println("Input the number of possible symbols in the code:");

        int availableCharacters = scanner.nextInt();

        if (availableCharacters < length) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + availableCharacters + " unique symbols.");
            System.exit(0);
        } else if (availableCharacters > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }

        System.out.print("The secret code is prepared: ");
        for (int i = 0; i < length; i++) {
            System.out.print("*");
        }
        if (availableCharacters <= 10) {
            System.out.print(" (" + (char)48 + "-" + (char)(48 + (availableCharacters - 1)) + ").");
        } else {
            System.out.println(" (0-9, a-" + (char)(97 + (availableCharacters - 11)) + ").");
        }

        System.out.println("Okay, let's start a game!");

        char[] secretCode = generateSecretCode(length, availableCharacters);
        String code = parseSecretCode(secretCode);




        int bulls = 0;
        int cows = 0;
        int turn = 1;

        while (bulls != secretCode.length) {
            System.out.println("Turn " + turn + ":");
            bulls = 0;
            cows = 0;
            String guess = scanner.next();
            if (guess.length() != code.length()) {
                System.out.println("Error: wrong length of your guess. You have to start over.");
                System.exit(0);
            }
            char[] guessArray = new char[length];

            for (int n = 0; n < length; n++) {
                guessArray[n] = guess.charAt(n);
            }

            for (int i = 0; i < length; i++) {
                if (guessArray[i] == secretCode[i]) {
                    bulls++;
                } else {
                    for (int x = 0; x < length; x++) {
                        if (guessArray[i] == secretCode[x]) {
                            cows++;
                        }
                    }
                }
            }
            System.out.println("Grade: " + evaluate(bulls, cows));
            turn++;
        }

        System.out.println("Congratulations! You guessed the secret code.");

    }


    public static String evaluate(int bulls, int cows) {
        if (bulls == 0 && cows == 0) {
            return "None.";
        } else if (bulls > 0 && cows == 0) {
            return bulls + " bull(s).";
        } else if (bulls == 0 && cows > 0) {
            return cows + " cow(s).";
        } else {
            return bulls + " bull(s) and " + cows + " cow(s).";
        }
    }

    public static char[] generateSecretCode(int length, int availableCharacters) {
        Random random = new Random();
        char[] secretCode = new char[length];
        int digit;
        if (availableCharacters <= 10) {
            for (int i = 0; i < secretCode.length; i++) {

                boolean isUsed;
                do {
                    isUsed = false;
                    digit = random.nextInt((48 + availableCharacters) - 48) + 48;
                    for (int n = 0; n < i; n++) {
                        if (secretCode[n] == (char)digit) {
                            isUsed = true;
                            break;
                        }
                    }
                } while (isUsed);
                secretCode[i] = (char)digit;
            }
        } else {
            for (int i = 0; i < secretCode.length; i++) {

                boolean isUsed;
                do {
                    isUsed = false;
                    int numberOrChar = random.nextInt(availableCharacters);
                    if (numberOrChar < 11) {
                        digit = random.nextInt((57 - 48)) + 48;
                    } else {
                        digit = random.nextInt((97 + (availableCharacters - 11)) - 97) + 97;
                    }
                    for (int n = 0; n < i; n++) {
                        if (secretCode[n] == (char)digit) {
                            isUsed = true;
                            break;
                        }
                    }
                } while (isUsed);
                secretCode[i] = (char)digit;
            }
        }
        return secretCode;
    }

    public static String parseSecretCode(char[] secretCode) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : secretCode) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
