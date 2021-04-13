package no.nav.veilarboppgave.util;

import com.zaxxer.hikari.HikariConfig;
import lombok.SneakyThrows;
import no.nav.vault.jdbc.hikaricp.HikariCPVaultUtil;

import javax.sql.DataSource;

import static no.nav.common.utils.EnvironmentUtils.isProduction;
import static no.nav.veilarboppgave.config.ApplicationConfig.APPLICATION_NAME;

public class DbUtils {

    public static DataSource createDataSource(String dbUrl) {
        HikariConfig config = createDataSourceConfig(dbUrl);
        return createVaultRefreshDataSource(config);
    }

    public static HikariConfig createDataSourceConfig(String dbUrl) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        return config;
    }

    @SneakyThrows
    private static DataSource createVaultRefreshDataSource(HikariConfig config) {
        String environment = isProduction().orElse(false) ? "prod" : "dev";
        String role = String.join("-", APPLICATION_NAME, environment, "admin");

        return HikariCPVaultUtil.createHikariDataSourceWithVaultIntegration(config, getMountPath(), role);
    }

    private static String getMountPath() {
        boolean isProd = isProduction().orElse(false);
        return "postgresql/" + (isProd ? "prod-fss" : "preprod-fss");
    }

}
