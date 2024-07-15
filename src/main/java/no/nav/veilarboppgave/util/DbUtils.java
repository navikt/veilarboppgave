package no.nav.veilarboppgave.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DbUtils {

    public static DataSource createDataSource(String dbUrl) {
        HikariConfig config = createDataSourceConfig(dbUrl);
        return new HikariDataSource(config);
    }

    public static HikariConfig createDataSourceConfig(String dbUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        return config;
    }

}
