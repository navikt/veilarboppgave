package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.PepClient;
import no.nav.fo.veilarboppgave.rest.api.OppgaveDTO;
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
public class OppgaveRessursTest {

    private static final String GYLDIG_FNR ="XXXXXXXXXXX";

    private OppgaveRessurs oppgaveRessurs;

    @Mock
    private PepClient pepClient;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        oppgaveRessurs = new OppgaveRessurs(new OppgaveServiceMock(), pepClient);
        when(pepClient.sjekkTilgangTilFnr(anyString())).thenReturn("XXXXXXXXXXX");
    }

    @Test
    public void skalSjekkeTilgangTilFnr() throws Exception {
        oppgaveRessurs.opprettOppgave(testData(GYLDIG_FNR));
        verify(pepClient, times(1)).sjekkTilgangTilFnr(anyString());
    }

    @Test
    public void skalValidereFnr() throws Exception {
        exception.expect(UgyldigRequest.class);
        oppgaveRessurs.opprettOppgave(testData("00000000000"));
    }

    private static OppgaveDTO testData(String fnr) {
        return new OppgaveDTO(
                fnr,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

    }
}
