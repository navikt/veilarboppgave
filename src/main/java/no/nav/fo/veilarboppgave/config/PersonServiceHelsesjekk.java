package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.context.annotation.Configuration;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.personV3WithSystemUser;

@Configuration
public class PersonServiceHelsesjekk implements Helsesjekk {

    public static final String PERSON_V3_ENDPOINT = "VIRKSOMHET_PERSON_V3_ENDPOINTURL";

    @Override
    public void helsesjekk() {
        personV3WithSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "PersonService via SOAP " + System.getProperty(PERSON_V3_ENDPOINT);
        String beskrivelse = "Sjekker om Person-tjenesten svarer.";
        return new HelsesjekkMetadata("personservice", endepunkt, beskrivelse, true);
    }
}
