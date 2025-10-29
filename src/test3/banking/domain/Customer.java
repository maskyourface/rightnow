package test3.banking.domain;

import java.util.Iterator;
import java.util.List;
import test3.banking.domain.Account;

public class Customer {
    private String firstName;
    private String lastName;
    private List<Account> accounts;

    public Customer(String firstName, List<Account> accounts, String lastName) {
        this.firstName = firstName;
        this.accounts = accounts;
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Iterator getAccounts() {
        return accounts.iterator();
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public int getNumOfAccounts() {
        return accounts.size();
    }


}
