package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.context.annotation.Configuration;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.aktoerV2OnBehalfOfSystemUser;

@Configuration
public class AktoerServiceHelsesjekk implements Helsesjekk {


    @Override
    public void helsesjekk() throws Throwable {
        aktoerV2OnBehalfOfSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "Aktoerv2 via SOAP " + System.getProperty("aktoer.endpoint.url");
        String beskrivelse = "Sjekker om aktoer-tjenesten svarer.";
        return new HelsesjekkMetadata(endepunkt, beskrivelse, true);
    }
}
