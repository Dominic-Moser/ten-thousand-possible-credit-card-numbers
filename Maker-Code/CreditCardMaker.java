import java.io.*;
import java.util.*;

public class CreditCardMaker {
    /*
     * Card brand identifier
     * Options include:
     * AMERICAN
     * VISA
     * 
     * Additional configs found at:
     * 'https://baymard.com/checkout-usability/credit-card-patterns'
     * 
     */

    static int MAX_LENGTH = 16;
    static int[] STARTING_DIGITS = { 2 };
    static final String CARD_PROVIDER = "VISA";

    static int NUM_GENERATIONS = 10000;
    static String FILE_LOCATION = CARD_PROVIDER + ".txt";

    public static void main(String[] args) throws IOException {
        findGivenNumberOfCombinations(NUM_GENERATIONS, FILE_LOCATION);
    }

    /*
     * Brute forces a given amount of cards and writes them to a given file,
     * seperated by commas
     * ex. XXXX-XXXX-XXXX-XXXX, XXXX-XXXX-XXXX-XXXX
     */
    public static void findGivenNumberOfCombinations(long numCardsToGenerate, String fileLocation) throws IOException {
        assignCardDetails();
        ArrayList<String> validCards = new ArrayList<>();
        while (true) {
            genUniqueCard(validCards);
            System.out.println("Added a unique card: " + validCards.size());
            if (validCards.size() == numCardsToGenerate)
                break;
        }

        FileWriter fw = new FileWriter(fileLocation);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String s : validCards) {
            bw.write(s.substring(0, 4) + "-" + s.substring(4, 8) + "-" + s.substring(8, 12) + "-" + s.substring(12)
                    + ", ");
        }
        bw.close();

        System.out.println("Succesfully written to: " + fileLocation);
    }

    /*
     * Tries to generate a new unique card based off of a random funciton
     * Validates that it is not a duplicate than adds it to the provided arraylist
     * 
     */

    public static void genUniqueCard(ArrayList<String> validCards) {
        int startingDigits = STARTING_DIGITS[r(STARTING_DIGITS.length)];
        int digitsToGenerate = MAX_LENGTH - startingDigits;

        while (true) {
            String finalString = s(startingDigits);

            for (int i = 0; i < digitsToGenerate; i++) {
                finalString += s(r(10));
            }

            if (!validCards.contains(finalString))
            {
                validCards.add(finalString);
                System.out.println(finalString);
            }
                
            break;
        }

    }

    /*
     * Takes the given variables at the top of the function and assigns the correct
     * values to them
     * Can be easily modified with any information you'd like to add
     */

    public static void assignCardDetails() {
        switch (CARD_PROVIDER) {
            case "AMERICAN":
                MAX_LENGTH = 15;
                STARTING_DIGITS = new int[] { 34, 37 };
                break;
            case "VISA":
                MAX_LENGTH = 19;
                STARTING_DIGITS = new int[] { 4 };
                break;
            case "MASTER":
                MAX_LENGTH = 16;
                STARTING_DIGITS = new int[] { 51, 52, 53, 54, 55 };
                break;
            case "DISCOVER":
                MAX_LENGTH = 16;
                STARTING_DIGITS = new int[] { 644, 645, 647, 647, 648, 649, 65 };
                break;
            default:
                break;
        }
    }

    /*
     * Function to test to see if a given number is a valid card
     * 
     * Automatically replaces any missing zero digits with zeros,
     * padding the end.
     * 
     * Throws IllegalArgumentException if the provided number is greater than
     * MAX_LENGTH
     */

    public static boolean isCardValid(long cardNumber) {

        if (s(cardNumber).length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Provided long has too great of a length, maximum length is: " + MAX_LENGTH);
        }

        String formatted = String.format("%016d", cardNumber);

        String[] split = formatted.split("");

        int sum = 0;
        for (int i = 0; i < split.length; i++) {
            int temp = i(split[i]);
            if (i % 2 == 0) {
                sum += everySecond(temp);
            } else {
                sum += temp;
            }
        }
        if (sum % 10 == 0)
            return true;
        return false;
    }

    /*
     * Returns num*2 if digit is less than two characters long, and num*2-9 from
     * if it is.
     */

    public static int everySecond(int num) {
        num *= 2;
        if (s(num).length() == 2) {
            num -= 9;
        }
        return num;
    }

    /*
     * Helper Functions
     * s returns the string value of any given variable
     * i returns the int value of a string
     * r returns an int ranging from 0, to the inputted num
     */
    public static <T> String s(T i) {
        return String.valueOf(i);
    }

    public static int i(String s) {
        return Integer.parseInt(s);
    }

    public static int r(int s) {
        return (int) (Math.random() * s);
    }

    public static long l(String s) {
        return Long.parseLong(s);
    }

}
