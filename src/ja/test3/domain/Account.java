package ja.test3.domain;

public abstract class Account {
    private double balance; //余额

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    abstract void withdraw(double amount) throws OverdraftExpection;
}
