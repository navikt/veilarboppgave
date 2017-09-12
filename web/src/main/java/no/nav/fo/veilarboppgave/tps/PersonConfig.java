package no.nav.fo.veilarboppgave.tps;

import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.sbl.dialogarena.types.Pingable;
import no.nav.tjeneste.virksomhet.person.v3.PersonV3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {

    private PersonV3 personV3Ping = new CXFClient<>(PersonV3.class)
            .configureStsForSystemUserInFSS()
            .build();

    @Bean
    public PersonV3 personV3() {
        return new CXFClient<>(PersonV3.class)
                .configureStsForOnBehalfOfWithJWT()
                .build();
    }

    @Bean
    public Pingable arbeidsfordelingV1Ping() {

        Pingable.Ping.PingMetadata metadata = new Pingable.Ping.PingMetadata(
                "PersonService via SOAP" + System.getProperty("person.endpoint.url"),
                "Sjekker om Person-tjenesten svarer.",
                true
        );

        return () -> {
            try {
                personV3Ping.ping();
                return Pingable.Ping.lyktes(metadata);
            } catch (Exception e) {
                return Pingable.Ping.feilet(metadata, e);
            }
        };
    }
}
