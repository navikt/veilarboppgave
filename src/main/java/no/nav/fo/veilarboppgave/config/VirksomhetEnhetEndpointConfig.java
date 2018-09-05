package no.nav.fo.veilarboppgave.config;

import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.virksomhet.tjenester.enhet.v1.Enhet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static no.nav.metrics.MetricsFactory.createTimerProxyForWebService;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Configuration
public class VirksomhetEnhetEndpointConfig {

    public static final String NORG_VIRKSOMHET_ENHET_URL = "VIRKSOMHET_ENHET_V1_ENDPOINTURL";

    @Bean
    public Enhet virksomhetEnhet() {
        return createTimerProxyForWebService("enhet_v1", new CXFClient<>(Enhet.class)
                .address(resolveVirksomhetEnhetUrl())
                .configureStsForOnBehalfOfWithJWT()
                .build(), Enhet.class);
    }

    private String resolveVirksomhetEnhetUrl() {
        return getRequiredProperty(NORG_VIRKSOMHET_ENHET_URL);
    }
}
