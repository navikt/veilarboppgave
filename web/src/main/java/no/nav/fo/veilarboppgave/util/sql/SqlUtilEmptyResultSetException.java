package no.nav.fo.veilarboppgave.util.sql;

public class SqlUtilEmptyResultSetException extends RuntimeException{
    public SqlUtilEmptyResultSetException(String sql) {
        super("Følgende sql ga tomt ResultSet: " + sql);
    }
}
