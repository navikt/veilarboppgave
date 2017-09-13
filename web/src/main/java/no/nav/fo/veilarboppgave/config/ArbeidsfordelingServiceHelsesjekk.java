package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArbeidsfordelingServiceHelsesjekk implements Helsesjekk {

    private final static ArbeidsfordelingV1 arbeidsfordelingV1 = new CXFClient<>(ArbeidsfordelingV1.class)
            .address(System.getProperty("arbeidsfordelingV1.endpoint.url"))
            .configureStsForSystemUserInFSS()
            .build();

    @Override
    public void helsesjekk() throws Throwable {
        arbeidsfordelingV1.ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "Arbeidsfordeling via SOAP " + System.getProperty("arbeidsfordelingV1.endpoint.url");
        String beskrivelse = "Sjekker om Arbeidsfordeling-tjenesten svarer.";
        return new HelsesjekkMetadata(endepunkt, beskrivelse, true);
    }
}
