package ja.test2;

public class staff_test {
    private static staffmember[] staffList;

    public  staff_test() {
        staffList = new staffmember[6];

        // 初始化6名员工
        staffList[0] = new manager("Sam", "123 Main Line", "555-0469",
                "123-45-6789", 2423.07);
        ((manager)staffList[0]).awardBouns(500.00); // 添加红利

        staffList[1] = new employee("Peter", "456 Off Line", "555-0101",
                "987-65-4321", 1246.15);

        staffList[2] = new employee("Mary", "789 Off Rocker", "555-0690",
                "010-20-3040", 1169.23);

        staffList[3] = new hourly("Cliff", "678 Fifth Ave.", "555-0000",
                "958-47-3625", 10.55);
        ((hourly)staffList[3]).addHours(40); // 设置工作小时数

        staffList[4] = new volunteer("Al", "987 Suds Ave.", "555-8374");

        staffList[5] = new volunteer("Gus", "321 Off Line", "555-7282");
    }
    public static void payDetail() {
        for (staffmember staffmember : staffList) {
            System.out.println(staffmember.ToString());
            System.out.println("----------------------------------------");
        }
    }
    public static void main(String[] args) {
        staff_test staff = new staff_test();
        staff.payDetail();
    }
}
