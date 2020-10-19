package no.nav.veilarboppgave.util.sql;

public class SqlUtilsException extends RuntimeException {
    public SqlUtilsException(String message) {
        super(message);
    }

    public SqlUtilsException(Throwable cause) {
        super(cause);
    }
}
