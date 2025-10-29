package ja.test2;

public class volunteer extends staffmember{


    public volunteer(String name, String address, String phone) {
        super(name, address, phone);
    }

    @Override
    public double pay(){
        return 0;
    }

    @Override
    public String ToString() {
        return super.ToString() + "\n" + "多谢";
    }


}
