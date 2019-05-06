package no.nav.fo.veilarboppgave.tests.unit;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.veilarbabac.Bruker;
import no.nav.apiapp.security.veilarbabac.VeilarbAbacPepClient;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import no.nav.fo.veilarboppgave.mocks.AktorServiceMock;
import no.nav.fo.veilarboppgave.mocks.BehandleOppgaveServiceMock;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveRessurs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static no.nav.fo.veilarboppgave.TestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class OppgaveRessursTest {

    private OppgaveRessurs oppgaveRessurs;
    private VeilarbAbacPepClient pepClientMock;

    @BeforeEach
    void setUp() {

        pepClientMock = mock(VeilarbAbacPepClient.class);

        oppgaveRessurs = new OppgaveRessurs(
                new BehandleOppgaveServiceMock(),
                pepClientMock,
                mock(OppgaveRepository.class),
                new AktorServiceMock());
    }

    @Test
    void skal_nekte_tilgang_til_fnr() {
        OppgaveDTO testData = oppgaveDTO(genererTilfeldigFnrUtenTilgang());

        doThrow(new IngenTilgang()).when(pepClientMock).sjekkLesetilgangTilBruker(Bruker.fraFnr(testData.getFnr()).medAktoerIdSupplier(()->""));

        assertThrows(IngenTilgang.class, () -> oppgaveRessurs.opprettOppgave(testData));
    }

    @Test
    void skal_kaste_exception_ved_validering_av_ugyldig_fnr() {
        OppgaveDTO testData = oppgaveDTO(IKKE_GYLDIG_FNR);
        assertThrows(UgyldigRequest.class, () -> oppgaveRessurs.opprettOppgave(testData));
    }
}
