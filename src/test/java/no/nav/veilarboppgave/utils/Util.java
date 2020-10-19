package no.nav.veilarboppgave.utils;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;

import static java.util.stream.IntStream.range;

public class Util {
    public static void switchOffLogging() {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.OFF);
    }

    public static String lagStringAvLengde(int lengde) {
        StringBuilder builder = new StringBuilder();
        range(0, lengde).forEach(
                x -> builder.append("X")
        );
        return builder.toString();
    }

}
