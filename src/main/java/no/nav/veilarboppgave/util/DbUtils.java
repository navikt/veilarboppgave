package no.nav.veilarboppgave.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import no.nav.vault.jdbc.hikaricp.HikariCPVaultUtil;

import javax.sql.DataSource;

import static no.nav.common.utils.EnvironmentUtils.isProduction;
import static no.nav.veilarboppgave.config.ApplicationConfig.APPLICATION_NAME;

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
