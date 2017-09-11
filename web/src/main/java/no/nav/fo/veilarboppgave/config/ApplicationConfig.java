package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.ApiApplication;
import no.nav.fo.veilarboppgave.rest.api.EnheterRessurs;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static no.nav.apiapp.ApiApplication.Sone.FSS;

@Configuration
@Import({
        EnheterRessurs.class
})
public class ApplicationConfig implements ApiApplication{

    @Override
    public Sone getSone() {
        return FSS;
    }
}
