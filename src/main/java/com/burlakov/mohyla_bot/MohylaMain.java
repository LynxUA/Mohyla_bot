package com.burlakov.mohyla_bot;

import com.burlakov.mohyla_bot.MohylaCommandFactory;
import io.github.nixtabyte.telegram.jtelebot.server.impl.DefaultCommandDispatcher;
import io.github.nixtabyte.telegram.jtelebot.server.impl.DefaultCommandQueue;
import io.github.nixtabyte.telegram.jtelebot.server.impl.DefaultCommandWatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by denysburlakov on 04.09.15.
 */
public class MohylaMain {
    public static void main(String[] args) {

        FileInputStream fis;
        Properties property = new Properties();
        String key = null;
        try {
            fis = new FileInputStream("src/main/resources/keys.properties");
            property.load(fis);

            key = property.getProperty("telegram.key");

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        DefaultCommandDispatcher dispatcher = new DefaultCommandDispatcher(10,100,new DefaultCommandQueue());
        dispatcher.startUp();
        DefaultCommandWatcher watcher = new DefaultCommandWatcher(2000, 100, key, dispatcher, new MohylaCommandFactory());
        watcher.startUp();
    }
}
