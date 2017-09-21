package no.nav.fo.veilarboppgave;

import lombok.SneakyThrows;
import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.fo.veilarboppgave.mocks.BehandleOppgaveServiceMock;
import no.nav.fo.veilarboppgave.mocks.PepClientMock;
import no.nav.fo.veilarboppgave.mocks.PersonServiceMock;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.ArbeidsfordelingService;
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
    @SneakyThrows
    public PepClient pepClient() {
        return new PepClientMock();
    }

    @Override
    public Sone getSone() {
        return FSS;
    }
}
