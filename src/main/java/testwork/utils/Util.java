package testwork.utils;

import org.apache.log4j.Logger;
import testwork.domain.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Util {
    public static final Logger logger = Logger.getLogger(Util.class);
    private static final Random random = new Random();

    public static int getRandomInt(int limitation){
        return random.nextInt(limitation);
    }

    public static Properties getProperties(String fileName){
        Properties properties = new Properties();
        try (InputStream inputStream = Util.class.getResourceAsStream(fileName)) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            logger.error("файл свойств не найден", e);
        } catch (IOException exception) {
            logger.error("ошибка доступа к файлу свойств", exception);
        }
        return properties;
    }

    public static Account createRandomAccount() {
        Account account = new Account(Util.getRandomInt(1000) + "", 10000);
        logger.info("create account: " + account.getId() + ", balance = " + account.getMoney());
        return account;
    }

    public static List<Account> createListOfAccounts(int numberOfAccounts) {
        ArrayList<Account> accounts = new ArrayList<>();
        for (int i = 0; i < numberOfAccounts; i++) {
            accounts.add(createRandomAccount());
        }
        return new CopyOnWriteArrayList<Account>(accounts);
    }

}
