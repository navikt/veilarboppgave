package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import no.nav.fo.veilarboppgave.security.abac.PepClientImpl;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveServiceImpl;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling.ArbeidsfordelingServiceImpl;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonServiceImpl;
import no.nav.sbl.dialogarena.common.abac.pep.Pep;
import no.nav.sbl.dialogarena.common.abac.pep.context.AbacContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static no.nav.apiapp.ApiApplication.Sone.FSS;
import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.*;

@Configuration
@Import({
        EnheterRessurs.class,
        OppgaveRessurs.class,
        ArbeidsfordelingServiceHelsesjekk.class,
        PersonServiceHelsesjekk.class,
        BehandleOppgaveServiceHelsesjekk.class,
        AbacContext.class,
})
public class ApplicationConfig implements ApiApplication {

    @Override
    public Sone getSone() {
        return FSS;
    }

    @Bean
    public ArbeidsfordelingService arbeidsfordelingService() {
        return new ArbeidsfordelingServiceImpl(arbeidsfordelingV1OnBehalfOfUser());
    }

    @Bean
    public PersonService personService() {
        return new PersonServiceImpl(personV3OnBehalfOfUser());
    }

    @Bean
    public BehandleOppgaveService oppgaveService() {
        return new BehandleOppgaveServiceImpl(behandleOppgaveV1OnBehalfOfUser());
    }

    @Bean
    public PepClient pepClient(Pep pep) {
        return new PepClientImpl(pep);
    }
}
