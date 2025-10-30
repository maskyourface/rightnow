package test3.banking.report;

import test3.banking.domain.*;
import java.util.Iterator;
import java.util.List;

public class CustomReport extends TestBanking {
    public CustomReport() {
    }

    public void generateReport(Bank bank) {
        System.out.println("CUSTOMERS REPORT");
        System.out.println("===================================");

        Iterator<Customer> customers = bank.getCustomers();

        while( customers.hasNext() ) {
            Customer customer = customers.next();

            System.out.println("储户姓名：" + customer.getName());

            Iterator<Account> accountIter = customer.getAccounts();

            int accountNum = 1;
            boolean hasAccounts = false;

            while(accountIter.hasNext()) {
                hasAccounts = true;
                Account account = accountIter.next();

                if(account instanceof SavingsAccount) {
                    SavingsAccount savingsAccount = (SavingsAccount) account;
                    System.out.println("Savings Account: 当前余额是￥" + savingsAccount.getBalance());
                }

                if(account instanceof CheckingAccount) {
                    CheckingAccount checkingAccount = (CheckingAccount) account;
                    System.out.println("Checking Account: 当前余额是￥" + checkingAccount.getBalance());
                }

                accountNum++;
            }
        }
    }
}
