package no.nav.veilarboppgave.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

public class LocalPostgresDatabase {

    public static PostgreSQLContainer<?> createPostgresContainer() {
        return new PostgreSQLContainer<>("postgres:12-alpine");
    }

    public static HikariDataSource createPostgresDataSource(PostgreSQLContainer<?> container) {
        HikariConfig config = new HikariConfig();
        config.setUsername(container.getUsername());
        config.setPassword(container.getPassword());
        config.setJdbcUrl(container.getJdbcUrl());

        return new HikariDataSource(config);
    }

    public static JdbcTemplate createPostgresJdbcTemplate(PostgreSQLContainer<?> container) {
       return new JdbcTemplate(createPostgresDataSource(container));
    }

    public static void cleanAndMigrate(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        flyway.clean();
        flyway.migrate();
    }

}
