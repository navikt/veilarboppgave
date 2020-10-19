package no.nav.veilarboppgave.utils;

import no.nav.veilarboppgave.utils.testdriver.TestDriver;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class LocalH2Database {

    private static JdbcTemplate db;

    public static JdbcTemplate getDb() {
        if (db == null) {
            TestDriver.init();

            DataSource dataSource = createTestDataSource("jdbc:h2:mem:veilarboppgave-local;DB_CLOSE_DELAY=-1;MODE=Oracle;TRACE_LEVEL_SYSTEM_OUT=1");
            db = new JdbcTemplate(dataSource);

            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);
            flyway.migrate();
        }

        return db;
    }

    private static DataSource createTestDataSource(String dbUrl) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(TestDriver.class.getName());
        dataSource.setUrl(dbUrl);
        return dataSource;
    }

}
