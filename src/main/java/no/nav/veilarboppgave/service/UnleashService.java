package no.nav.veilarboppgave.service;

import lombok.RequiredArgsConstructor;
import no.nav.common.featuretoggle.UnleashClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnleashService {
    private static final String POAO_TILGANG_ENABLED = "veilarboppgave.poao-tilgang-enabled";

    private final UnleashClient unleashClient;

    public boolean isPoaoTilgangEnabled() {
        return unleashClient.isEnabled(POAO_TILGANG_ENABLED);
    }

}
