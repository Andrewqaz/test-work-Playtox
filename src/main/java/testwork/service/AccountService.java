package testwork.service;

import org.apache.log4j.Logger;
import testwork.domain.Account;
import testwork.exception.TransferException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountService {
    public static final Logger logger = Logger.getLogger(AccountService.class);

    public Account createRandomAccount() {
        Random random = new Random();
        int id = random.nextInt(3000);
        Account account = new Account(id + "", 10000);
        logger.info("create account: " + account.getId() + ", balance = " + account.getMoney());
        return account;
    }

    public List<Account> createListOfAccounts(int numberOfAccounts) {
        ArrayList<Account> accounts = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            accounts.add(createRandomAccount());
        }
        return accounts;
    }

    public void transferMoney(Account accFrom, Account accTo, int amount) throws TransferException {
        if (accFrom.getMoney() >= amount) {
            accFrom.setMoney(accFrom.getMoney() - amount);
            accTo.setMoney(accTo.getMoney() + amount);
            logger.info(accFrom.getId() + " -> " + accTo.getId() + " Сумма перевода: " + amount);
            logger.info(accFrom.getId() + " balance = " + accFrom.getMoney());
            logger.info(accTo.getId() + " balance = " + accTo.getMoney());
        } else {
            throw new TransferException("Сумма перевода: " + amount + "; " +
                    accFrom.getId() + "(balance" + accFrom.getMoney() + ")" +
                    " -> " + accTo.getId() + "(balance" + accTo.getMoney() +
                    ") " + "Недостаточно средств");
        }
    }
}
