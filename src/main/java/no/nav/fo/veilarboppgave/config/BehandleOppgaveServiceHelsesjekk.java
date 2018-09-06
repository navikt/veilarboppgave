package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import org.springframework.context.annotation.Configuration;

import static no.nav.fo.veilarboppgave.config.SoapClientConfiguration.behandleOppgaveV1WithSystemUser;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
public class BehandleOppgaveServiceHelsesjekk implements Helsesjekk{

    static final String BEHANDLEOPPGAVE_V1_URL = "VIRKSOMHET_BEHANDLEOPPGAVE_V1_ENDPOINTURL";

    @Override
    public void helsesjekk() {
        behandleOppgaveV1WithSystemUser().ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "Behandle oppgave via SOAP " + getRequiredProperty(BEHANDLEOPPGAVE_V1_URL);
        String beskrivelse = "Sjekker om BehandleOppgave-tjenesten svarer.";
        return new HelsesjekkMetadata("behandleoppgave", endepunkt, beskrivelse, true);
    }
}
