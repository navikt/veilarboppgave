package no.nav.veilarboppgave.controller;

import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.EnheterService;
import no.nav.veilarboppgave.utils.TestData;
import no.nav.veilarboppgave.domain.TemaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class EnhetControllerTest {

    private EnheterController enheterController;
    private AuthService authServiceMock;
    private EnheterService enhetServiceMock;

    @BeforeEach
    public void setUp() {
        authServiceMock = mock(AuthService.class);
        enhetServiceMock = mock(EnheterService.class);

        enheterController = new EnheterController(enhetServiceMock, authServiceMock);
    }

    @Test
    public void skal_nekte_tilgang_til_fnr() {
        Fnr fnr = TestData.genererTilfeldigFnrUtenTilgang();
        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN)).when(authServiceMock).sjekkLesetilgangMedAktorId(AktorId.of("testaktoerid"));

        assertThrows(ResponseStatusException.class, () -> enheterController.hentEnheter(fnr, TemaDTO.OPPFOLGING.name()));
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_fnr() {
        assertThrows(ResponseStatusException.class, () -> enheterController.hentEnheter(TestData.IKKE_GYLDIG_FNR, TemaDTO.OPPFOLGING.name()));
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() {
        assertThrows(ResponseStatusException.class, () -> {
            Fnr fnr = TestData.genererTilfeldigFnrMedTilgang();
            enheterController.hentEnheter(fnr, "UGYLDIG_TEMA");
        });
    }
}
