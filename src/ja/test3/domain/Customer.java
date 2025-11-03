package ja.test3.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private List<Account> accounts;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public Iterator getAccounts() {
        return accounts.iterator();
    }

    public Account getAccount(int index) {
        return accounts.get(index);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public int getNumOfAccounts() {
        return accounts.size();
    }


}
