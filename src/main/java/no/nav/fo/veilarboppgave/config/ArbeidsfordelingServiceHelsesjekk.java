package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.context.annotation.Configuration;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.arbeidsfordelingV1WithSystemUser;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
public class ArbeidsfordelingServiceHelsesjekk implements Helsesjekk {

    static final String ARBEIDSFORDELING_V1_URL = "VIRKSOMHET_ARBEIDSFORDELING_V1_ENDPOINTURL";

    @Override
    public void helsesjekk() {
        arbeidsfordelingV1WithSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "Arbeidsfordeling via SOAP " + getRequiredProperty(ARBEIDSFORDELING_V1_URL);
        String beskrivelse = "Sjekker om Arbeidsfordeling-tjenesten svarer.";
        return new HelsesjekkMetadata("arbeidsfordelingservice", endepunkt, beskrivelse, true);
    }
}
