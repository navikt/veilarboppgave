package no.nav.fo.veilarboppgave.util.sql;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.function.Function;

public class SqlUtils {

    public static InsertQuery insert(JdbcTemplate db, String tableName) {
        return new InsertQuery(db, tableName);
    }

    public static <T> SelectQuery<T> select(DataSource ds, String tableName, Function<ResultSet, T> mapper) {
        return new SelectQuery<>(ds, tableName, mapper);
    }

}
