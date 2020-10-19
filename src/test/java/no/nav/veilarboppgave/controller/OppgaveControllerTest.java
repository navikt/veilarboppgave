package no.nav.veilarboppgave.controller;

import no.nav.veilarboppgave.domain.OppgaveDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgaveService;
import no.nav.veilarboppgave.utils.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

class OppgaveControllerTest {

    private OppgaveController oppgaveRessurs;
    private AuthService authServiceMock;

    @BeforeEach
    void setUp() {
        authServiceMock = mock(AuthService.class);
        oppgaveRessurs = new OppgaveController(mock(OppgaveService.class), authServiceMock);
    }

    @Test
    void skal_sjekke_tilgang() {
        OppgaveDTO testData = TestData.oppgaveDTO(TestData.genererTilfeldigFnrUtenTilgang());

        oppgaveRessurs.opprettOppgave(testData);

        verify(authServiceMock, atLeastOnce()).sjekkLesetilgangMedAktorId(any());
    }

}
