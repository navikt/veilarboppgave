package no.nav.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;

import static no.nav.veilarboppgave.config.SoapClientConfiguration.virksomhetenhetOnBehalfOfSystemUser;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class VirksomhetEnhetServiceHelsesjekk implements Helsesjekk {

    static final String VIRKSOMHETENHET_V1_URL = "VIRKSOMHET_ENHET_V1_ENDPOINTURL";

    @Override
    public void helsesjekk() {
        virksomhetenhetOnBehalfOfSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "VirksomhetEnhet via SOAP " + getRequiredProperty(VIRKSOMHETENHET_V1_URL);
        String beskrivelse = "Sjekker om VirksomhetEnhet-tjenesten svarer.";
        return new HelsesjekkMetadata("virksomhetenhet", endepunkt, beskrivelse, true);
    }
}
