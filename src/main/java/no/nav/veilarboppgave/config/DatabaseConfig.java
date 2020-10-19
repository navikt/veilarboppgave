package no.nav.veilarboppgave.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.utils.Credentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static no.nav.common.utils.NaisUtils.getCredentials;

@Slf4j
@Configuration
public class DatabaseConfig {

    public static final String VEILARBOPPGAVEDB_URL = "VEILARBOPPGAVEDB_URL";
    public static final String VEILARBOPPGAVEDB_USERNAME = "VEILARBOPPGAVEDB_USERNAME";
    public static final String VEILARBOPPGAVEDB_PASSWORD = "VEILARBOPPGAVEDB_PASSWORD";

    private final EnvironmentProperties environmentProperties;

    private final Credentials oracleCredentials;

    public DatabaseConfig(EnvironmentProperties environmentProperties) {
        this.environmentProperties = environmentProperties;
        oracleCredentials = getCredentials("oracle_creds");
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environmentProperties.getDbUrl());
        config.setUsername(oracleCredentials.username);
        config.setPassword(oracleCredentials.password);
        config.setMaximumPoolSize(5);

        return new HikariDataSource(config);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public JdbcTemplate db(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
