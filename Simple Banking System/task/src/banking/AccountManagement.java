package banking;

import java.util.Scanner;

import static banking.DbControl.*;

public class AccountManagement {

    public static void addFunds(Scanner scanner, String accountNumber) {
        System.out.println("Enter income:");
        String income = scanner.nextLine();
        int balance = getBalance(accountNumber) + Integer.parseInt(income);
        updateBalance(balance, accountNumber);
        System.out.println("Income Added!");
    }

    public static void transferFunds(Scanner scanner, String accountNumber) {
        System.out.println("Enter card number");
        String transferCard = scanner.nextLine();

        if (checkLuhn(transferCard)) {
            AccountInfo transferAccount = findRecord(transferCard);
            if (transferAccount != null) {
                System.out.println("Enter how much money you want to transfer:");
                String transferAmount = scanner.nextLine();
                int tempAmount = Integer.parseInt(transferAmount);
                int fromAccountBlanace = getBalance(accountNumber);
                if (fromAccountBlanace >= tempAmount) {
                    //transfer from accountNumber
                    fromAccountBlanace -= tempAmount;
                    updateBalance(fromAccountBlanace, accountNumber);

                    //transfer to accountNumber
                    transferAccount.setBalance(transferAccount.getBalance() + tempAmount);
                    updateBalance(transferAccount.getBalance(), transferAccount.getAccountNumber());

                    System.out.println("Success!");
                } else {
                    System.out.println("Not enough money!");
                }

            } else {
                System.out.println("Such a card does not exist.");
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }
    }


    private static boolean checkLuhn(String accountNumber) {
        boolean validCard = false;
        int checkTotal = 0;
        for (int i = 0; i < accountNumber.length() - 1; i++) {
            int value = Character.getNumericValue(accountNumber.charAt(i));
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
            checkSum = 0;
        }
        String cardCheckValue = String.valueOf(accountNumber.charAt(15));
        if (checkSum ==  Integer.parseInt(cardCheckValue)) {
            validCard = true;
        }

        return validCard;
    }
}
