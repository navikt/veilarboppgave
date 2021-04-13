package no.nav.veilarboppgave.config;

import lombok.extern.slf4j.Slf4j;
import no.nav.veilarboppgave.util.DbUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Slf4j
@Configuration
public class DatabaseConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public DataSource dataSource(EnvironmentProperties properties) {
        return DbUtils.createDataSource(properties.getDbUrl());
    }

    @PostConstruct
    public void migrateDb() {
        log.info("Starting database migration...");

        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }

}
