package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingServiceImpl;
import no.nav.fo.veilarboppgave.rest.api.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.OppgaveRessurs;
import no.nav.fo.veilarboppgave.tps.PersonServiceImpl;
import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;
import no.nav.tjeneste.virksomhet.person.v3.PersonV3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static no.nav.apiapp.ApiApplication.Sone.FSS;

@Configuration
@Import({
        EnheterRessurs.class,
        OppgaveRessurs.class,
        ArbeidsfordelingServiceHelsesjekk.class,
        PersonServiceHelsesjekk.class
})
public class ApplicationConfig implements ApiApplication {

    @Override
    public Sone getSone() {
        return FSS;
    }

    @Bean
    public ArbeidsfordelingService arbeidsfordelingService() {
        return new ArbeidsfordelingServiceImpl(arbeidsfordelingV1());
    }

    @Bean
    public PersonServiceImpl personService() {
        return new PersonServiceImpl(personV3());
    }

    private static ArbeidsfordelingV1 arbeidsfordelingV1() {
        return new CXFClient<>(ArbeidsfordelingV1.class)
                .address(System.getProperty("arbeidsfordelingV1.endpoint.url"))
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }

    private static PersonV3 personV3() {
        return new CXFClient<>(PersonV3.class)
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }
}
