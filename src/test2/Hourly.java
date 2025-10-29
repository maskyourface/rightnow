package test2;

import java.util.ArrayList;

public class Hourly extends Employee {
    private int hourWorked;

    public Hourly(ArrayList<StaffMember> staffMembers, String name, String socialSN, double payrate, int hourWorked) {
        super(staffMembers, name, socialSN, payrate);
        this.hourWorked = hourWorked;
    }

    public int getHourWorked() {
        return hourWorked;
    }

    public void setHourWorked(int hourWorked) {
        this.hourWorked = hourWorked;
    }

    @Override
    public String toString() {
        return "Hourly{" +
                "hourWorked=" + hourWorked +
                '}';
    }

    public void addHours(int hours) {
        this.hourWorked += hours;
    }
}
