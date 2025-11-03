package ja.test3.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bank extends TestBanking{
    private List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<>();
    }

    public Bank(List<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public Customer getCustomer(int index) {
        return customers.get(index);
    }

    public int getNumOfCustomers() {
        return customers.size();
    }

    public Iterator<Customer> getCustomers() {
        return customers.iterator();
    }
}
