package no.nav.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;

import static no.nav.veilarboppgave.config.SoapClientConfiguration.organisasjonenhetOnBehalfOfSystemUser;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class OrganisasjonEnhetServiceHelsesjekk implements Helsesjekk {

    static final String ORGANISASJONENHET_V2_URL = "VIRKSOMHET_ORGANISASJONENHET_V2_ENDPOINTURL";

    @Override
    public void helsesjekk() {
        organisasjonenhetOnBehalfOfSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "OrganisasjonEnhet via SOAP " + getRequiredProperty(ORGANISASJONENHET_V2_URL);
        String beskrivelse = "Sjekker om OrganisasjonEnhet-tjenesten svarer.";
        return new HelsesjekkMetadata("organisasjonenhet", endepunkt, beskrivelse, true);
    }
}
