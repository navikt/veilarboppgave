package no.nav.veilarboppgave.service;

import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.client.oppgave.OppgaveClient;
import no.nav.veilarboppgave.domain.*;
import no.nav.veilarboppgave.repositoyry.OppgavehistorikkRepository;
import no.nav.veilarboppgave.utils.TestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class OppgaveServiceTest {

    private OppgaveClient oppgaveClient;
    private OppgavehistorikkRepository oppgavehistorikkRepository;
    private AuthService authService;

    private OppgaveService oppgaveService;

    @Before
    public void setUp() {
        oppgaveClient = mock(OppgaveClient.class);
        oppgavehistorikkRepository = mock(OppgavehistorikkRepository.class);
        authService = mock(AuthService.class);
        oppgaveService = new OppgaveService(oppgaveClient, oppgavehistorikkRepository, authService);
    }

    @Test
    public void opprettOppgave_skal_opprette_og_lagre_historikk() {
        // Arrange
        Fnr fnr = TestData.genererTilfeldigFnrMedTilgang();
        OppgaveDTO oppgaveDTO = TestData.oppgaveDTO(fnr);
        AktorId aktorId = AktorId.of("1000000000001");

        String oppgaveIdStr = "GSAK123";
        OppgaveId oppgaveId = new OppgaveId(oppgaveIdStr);

        when(oppgaveClient.opprettOppgave(any(Oppgave.class)))
                .thenReturn(Optional.of(oppgaveId));
        when(authService.getInnloggetBrukerIdent()).thenReturn("Z000000");

        OppgavehistorikkDTO expected = new OppgavehistorikkDTO(
                oppgaveDTO.getTema(),
                oppgaveDTO.getType(),
                new Timestamp(0),
                "Z000000",
                oppgaveIdStr,
                aktorId.get()
        );
        when(oppgavehistorikkRepository.hentOppgavehistorikkForGsakId(oppgaveId))
                .thenReturn(expected);

        // Act
        OppgavehistorikkDTO result = oppgaveService.opprettOppgave(aktorId, oppgaveDTO);

        // Assert returned value
        assertNotNull(result);
        assertEquals(expected, result);

        // Verify oppgaveClient called with correctly mapped Oppgave
        var oppgaveCaptor = org.mockito.ArgumentCaptor.forClass(Oppgave.class);
        verify(oppgaveClient, times(1)).opprettOppgave(oppgaveCaptor.capture());
        Oppgave sentOppgave = oppgaveCaptor.getValue();

        assertEquals(aktorId, sentOppgave.getAktorId());
        assertEquals(TemaDTO.OPPFOLGING, sentOppgave.getTemaDTO());
        assertEquals(OppgaveType.VURDER_KONSEKVENS_FOR_YTELSE, sentOppgave.getType());
        assertEquals(Prioritet.NORM, sentOppgave.getPrioritet());
        assertEquals(oppgaveDTO.getBeskrivelse(), sentOppgave.getBeskrivelse());
        assertEquals(LocalDate.parse("2017-09-18"), sentOppgave.getFraDato());
        assertEquals(LocalDate.parse("2017-10-18"), sentOppgave.getTilDato());
        assertEquals("1234", sentOppgave.getEnhetId());
        assertEquals("Z1234", sentOppgave.getVeilederId());
        assertEquals("0104", sentOppgave.getAvsenderenhetId());

        // Verify historikk insert with expected values
        var historikkCaptor = org.mockito.ArgumentCaptor.forClass(OppgavehistorikkDTO.class);
        verify(oppgavehistorikkRepository, times(1)).insertOppgaveHistorikk(historikkCaptor.capture());
        OppgavehistorikkDTO historikk = historikkCaptor.getValue();
        assertEquals(oppgaveDTO.getTema(), historikk.getTema());
        assertEquals(oppgaveDTO.getType(), historikk.getType());
        assertEquals("Z000000", historikk.getOpprettetAv());
        assertEquals(oppgaveIdStr, historikk.getGsakID());
        assertEquals(aktorId.get(), historikk.getAktoerid());
        assertNotNull("OpprettetDato should be set", historikk.getOpprettetDato());

        // Verify that we fetch historikk for returned value
        verify(oppgavehistorikkRepository, times(1)).hentOppgavehistorikkForGsakId(oppgaveId);
    }

    @Test(expected = ResponseStatusException.class)
    public void opprettOppgave_skal_kaste_500_nar_client_feiler() {
        // Arrange
        Fnr fnr = TestData.genererTilfeldigFnrMedTilgang();
        OppgaveDTO oppgaveDTO = TestData.oppgaveDTO(fnr);
        AktorId aktorId = AktorId.of("1000000000002");

        when(oppgaveClient.opprettOppgave(any(Oppgave.class)))
                .thenReturn(Optional.empty());

        // Act -> Expect exception
        oppgaveService.opprettOppgave(aktorId, oppgaveDTO);
    }
}
