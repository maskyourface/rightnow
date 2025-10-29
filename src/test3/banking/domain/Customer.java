package test3.banking.domain;

import java.util.List;
import test3.banking.domain.Account;

public class Customer {
    private String firstName;
    private String lastName;
    private List<Account> Account;

    public Customer(List<Account> account, String lastName, String firstName) {
        Account = account;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getName() {
        return firstName + lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Account> getAccount() {
        return Account;
    }

    public void setAccount(List<Account> account) {
        Account = account;
    }




}
