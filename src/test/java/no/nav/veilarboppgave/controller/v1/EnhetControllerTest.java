package no.nav.veilarboppgave.controller.v1;

import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.controller.v1.EnheterController;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.EnheterService;
import no.nav.veilarboppgave.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EnhetControllerTest {

    private EnheterController enheterController;
    private AuthService authServiceMock;

    @BeforeEach
    public void setUp() {
        authServiceMock = mock(AuthService.class);
        enheterController = new EnheterController(mock(EnheterService.class), authServiceMock);
    }

    @Test
    public void skal_sjekke_tilgang() {
        Fnr fnr = TestData.genererTilfeldigFnrUtenTilgang();

        enheterController.hentEnheter(fnr, TemaDTO.OPPFOLGING.name());

        verify(authServiceMock, atLeastOnce()).sjekkLesetilgangMedAktorId(any());
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() {
        assertThrows(ResponseStatusException.class, () -> {
            Fnr fnr = TestData.genererTilfeldigFnrMedTilgang();
            enheterController.hentEnheter(fnr,  "UGYLDIG_TEMA");
        });
    }
}
