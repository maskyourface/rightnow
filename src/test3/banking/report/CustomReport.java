package test3.banking.report;

import test3.banking.domain.Bank;
import test3.banking.domain.Customer;
import test3.banking.domain.TestBanking;

public class CustomReport extends TestBanking {
    public CustomReport() {
    }

    public void generateReport(Bank bank) {
        System.out.println("CUSTOMERS REPORT");

        for (int i=0;i<bank.getCustomers().size();i++) {
            Customer customer = bank.getCustomers().get(i);
            String name = customer.getName();

        }
    }
}
