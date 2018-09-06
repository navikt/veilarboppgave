package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.organisasjonenhetOnBehalfOfSystemUser;

public class OrganisasjonEnhetServiceHelsesjekk implements Helsesjekk {

    public static final String ORGANISASJONENHET_V2_URL = "VIRKSOMHET_ORGANISASJONENHET_V2_ENDPOINTURL";

    @Override
    public void helsesjekk() throws Throwable {
        organisasjonenhetOnBehalfOfSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "OrganisasjonEnhet via SOAP " + System.getProperty(ORGANISASJONENHET_V2_URL);
        String beskrivelse = "Sjekker om OrganisasjonEnhet-tjenesten svarer.";
        return new HelsesjekkMetadata("organisasjonenhet", endepunkt, beskrivelse, true);
    }
}
