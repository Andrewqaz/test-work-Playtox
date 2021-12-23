package testwork;

import testwork.controller.ImitationOfWork;
import testwork.domain.Account;
import testwork.utils.Util;

import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = Util.getProperties("/property.properties");
        int numberOfAccounts = Integer.parseInt(properties.getProperty("accounts"));
        System.out.println(numberOfAccounts);
        List<Account> accounts = Util.createListOfAccounts(numberOfAccounts);

        int sumBefore = 0;
        for (Account a : accounts) {
            sumBefore = sumBefore+a.getMoney();
        }

        int amountThreads = Integer.parseInt(properties.getProperty("threads"));
        for (int i = 0; i < amountThreads; i++) {
            Thread thread = new Thread(new ImitationOfWork(accounts));
            thread.start();
        }

        while (ImitationOfWork.getNumberOfOperations().get()>0){
        }

        accounts.forEach(account -> System.out.println(account.getId() + " balance: " + account.getMoney()));

        int sumAfter = 0;
        for (Account a : accounts) {
            sumAfter = sumAfter+a.getMoney();
        }
        System.out.println("sumBefore = "+sumBefore);
        System.out.println("sumAfter = "+sumAfter);
    }
}

