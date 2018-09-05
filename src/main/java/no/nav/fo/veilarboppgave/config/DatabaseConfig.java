package no.nav.fo.veilarboppgave.config;

import com.zaxxer.hikari.HikariDataSource;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    public static final String VEILARBOPPGAVEDB_URL = "VEILARBOPPGAVEDB_URL";
    public static final String VEILARBOPPGAVEDB_USERNAME = "VEILARBOPPGAVEDB_USERNAME";
    public static final String VEILARBOPPGAVEDB_PASSWORD = "VEILARBOPPGAVEDB_PASSWORD";

    @Bean
    public static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getRequiredProperty(VEILARBOPPGAVEDB_URL));
        config.setUsername(getRequiredProperty(VEILARBOPPGAVEDB_USERNAME));
        config.setPassword(getRequiredProperty(VEILARBOPPGAVEDB_PASSWORD));
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);

        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public OppgaveRepository oppgaveRepository(JdbcTemplate db) {
        return new OppgaveRepository(db);
    }
}
