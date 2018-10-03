package no.nav.fo.veilarboppgave.config;

import no.nav.sbl.jdbc.Database;
import no.nav.sbl.jdbc.DataSourceFactory;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    private static final String VEILARBOPPGAVEDB_URL = "VEILARBOPPGAVEDB_URL";
    private static final String VEILARBOPPGAVEDB_USERNAME = "VEILARBOPPGAVEDB_USERNAME";
    private static final String VEILARBOPPGAVEDB_PASSWORD = "VEILARBOPPGAVEDB_PASSWORD";

    @Bean
    public static DataSource getDataSource() {
        return DataSourceFactory.dataSource()
                .url(getRequiredProperty(VEILARBOPPGAVEDB_URL))
                .username(getRequiredProperty(VEILARBOPPGAVEDB_USERNAME))
                .password(getRequiredProperty(VEILARBOPPGAVEDB_PASSWORD))
                .maxPoolSize(300)
                .minimumIdle(1)
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Database database(JdbcTemplate jdbcTemplate) {
        return new Database(jdbcTemplate);
    }

    @Bean
    public OppgaveRepository oppgaveRepository(JdbcTemplate db) {
        return new OppgaveRepository(db);
    }
}
