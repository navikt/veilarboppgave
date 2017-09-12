package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingConfig;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingService;
import no.nav.fo.veilarboppgave.norg.ArbeidsfordelingServiceImpl;
import no.nav.fo.veilarboppgave.rest.api.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.OppgaveRessurs;
import no.nav.fo.veilarboppgave.tps.PersonConfig;
import no.nav.fo.veilarboppgave.tps.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static no.nav.apiapp.ApiApplication.Sone.FSS;

@Configuration
@Import({
        EnheterRessurs.class,
        OppgaveRessurs.class,
        ArbeidsfordelingConfig.class,
        PersonConfig.class
})
public class ApplicationConfig implements ApiApplication{

    @Override
    public Sone getSone() {
        return FSS;
    }

    @Bean
    public ArbeidsfordelingService arbeidsfordelingService() {
        return new ArbeidsfordelingServiceImpl();
    }

    @Bean
    public PersonServiceImpl personService() {
        return new PersonServiceImpl();
    }
}
