package no.nav.fo.veilarboppgave.config;

import no.nav.apiapp.selftest.Helsesjekk;
import no.nav.apiapp.selftest.HelsesjekkMetadata;
import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.tjeneste.virksomhet.person.v3.PersonV3;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonServiceHelsesjekk implements Helsesjekk {

    private static final PersonV3 personV3Ping = new CXFClient<>(PersonV3.class)
            .address(System.getProperty("personV3.endpoint.url"))
            .configureStsForSystemUserInFSS()
            .build();

    @Override
    public void helsesjekk() throws Throwable {
        personV3Ping.ping();
    }

    @Override
    public HelsesjekkMetadata getMetadata() {
        String endepunkt = "PersonService via SOAP " + System.getProperty("personV3.endpoint.url");
        String beskrivelse = "Sjekker om Person-tjenesten svarer.";
        return new HelsesjekkMetadata(endepunkt, beskrivelse, true);
    }
}
