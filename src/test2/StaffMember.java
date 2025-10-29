package test2;

import java.util.ArrayList;

abstract class StaffMember extends Staff {
    private String name;
    private String address;
    private String phone;

    public StaffMember(ArrayList<StaffMember> staffMembers, String name) {
        super(staffMembers);
        this.name = name;
    }

    abstract double pay();
}
