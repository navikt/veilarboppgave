package no.nav.fo.veilarboppgave.config;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class InMemDatabaseConfig {

    public static DataSource setupInMemoryDatabase() {
        SingleConnectionDataSource ds = new SingleConnectionDataSource();
        ds.setSuppressClose(true);
        ds.setUrl("jdbc:h2:mem:veilarboppgave;DB_CLOSE_DELAY=-1;MODE=Oracle");
        ds.setUsername("sa");
        ds.setPassword("");
    
        Flyway flyway = new Flyway();
        flyway.setLocations("db/migration/veilarboppgaveDB");
        flyway.setDataSource(ds);
        flyway.migrate();
    
        return ds;
    }

}
