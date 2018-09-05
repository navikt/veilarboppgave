package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.virksomhetenhetOnBehalfOfSystemUser;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class VirksomhetEnhetServiceHelsesjekk implements Helsesjekk {

    @Override
    public void helsesjekk() throws Throwable {
        virksomhetenhetOnBehalfOfSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = getRequiredProperty(VirksomhetEnhetEndpointConfig.NORG_VIRKSOMHET_ENHET_URL);
        String beskrivelse = "Sjekker om VirksomhetEnhet-tjenesten svarer.";
        return new HelsesjekkMetadata("virksomhetenhet", endepunkt, beskrivelse, true);
    }
}
