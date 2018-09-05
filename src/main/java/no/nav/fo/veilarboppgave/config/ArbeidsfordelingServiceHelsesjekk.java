package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.context.annotation.Configuration;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.arbeidsfordelingV1WithSystemUser;

@Configuration
public class ArbeidsfordelingServiceHelsesjekk implements Helsesjekk {


    @Override
    public void helsesjekk() throws Throwable {
        arbeidsfordelingV1WithSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "Arbeidsfordeling via SOAP " + System.getProperty("arbeidsfordelingV1.endpoint.url");
        String beskrivelse = "Sjekker om Arbeidsfordeling-tjenesten svarer.";
        return new HelsesjekkMetadata("arbeidsfordelingservice", endepunkt, beskrivelse, true);
    }
}
