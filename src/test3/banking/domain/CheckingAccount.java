package test3.banking.domain;

public class CheckingAccount extends Account {
    private boolean canOverdraft;
    private double maxOverdraft;

    public CheckingAccount(boolean canOverdraft, double maxOverdraft,double balance) {
        super(balance);
        this.canOverdraft = canOverdraft;
        this.maxOverdraft = maxOverdraft;
    }

    public boolean isCanOverdraft() {
        return canOverdraft;
    }

    public void setCanOverdraft(boolean canOverdraft) {
        this.canOverdraft = canOverdraft;
    }

    public double getMaxOverdraft() {
        return maxOverdraft;
    }

    public void setMaxOverdraft(double maxOverdraft) {
        this.maxOverdraft = maxOverdraft;
    }

    public void withdraw(double amount) throws OverdraftExpection {
    }


}
