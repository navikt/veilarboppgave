package no.nav.veilarboppgave.utils;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class LocalH2Database {

    private static JdbcTemplate db;

    public static JdbcTemplate getDb() {
        if (db == null) {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setURL("jdbc:h2:mem:veilarboppgave-local;DB_CLOSE_DELAY=-1;MODE=Oracle;TRACE_LEVEL_SYSTEM_OUT=1");

            db = new JdbcTemplate(dataSource);

            Flyway.configure()
                    .dataSource(dataSource)
                    .load()
                    .migrate();
        }

        return db;
    }

}
