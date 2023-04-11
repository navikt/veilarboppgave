package no.nav.veilarboppgave.service;

import com.nimbusds.jwt.JWTClaimsSet;
import no.nav.common.abac.VeilarbPep;
import no.nav.common.auth.context.AuthContextHolder;
import no.nav.common.client.aktoroppslag.AktorOppslagClient;
import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.common.types.identer.NavIdent;
import no.nav.poao_tilgang.client.Decision;
import no.nav.poao_tilgang.client.PoaoTilgangClient;
import no.nav.poao_tilgang.client.api.ApiResult;
import no.nav.veilarboppgave.utils.TestData;
import no.nav.veilarboppgave.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    private AuthService authService;
    private AuthContextHolder authContextHolder = mock(AuthContextHolder.class);
    private final PoaoTilgangClient poaoTilgangClient = mock(PoaoTilgangClient.class);
    private final UnleashService unleashService = mock(UnleashService.class);

    @BeforeEach
    public void setUp() {
        authService = new AuthService(
                mock(VeilarbPep.class),
                mock(AktorOppslagClient.class),
                authContextHolder,
                poaoTilgangClient,
                unleashService);
    }

    @Test
    public void skal_sjekke_tilgang_via_poao_when_enabled() {
        NavIdent navIdent = new NavIdent("123");
        Fnr FNR = TestData.genererTilfeldigFnrUtenTilgang();
        AktorId aktorId = new AktorId("111");
        JWTClaimsSet claims = TestUtils.getJwtClaimsSet(navIdent, FNR);
        ApiResult apiresult = new ApiResult<>(null, Decision.Permit.INSTANCE);

        when(unleashService.isPoaoTilgangEnabled()).thenReturn(true);
        when(authService.getFnrOrThrow(any())).thenReturn(FNR);
        when(authContextHolder.getIdTokenClaims()).thenReturn(Optional.of(claims));
        when(poaoTilgangClient.evaluatePolicy(any())).thenReturn(apiresult);

        authService.sjekkLesetilgangMedAktorId(aktorId);

        verify(poaoTilgangClient, atLeastOnce()).evaluatePolicy(any());
    }


}
