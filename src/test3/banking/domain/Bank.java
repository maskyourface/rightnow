package test3.banking.domain;

import java.util.Iterator;
import java.util.List;

public class Bank extends TestBanking{
    private List<Customer> customers;

    public Bank(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public int getNumOfCustomer() {
        return customers.size();
    }

    public Iterator<Customer> getCustomer() {
        return customers.iterator();
    }
}
