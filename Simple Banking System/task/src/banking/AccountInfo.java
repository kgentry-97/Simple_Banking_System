package banking;

public class AccountInfo {
    private String accountNumber;
    private String pin;
    private int balance;

    public AccountInfo(String accountNumber, String pin, int balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public String getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public AccountInfo setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountInfo setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountInfo setPin(String pin) {
        this.pin = pin;
        return this;
    }
}
