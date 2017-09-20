package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.fo.veilarboppgave.mocks.PepClientMock;
import no.nav.fo.veilarboppgave.mocks.PersonServiceMock;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static no.nav.fo.veilarboppgave.TestData.*;
import static no.nav.fo.veilarboppgave.domene.Tema.OPPFOLGING;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnhetRessursTest {
    private EnheterRessurs enheterRessurs;

    @BeforeEach
    public void setUp() throws Exception {
        enheterRessurs = new EnheterRessurs(
                new ArbeidsfordelingServiceMock(),
                new PersonServiceMock(),
                new PepClientMock()
        );
    }

    @Test
    public void skal_nekte_tilgang_til_fnr() throws Exception {
        String fnr = genererTilfeldigFnrUtenTilgang().getFnr();
        assertThrows(IngenTilgang.class, () -> enheterRessurs.hentEnheter(fnr, OPPFOLGING.name()));
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_fnr() throws Exception {
        assertThrows(UgyldigRequest.class, () -> enheterRessurs.hentEnheter(IKKE_GYLDIG_FNR.getFnr(), OPPFOLGING.name()));

    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() throws Exception {
        assertThrows(UgyldigRequest.class, () -> {
            String fnr = genererTilfeldigFnrMedTilgang().getFnr();
            enheterRessurs.hentEnheter(fnr, "UGYLDIG_TEMA");
        });
    }
}
