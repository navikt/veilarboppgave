package no.nav.fo.veilarboppgave;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;

public class Util {
    public static void switchOffLogging() {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.OFF);
    }
}
