package ja.test3.domain;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(double balance, double interestRate) {
        super(balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) throws OverdraftExpection {
        if (amount > getBalance()) {
            throw new OverdraftExpection("out of money");
        }
        setBalance(getBalance() - amount);
    }
}
