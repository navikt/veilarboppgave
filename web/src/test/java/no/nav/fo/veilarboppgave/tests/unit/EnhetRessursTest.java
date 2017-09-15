package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.fo.veilarboppgave.mocks.PersonServiceMock;
import no.nav.fo.veilarboppgave.rest.api.enheter.EnheterRessurs;
import no.nav.fo.veilarboppgave.security.abac.PepClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static no.nav.fo.veilarboppgave.TestData.IKKE_GYLDIG_FNR;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnhetRessursTest {
    private static final String GYLDIG_FNR ="XXXXXXXXXXX";
    private EnheterRessurs enheterRessurs;

    @Mock
    private PepClient pepClientMock;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        enheterRessurs = new EnheterRessurs(
                new ArbeidsfordelingServiceMock(),
                new PersonServiceMock(),
                pepClientMock
        );
    }

    @Test
    public void skal_sjekke_tilgang_til_fnr() throws Exception {
        enheterRessurs.hentEnheter(GYLDIG_FNR);
        verify(pepClientMock, times(1)).sjekkTilgangTilFnr(anyString());
    }

    @Test
    public void skal_validere_fnr() throws Exception {
        exception.expect(UgyldigRequest.class);
        enheterRessurs.hentEnheter(IKKE_GYLDIG_FNR);
    }
}
