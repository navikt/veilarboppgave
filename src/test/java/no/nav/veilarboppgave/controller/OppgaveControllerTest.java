package no.nav.veilarboppgave.controller;

import no.nav.veilarboppgave.domain.OppgaveDTO;
import no.nav.veilarboppgave.controller.OppgaveController;
import no.nav.veilarboppgave.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class OppgaveControllerTest {

//    private OppgaveController oppgaveRessurs;
//    private PepClient pepClientMock;
//
//    @BeforeEach
//    void setUp() {
//
//        pepClientMock = mock(PepClient.class);
//
//        oppgaveRessurs = new OppgaveController(
//                new BehandleOppgaveServiceMock(),
//                pepClientMock,
//                mock(OppgaveRepository.class),
//                new AktorServiceMock());
//    }
//
//    @Test
//    void skal_nekte_tilgang_til_fnr() {
//        OppgaveDTO testData = TestData.oppgaveDTO(TestData.genererTilfeldigFnrUtenTilgang());
//
//        doThrow(new IngenTilgang()).when(pepClientMock).sjekkLesetilgangTilAktorId("testaktoerid");
//
//        assertThrows(IngenTilgang.class, () -> oppgaveRessurs.opprettOppgave(testData));
//    }
//
//    @Test
//    void skal_kaste_exception_ved_validering_av_ugyldig_fnr() {
//        OppgaveDTO testData = TestData.oppgaveDTO(TestData.IKKE_GYLDIG_FNR);
//        assertThrows(UgyldigRequest.class, () -> oppgaveRessurs.opprettOppgave(testData));
//    }
}
