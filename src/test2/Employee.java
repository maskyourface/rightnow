package test2;

import java.util.ArrayList;

public class Employee extends StaffMember {
    private String socialSN;
    private double payrate;

    public Employee(ArrayList<StaffMember> staffMembers, String name, String socialSN, double payrate) {
        super(staffMembers, name);
        this.socialSN = socialSN;
        this.payrate = payrate;
    }

    public String getSocialSN() {
        return socialSN;
    }

    public void setSocialSN(String socialSN) {
        this.socialSN = socialSN;
    }

    public double getPayrate() {
        return payrate;
    }

    public void setPayrate(double payrate) {
        this.payrate = payrate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "socialSN='" + socialSN + '\'' +
                ", payrate=" + payrate +
                '}';
    }

    @Override
    double pay() {
        return
    }


}
