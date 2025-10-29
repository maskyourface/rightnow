package ja.test2;

public class employee extends staffmember{
    private String socialSN;
    private double payRate;

    public employee(String name, String address, String phone,String socialSN, double payRate){
        super(name,address,phone);
        this.socialSN = socialSN;
        this.payRate = payRate;
    }

    public void setSocialSN(String socialSN) {
        this.socialSN = socialSN;
    }

    public void setPayRate(double payRate) {
        this.payRate = payRate;
    }

    public double getPayRate(){
        return payRate;
    }
    public String getSocialSN(){
        return socialSN;
    }
    @Override
    public double pay() {

        return payRate;
    }

    @Override
    public String ToString() {
        return super.ToString() + "\n" +
                "社会安全号：" + socialSN + "\n" +
                "工资：$" + String.format("%.2f", pay());
    }


}
