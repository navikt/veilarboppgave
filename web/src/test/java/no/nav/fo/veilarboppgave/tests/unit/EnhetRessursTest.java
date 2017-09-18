package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.fo.veilarboppgave.mocks.PepClientMock;
import no.nav.fo.veilarboppgave.mocks.PersonServiceMock;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static no.nav.fo.veilarboppgave.TestData.*;
import static no.nav.fo.veilarboppgave.domene.Tema.OPPFOLGING;

public class EnhetRessursTest {
    private EnheterRessurs enheterRessurs;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        enheterRessurs = new EnheterRessurs(
                new ArbeidsfordelingServiceMock(),
                new PersonServiceMock(),
                new PepClientMock()
        );
    }

    @Test
    public void skal_nekte_tilgang_til_fnr() throws Exception {
        exception.expect(IngenTilgang.class);
        enheterRessurs.hentEnheter(genererTilfeldigFnrUtenTilgang().getFnr(), OPPFOLGING.name());
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_fnr() throws Exception {
        exception.expect(UgyldigRequest.class);
        enheterRessurs.hentEnheter(IKKE_GYLDIG_FNR.getFnr(), OPPFOLGING.name());
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() throws Exception {
        exception.expect(UgyldigRequest.class);
        enheterRessurs.hentEnheter(genererTilfeldigFnrMedTilgang().getFnr(), "UGYLDIG_TEMA");
    }

}
