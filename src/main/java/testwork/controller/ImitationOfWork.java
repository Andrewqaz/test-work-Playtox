package testwork.controller;

import org.apache.log4j.Logger;
import testwork.domain.Account;
import testwork.exception.TransferException;
import testwork.service.AccountService;
import testwork.utils.Util;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class ImitationOfWork implements Runnable {
    private final static Logger logger = Logger.getLogger(ImitationOfWork.class);
    private List<Account> accounts;
    private AccountService accountService;

    private static AtomicInteger numberOfOperations;

    public static AtomicInteger getNumberOfOperations() {
        return numberOfOperations;
    }

    static {
        numberOfOperations = new AtomicInteger();
        Properties properties = Util.getProperties("/property.properties");
        numberOfOperations.set(Integer.parseInt(properties.getProperty("numberOfOperations")));
        logger.trace("количество опреаций = " + numberOfOperations);
    }

    public ImitationOfWork(List<Account> accounts) {
        this.accounts = accounts;
        accountService = new AccountService();
    }

    public void working() {
        while (numberOfOperations.getAndDecrement() > 0) {
            Account accFrom;
            Account accTo;
            try {
                accFrom = accounts.remove(Util.getRandomInt(accounts.size()+1)-1);
            } catch (ArrayIndexOutOfBoundsException e) {
                numberOfOperations.getAndIncrement();
                continue;
            }
            try {
                accTo = accounts.remove(Util.getRandomInt(accounts.size()+1)-1);
            } catch (ArrayIndexOutOfBoundsException  e) {
                accounts.add(accFrom);
                numberOfOperations.getAndIncrement();
                continue;
            }
            int amount = Util.getRandomInt(10000);
            try {
                accountService.transferMoney(accFrom, accTo, amount);
            } catch (TransferException e) {
                logger.warn(e);
                numberOfOperations.getAndIncrement();
            }
            accounts.add(accFrom);
            accounts.add(accTo);
            try {
//                System.out.println(Thread.currentThread().getName()+" засыпает");
                Thread.sleep(Util.getRandomInt(1000)+1000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    @Override
    public void run() {
        working();
    }
}
