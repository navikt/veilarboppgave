package no.nav.fo.veilarboppgave;

import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.mocks.*;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetService;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static no.nav.apiapp.ApiApplication.Sone.FSS;

@Configuration
@Import({
        EnheterRessurs.class,
        OppgaveRessurs.class,
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
    public PepClient pepClient() {
        return new PepClientMock();
    }

    @Override
    public Sone getSone() {
        return FSS;
    }
}
