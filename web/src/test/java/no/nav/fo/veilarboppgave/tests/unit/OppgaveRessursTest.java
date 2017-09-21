package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.mocks.PepClientMock;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static no.nav.fo.veilarboppgave.TestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OppgaveRessursTest {

    private OppgaveRessurs oppgaveRessurs;

    @BeforeEach
    void setUp() throws Exception {
        oppgaveRessurs = new OppgaveRessurs(new BehandleOppgaveServiceMock(), new PepClientMock());
    }

    @Test
    void skal_nekte_tilgang_til_fnr() throws Exception {
        OppgaveDTO testData = oppgaveDTO(genererTilfeldigFnrUtenTilgang());
        assertThrows(IngenTilgang.class, () -> oppgaveRessurs.opprettOppgave(testData));
    }

    @Test
    void skal_kaste_exception_ved_validering_av_ugyldig_fnr() throws Exception {
        OppgaveDTO testData = oppgaveDTO(IKKE_GYLDIG_FNR);
        assertThrows(UgyldigRequest.class, () -> oppgaveRessurs.opprettOppgave(testData));
    }
}
