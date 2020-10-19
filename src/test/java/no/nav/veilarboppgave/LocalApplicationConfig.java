package no.nav.veilarboppgave;

import no.nav.apiapp.ApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import no.nav.apiapp.security.PepClient;
import no.nav.dialogarena.aktor.AktorService;
import no.nav.veilarboppgave.config.InMemDatabaseConfig;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import no.nav.fo.veilarboppgave.mocks.*;
import no.nav.veilarboppgave.mocks.*;
import no.nav.veilarboppgave.controller.EnheterController;
import no.nav.veilarboppgave.controller.OppgaveController;
import no.nav.veilarboppgave.controller.OppgavehistorikkController;
import no.nav.veilarboppgave.client.gsak.BehandleOppgaveService;
import no.nav.veilarboppgave.client.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.veilarboppgave.client.norg.organisasjonenhet.OrganisasjonEnhetService;
import no.nav.veilarboppgave.client.tps.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.mock;

@Configuration
@Import({
        EnheterController.class,
        OppgaveController.class,
        OppgavehistorikkController.class
})
public class LocalApplicationConfig implements ApiApplication {

    @Bean
    public ArbeidsfordelingService arbeidsfordelingService() {
        return new ArbeidsfordelingServiceMock();
    }

    @Bean
    public PersonService personService() {
        return new PersonServiceMock();
    }

    @Bean
    public BehandleOppgaveService oppgaveService() {
        return new BehandleOppgaveServiceMock();
    }

    @Bean
    public OrganisasjonEnhetService organisasjonEnhetService() { return new OrganisasjonEnhetServiceMock(); }

    @Bean
    public AktorService aktorService() { return new AktorServiceMock(); }

    @Bean
    public OppgaveRepository oppgaveRepository() { return new OppgaveRepository(new JdbcTemplate(InMemDatabaseConfig.setupInMemoryDatabase())); }

    @Bean
    public PepClient pepClient() {
        return mock(PepClient.class);
    }

    @Override
    public void configure(ApiAppConfigurator apiAppConfigurator) {

    }
}
