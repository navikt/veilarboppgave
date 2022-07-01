package no.nav.veilarboppgave.service;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import no.nav.common.auth.context.AuthContextHolder;
import no.nav.common.token_client.client.AzureAdOnBehalfOfTokenClient;
import no.nav.common.utils.AuthUtils;
import no.nav.veilarboppgave.config.EnvironmentProperties;
import no.nav.veilarboppgave.util.DownstreamApi;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ContextAwareService {
    private final EnvironmentProperties environmentProperties;
    private final AzureAdOnBehalfOfTokenClient aadOboTokenClient;
    private final AuthContextHolder authContext;

    public Supplier<String> contextAwareUserTokenSupplier(DownstreamApi receivingApp) {
        String azureAdIssuer = environmentProperties.getNaisAadIssuer();
        return () -> {
            String token = authContext.requireIdTokenString();
            String tokenIssuer = authContext.getIdTokenClaims()
                    .map(JWTClaimsSet::getIssuer)
                    .orElseThrow();
            return AuthUtils.bearerToken(
                    azureAdIssuer.equals(tokenIssuer) ?
                            getAadOboTokenForTjeneste(token, receivingApp)
                            : token
            );
        };
    }

    private String getAadOboTokenForTjeneste(String token, DownstreamApi api) {
        String scope = "api://" + api.cluster + "." + api.namespace + "." + api.serviceName + "/.default";
        return aadOboTokenClient.exchangeOnBehalfOfToken(scope, token);
    }

}