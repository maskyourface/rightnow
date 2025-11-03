package ja.test3.domain;

public class CheckingAccount extends Account {
    private boolean canOverdraft;
    private double maxOverdraft;

    public CheckingAccount(double maxOverdraft,boolean canOverdraft,double balance) {
        super(balance);
        this.canOverdraft = canOverdraft;
        this.maxOverdraft = maxOverdraft;
    }
    public CheckingAccount(double balance) {
        super(balance);
        this.canOverdraft = false;
        this.maxOverdraft = 0.00;
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
        double overdraft =amount - getBalance();
        if (overdraft <= 0) {
            setBalance(getBalance() - amount);
            return;
        }

        if(!canOverdraft) {
            throw new OverdraftExpection("now allow overdraft");
        }

        if(overdraft > maxOverdraft) {
            throw new OverdraftExpection("out of limit");
        } else {
            maxOverdraft -= overdraft;
            setBalance(getBalance() - amount);
        }
    }


}
