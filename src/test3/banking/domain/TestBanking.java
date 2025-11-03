package test3.banking.domain;

import test3.banking.report.CustomReport;

import java.util.ArrayList;
import java.util.List;

public class TestBanking {
    public static void main(String[] args) {
        SavingsAccount JS = new SavingsAccount(500.00,0.05);
        SavingsAccount TS = new SavingsAccount(1500.00,0.05);
        SavingsAccount MS = new SavingsAccount(150.00,0.05);

        CheckingAccount JSc = new CheckingAccount(200.00,true,400.00);
        CheckingAccount OBc = new CheckingAccount(200.00);
        CheckingAccount TSc = new CheckingAccount(300.00);

        Customer JaneS = new Customer("Jane","Simms");
        Customer OwenB = new Customer("Owen","Bryant");
        Customer TimS = new Customer("Tim","Soley");
        Customer MariaS = new Customer("Maria","Soley");

        JaneS.addAccount(JS);
        JaneS.addAccount(JSc);
        OwenB.addAccount(OBc);
        TimS.addAccount(TS);
        TimS.addAccount(TSc);
        MariaS.addAccount(MS);
        MariaS.addAccount(TSc);



        Bank bank = new Bank();
        bank.addCustomer(JaneS);
        bank.addCustomer(OwenB);
        bank.addCustomer(TimS);
        bank.addCustomer(MariaS);

        CustomReport report = new CustomReport();
        report.generateReport(bank);

    }
}
