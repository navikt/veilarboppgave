package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.PepClient;
import no.nav.fo.veilarboppgave.mocks.ArbeidsfordelingServiceMock;
import no.nav.fo.veilarboppgave.mocks.PersonServiceMock;
import no.nav.fo.veilarboppgave.rest.api.EnheterRessurs;
import no.nav.fo.veilarboppgave.rest.api.OppgaveRessurs;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.OppgaveServiceMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetRessursTest {

    private static final String GYLDIG_FNR ="XXXXXXXXXXX";

    private EnheterRessurs enheterRessurs;
    private OppgaveRessurs oppgaveRessurs;

    @Mock
    private PepClient pepClient;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        enheterRessurs = new EnheterRessurs(new ArbeidsfordelingServiceMock(), new PersonServiceMock(), pepClient);
        oppgaveRessurs = new OppgaveRessurs(new OppgaveServiceMock(), pepClient);
        when(pepClient.sjekkTilgangTilFnr(anyString())).thenReturn("XXXXXXXXXXX");
    }

    @Test
    public void skalSjekkeTilgangTilFnr() throws Exception {
        enheterRessurs.hentEnheter(GYLDIG_FNR);
        verify(pepClient, times(1)).sjekkTilgangTilFnr(anyString());
    }

    @Test
    public void skalValidereFnr() throws Exception {
        exception.expect(UgyldigRequest.class);
        enheterRessurs.hentEnheter("00000000000");
    }
}
