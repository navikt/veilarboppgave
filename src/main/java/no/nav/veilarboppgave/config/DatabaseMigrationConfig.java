package no.nav.veilarboppgave.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.nav.common.utils.EnvironmentUtils;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static java.lang.String.format;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class DatabaseMigrationConfig {

    private final DataSource dataSource;

    @PostConstruct
    public void migrateDb() {
        log.info("Starting database migration...");

        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }

}
