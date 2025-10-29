package ja.test2;

import javax.swing.*;

public class manager extends employee{
    private double bouns ;

    public manager(String name, String address, String phone, String socialSN, double payRate) {
        super(name, address, phone, socialSN, payRate);
        this.bouns = 0;
    }

    public double getBouns() {
        return bouns;
    }

    public void setBouns(double bouns) {
        this.bouns = bouns;
    }

    @Override
    public double pay(){
        double a = bouns + getPayRate();
        bouns = 0;
        return a;
    }
    public void awardBouns(double bouns){
        this.bouns += bouns;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "社会安全号：" + getSocialSN() + "\n" +
                "工资：$" + String.format("%.2f", pay());
    }
}
