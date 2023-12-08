package no.nav.veilarboppgave.controller.v2;

import no.nav.veilarboppgave.domain.EnheterRequest;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.EnheterService;
import no.nav.veilarboppgave.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EnhetControllerV2Test {

    private EnheterControllerV2 enheterControllerV2;
    private AuthService authServiceMock;

    @BeforeEach
    public void setUp() {
        authServiceMock = mock(AuthService.class);
        enheterControllerV2 = new EnheterControllerV2(mock(EnheterService.class), authServiceMock);
    }

    @Test
    public void skal_sjekke_tilgang() {
        EnheterRequest enheterRequest = new EnheterRequest(TestData.genererTilfeldigFnrUtenTilgang(), null);

        enheterControllerV2.hentEnheter(enheterRequest, TemaDTO.OPPFOLGING.name());

        verify(authServiceMock, atLeastOnce()).sjekkLesetilgangMedAktorId(any());
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() {
        assertThrows(ResponseStatusException.class, () -> {
            EnheterRequest enheterRequest = new EnheterRequest(TestData.genererTilfeldigFnrMedTilgang(), null);
            enheterControllerV2.hentEnheter(enheterRequest, "UGYLDIG_TEMA");
        });
    }
}
