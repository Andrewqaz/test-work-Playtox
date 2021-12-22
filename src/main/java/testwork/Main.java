package testwork;

import org.apache.log4j.Logger;
import testwork.controller.ImitationOfWork;
import testwork.domain.Account;
import testwork.service.AccountService;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);


    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream inputStream = ImitationOfWork.class.getResourceAsStream("/property.properties")) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("файл свойств не найден", e);
        } catch (IOException exception) {
            logger.error("ошибка доступа к файлу свойств", exception);
        }

        int numberOfAccounts = Integer.parseInt(properties.getProperty("accounts"));
        System.out.println(numberOfAccounts);
        AccountService accountService = new AccountService();
        List<Account> accounts = accountService.createListOfAccounts(numberOfAccounts);
        ImitationOfWork work = new ImitationOfWork(accounts);
        work.working();
        accounts.forEach(account -> System.out.println(account.getId() + " balance: " + account.getMoney()));
    }
}

