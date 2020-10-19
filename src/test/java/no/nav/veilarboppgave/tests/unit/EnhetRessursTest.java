package no.nav.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.PepClient;
import no.nav.veilarboppgave.mocks.AktorServiceMock;
import no.nav.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.veilarboppgave.mocks.OrganisasjonEnhetServiceMock;
import no.nav.veilarboppgave.mocks.PersonServiceMock;
import no.nav.veilarboppgave.controller.EnheterController;
import no.nav.veilarboppgave.TestData;
import no.nav.veilarboppgave.domain.TemaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class EnhetRessursTest {
    private EnheterController enheterController;
    private PepClient pepClientMock;

    @BeforeEach
    public void setUp() {

        pepClientMock = mock(PepClient.class);

        enheterController = new EnheterController(
                new ArbeidsfordelingServiceMock(),
                new PersonServiceMock(),
                pepClientMock,
                new OrganisasjonEnhetServiceMock() {
                },
                new AktorServiceMock()
        );
    }

    @Test
    public void skal_nekte_tilgang_til_fnr() {
        String fnr = TestData.genererTilfeldigFnrUtenTilgang().getFnr();
        doThrow(new IngenTilgang()).when(pepClientMock).sjekkLesetilgangTilAktorId("testaktoerid");

        assertThrows(IngenTilgang.class, () -> enheterController.hentEnheter(fnr, TemaDTO.OPPFOLGING.name()));
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_fnr() {
        assertThrows(UgyldigRequest.class, () -> enheterController.hentEnheter(TestData.IKKE_GYLDIG_FNR.getFnr(), TemaDTO.OPPFOLGING.name()));

    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() {
        assertThrows(UgyldigRequest.class, () -> {
            String fnr = TestData.genererTilfeldigFnrMedTilgang().getFnr();
            enheterController.hentEnheter(fnr, "UGYLDIG_TEMA");
        });
    }
}
