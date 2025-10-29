package test2;

import java.util.ArrayList;

public class Volunteer extends StaffMember {
    public Volunteer(ArrayList<StaffMember> staffMembers, String name) {
        super(staffMembers, name);
    }

    @Override
    double pay() {
        return 0;
    }
}
