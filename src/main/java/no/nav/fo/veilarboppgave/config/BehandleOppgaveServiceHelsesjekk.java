package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.context.annotation.Configuration;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.behandleOppgaveV1WithSystemUser;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
public class BehandleOppgaveServiceHelsesjekk implements Helsesjekk{

    private static final String BEHANDLEOPPGAVE_V1_ENDPOINTURL = "VIRKSOMHET_BEHANDLEOPPGAVE_V1_ENDPOINTURL";

    @Override
    public void helsesjekk() throws Throwable {
        behandleOppgaveV1WithSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = getRequiredProperty(BEHANDLEOPPGAVE_V1_ENDPOINTURL);
        String beskrivelse = "Sjekker om BehandleOppgave-tjenesten svarer.";
        return new HelsesjekkMetadata("behandleoppgave", endepunkt, beskrivelse, true);
    }
}
