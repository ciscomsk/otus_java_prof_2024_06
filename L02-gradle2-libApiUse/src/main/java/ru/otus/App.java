package ru.otus;

import com.google.common.collect.Lists;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Lib.log();

        // api - guava доступна
        // implementation - guava недоступна
        logger.info("{}", Lists.reverse(List.of(1, 2, 3)));
    }
}
