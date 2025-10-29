package test2;

import java.util.ArrayList;

public class Manager extends Employee {
    private double bonus;

    public Manager(ArrayList<StaffMember> staffMembers, String name, String socialSN, double payrate, double bonus) {
        super(staffMembers, name, socialSN, payrate);
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void awardBonus(double award) {
        this.bonus += award;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "bonus=" + bonus +
                '}';
    }
}
