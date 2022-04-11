package banking;

import java.util.Random;
import java.util.Scanner;

import static banking.AccountManagement.*;
import static banking.DbControl.*;

public class Main {

    static String filename;

    public static void main(String[] args) {
        filename = args[1];
        createDb(filename);
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        do {
            mainMenu();
            String userInput = scanner.nextLine();
            switch (Integer.parseInt(userInput)) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    accountLogin(scanner);
                    break;
                case 0:
                    System.out.println("Bye!");
                    done = true;
                    break;
                default:
                    System.out.println("invalid input please try again");
            }
        } while (!done);
    }

    private static void mainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. log into account");
        System.out.println("0. Exit");
    }

    private static void accountMenu() {
        System.out.println("1. Balance");
        System.out.println("2. Add Income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close Account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    private static void createAccount() {
        String tempSubcard = String.format("%09d", new Random().nextInt((int) 1E+9));
        String tempCardNum = "400000" + tempSubcard;
        String cardNum = createLuan(tempCardNum);

        String pin = String.format("%04d", new Random().nextInt(10000));
        System.out.println("Your card has been created");
        System.out.println("Your card number: \n" + cardNum);
        System.out.println("Your pin is:\n" + pin);

        AccountInfo newAccount = new AccountInfo(cardNum, pin, 0);
        insertRecord(newAccount);
    }

    private static String createLuan(String tempCardNum) {

        int checkTotal = 0;
        for (int i = 0; i < tempCardNum.length(); i++) {
            int value = Character.getNumericValue(tempCardNum.charAt(i));
            if (i % 2 == 0) {
                value = value * 2;
                if (value > 9) {
                    value = value - 9;
                }
            }
            checkTotal += value;
        }

        int checkSum = 10 - (checkTotal % 10);
        if (checkSum == 10) {
            tempCardNum = tempCardNum + 0;
        } else {
            tempCardNum = tempCardNum + checkSum;
        }
        return tempCardNum;

    }

    private static void accountLogin(Scanner scanner1) {
        System.out.println("Enter your card number:");
        String cardNumber = scanner1.nextLine();
        System.out.println("Enter your pin");
        String pin = scanner1.nextLine();

        AccountInfo accountInfo = findRecord(cardNumber);

        if (accountInfo != null && accountInfo.getPin().equals(pin)) {
            System.out.println("You have successfully logged in!");
            accountOptions(scanner1, accountInfo.getAccountNumber());
        } else {
            System.out.println("Wrong card number or pin");
        }
    }

    private static void accountOptions(Scanner scanner2, String accountNumber) {
        boolean accountDone = false;

        do {
            accountMenu();
            String accountMenu = scanner2.nextLine();
            switch (Integer.parseInt(accountMenu)) {
                case 1:
                    System.out.println("Balance: " + getBalance(accountNumber));
                    break;
                case 2:
                    addFunds(scanner2, accountNumber);
                    break;
                case 3:
                    transferFunds(scanner2, accountNumber);
                    break;
                case 4:
                    deleteRecord(accountNumber);
                    System.out.println("The account has been closed!");
                    accountDone = true;
                    break;
                case 5:
                    System.out.println("You have successfully logged out!");
                    accountDone = true;
                    break;
                case 0:
                    accountDone = true;
                    System.exit(0);
                default:
                    System.out.println("invalid Input try again");
            }
        } while (!accountDone);
    }


}