package testwork.service;

import org.apache.log4j.Logger;
import testwork.domain.Account;
import testwork.exception.TransferException;

public class AccountService {
    public static final Logger logger = Logger.getLogger(AccountService.class);

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
