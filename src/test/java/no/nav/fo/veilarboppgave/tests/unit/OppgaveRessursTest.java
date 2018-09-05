package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
//import no.nav.brukerdialog.security.context.InternbrukerSubjectHandler;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import no.nav.fo.veilarboppgave.mocks.AktorServiceMock;
import no.nav.fo.veilarboppgave.mocks.BehandleOppgaveServiceMock;
import no.nav.fo.veilarboppgave.mocks.EnhetServiceMock;
import no.nav.fo.veilarboppgave.mocks.PepClientMock;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.System.setProperty;
import static no.nav.fo.veilarboppgave.TestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class OppgaveRessursTest {

    private OppgaveRessurs oppgaveRessurs;
/*
    @BeforeAll
    public static void setIdent() {
        setProperty("no.nav.brukerdialog.security.context.subjectHandlerImplementationClass", InternbrukerSubjectHandler.class.getName());
        InternbrukerSubjectHandler.setVeilederIdent("testident");
    }
*/
    @BeforeEach
    void setUp() throws Exception {
        oppgaveRessurs = new OppgaveRessurs(new BehandleOppgaveServiceMock(), new PepClientMock(), new EnhetServiceMock(),
                mock(OppgaveRepository.class), new AktorServiceMock());
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
