package no.nav.fo.veilarboppgave.tps;

import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.sbl.dialogarena.types.Pingable;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArbeidsfordelingConfig {

    @Bean
    public ArbeidsfordelingV1 arbeidsfordelingV1() {
        return new CXFClient<>(ArbeidsfordelingV1.class)
                .configureStsForSystemUserInFSS()
                .build();
    }

    @Bean
    public Pingable arbeidsfordelingV1Ping() {
        Pingable.Ping.PingMetadata metadata = new Pingable.Ping.PingMetadata(
                "Arbeidsfordeling via SOAP" + System.getProperty("arbeidsfordeling.endpoint.url"),
                "Sjekker om Arbeidsfordeling-tjenesten svarer.",
                true
        );

        return () -> {
            try {
                arbeidsfordelingV1().ping();
                return Pingable.Ping.lyktes(metadata);
            } catch (Exception e) {
                return Pingable.Ping.feilet(metadata, e);
            }
        };

    }

}
