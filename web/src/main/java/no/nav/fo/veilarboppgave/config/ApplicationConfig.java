package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.ApiApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static no.nav.apiapp.ApiApplication.Sone.FSS;

@Configuration
public class ApplicationConfig implements ApiApplication{

    @Override
    public Sone getSone() {
        return FSS;
    }

    @Override
    public boolean brukSTSHelsesjekk() {
        return false;
    }
}
