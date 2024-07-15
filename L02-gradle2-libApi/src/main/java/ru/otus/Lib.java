package ru.otus;

import com.google.common.collect.Lists;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lib {
    private static final Logger logger = LoggerFactory.getLogger(Lib.class);

    private Lib() {}

    public static void log() {
        logger.info("{}", Lists.reverse(List.of(1, 2, 3)));
        logger.info("I'm from L02-gradle2-libApi");
    }
}
