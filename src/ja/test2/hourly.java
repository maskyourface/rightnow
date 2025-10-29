package ja.test2;

public class hourly extends employee{
    private int hoursWorked;

    public hourly(String name, String address, String phone, String socialSN, double payRate) {
        super(name, address, phone, socialSN, payRate);
        this.hoursWorked = 0;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public void addHours(int hours){
        hoursWorked += hours;
    }
    @Override
    public double pay(){
        return hoursWorked * super.getPayRate();
    }
    @Override
    public String ToString(){

        return super.ToString() + "\n" +

                "工作小时数：" + hoursWorked + "\n";
    }
}
