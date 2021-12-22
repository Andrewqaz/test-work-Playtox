package testwork.controller;

import org.apache.log4j.Logger;
import testwork.Main;
import testwork.domain.Account;
import testwork.exception.TransferException;
import testwork.service.AccountService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class ImitationOfWork {
    private final static Logger logger = Logger.getLogger(ImitationOfWork.class);
    private List<Account> accounts;
    private AccountService accountService;

    private static int numberOfOperations;
    static {
        try (InputStream inputStream = ImitationOfWork.class.getResourceAsStream("/property.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            numberOfOperations = Integer.parseInt(properties.getProperty("numberOfOperations"));
            logger.trace("количество опреаций = " + numberOfOperations);
        } catch (FileNotFoundException e) {
            logger.error("файл свойств не найден, количество операций установлено 30", e);
            numberOfOperations = 30;
        } catch (IOException exception) {
            logger.error("ошибка доступа к файлу свойств, количество операций установлено 30", exception);
            numberOfOperations = 30;
        }
    }

    public ImitationOfWork(List<Account> accounts) {
        this.accounts = accounts;
        accountService = new AccountService();
    }

    public void working(){
        Random random = new Random();
        while (numberOfOperations>0){
            Account accFrom = accounts.get(random.nextInt(accounts.size()));
            Account accTo = accounts.get(random.nextInt(accounts.size()));
            int amount = random.nextInt(10000);
            try {
                accountService.transferMoney(accFrom, accTo, amount);
                numberOfOperations--;
            } catch (TransferException e) {
                logger.warn(e);
            }
        }
    }
}
