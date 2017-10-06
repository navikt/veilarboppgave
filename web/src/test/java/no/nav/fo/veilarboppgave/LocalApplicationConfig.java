package no.nav.fo.veilarboppgave;

import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import no.nav.fo.veilarboppgave.mocks.*;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgavehistorikkRessurs;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.ws.consumer.aktoer.AktoerService;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetService;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import static no.nav.apiapp.ApiApplication.Sone.FSS;
import static no.nav.fo.veilarboppgave.config.LocalJndiContextConfig.setupInMemoryDatabase;

@Configuration
@Import({
        EnheterRessurs.class,
        OppgaveRessurs.class,
        OppgavehistorikkRessurs.class
})
public class LocalApplicationConfig implements ApiApplication{

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
    public EnhetServiceMock virksomhetEnhetService() { return new EnhetServiceMock(); }

    @Bean
    public OrganisasjonEnhetService organisasjonEnhetService() { return new OrganisasjonEnhetServiceMock(); }

    @Bean
    public AktoerService aktoerService() {
        return new AktoerServiceMock();
    }

    @Bean
    public OppgaveRepository oppgaveRepository() { return new OppgaveRepository(new JdbcTemplate(setupInMemoryDatabase())); }

    @Bean
    public PepClient pepClient() {
        return new PepClientMock();
    }

    @Override
    public Sone getSone() {
        return FSS;
    }
}
