package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.PepClient;
import no.nav.fo.veilarboppgave.mocks.AktorServiceMock;
import no.nav.fo.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.fo.veilarboppgave.mocks.OrganisasjonEnhetServiceMock;
import no.nav.fo.veilarboppgave.mocks.PersonServiceMock;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static no.nav.fo.veilarboppgave.TestData.*;
import static no.nav.fo.veilarboppgave.domene.TemaDTO.OPPFOLGING;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

public class EnhetRessursTest {
    private EnheterRessurs enheterRessurs;
    private PepClient pepClientMock;

    @BeforeEach
    public void setUp() {

        pepClientMock = mock(PepClient.class);

        enheterRessurs = new EnheterRessurs(
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
        String fnr = genererTilfeldigFnrUtenTilgang().getFnr();
        doThrow(new IngenTilgang()).when(pepClientMock).sjekkLesetilgangTilAktorId("testaktoerid");

        assertThrows(IngenTilgang.class, () -> enheterRessurs.hentEnheter(fnr, OPPFOLGING.name()));
    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_fnr() {
        assertThrows(UgyldigRequest.class, () -> enheterRessurs.hentEnheter(IKKE_GYLDIG_FNR.getFnr(), OPPFOLGING.name()));

    }

    @Test
    public void skal_kaste_exception_ved_validering_av_ugyldig_tema() {
        assertThrows(UgyldigRequest.class, () -> {
            String fnr = genererTilfeldigFnrMedTilgang().getFnr();
            enheterRessurs.hentEnheter(fnr, "UGYLDIG_TEMA");
        });
    }
}
