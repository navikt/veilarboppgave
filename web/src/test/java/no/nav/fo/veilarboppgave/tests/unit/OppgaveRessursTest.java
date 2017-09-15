package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.mocks.PepClientMock;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.OppgaveServiceMock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static no.nav.fo.veilarboppgave.TestData.*;

@RunWith(MockitoJUnitRunner.class)
public class OppgaveRessursTest {
    private static final String GYLDIG_FNR ="XXXXXXXXXXX";

    private OppgaveRessurs oppgaveRessurs;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        oppgaveRessurs = new OppgaveRessurs(new OppgaveServiceMock(), new PepClientMock());
    }

    @Test
    public void skal_nekte_tilgang_til_fnr() throws Exception {
        exception.expect(IngenTilgang.class);
        oppgaveRessurs.opprettOppgave(testData(IKKE_AUTORISERT_FNR));
    }

    @Test
    public void skal_validere_fnr() throws Exception {
        exception.expect(UgyldigRequest.class);
        oppgaveRessurs.opprettOppgave(testData(IKKE_GYLDIG_FNR));
    }

}
