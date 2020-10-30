package no.nav.veilarboppgave.controller;


import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgavehistorikkService;
import no.nav.veilarboppgave.utils.LocalH2Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class OppgavehistorikkControllerTest {

    private AuthService authService = mock(AuthService.class);

    private OppgaveRepository oppgaveRepository;

    private OppgavehistorikkController oppgavehistorikkController;

    @BeforeEach
    public void resetMocks() {
        reset(authService);
        oppgaveRepository = new OppgaveRepository(LocalH2Database.getDb());
        OppgavehistorikkService oppgavehistorikkService = new OppgavehistorikkService(oppgaveRepository);
        oppgavehistorikkController = new OppgavehistorikkController(authService, oppgavehistorikkService);
    }

    @Test
    public void skalHenteOppgavehistorikk() {
        AktorId aktoerid = AktorId.of("1111");
        Fnr fnr = Fnr.of("2222");

        when(authService.getAktorIdOrThrow(any())).thenReturn(aktoerid);
//        when(authService.getFnr(aktoerid)).thenReturn(Optional.of(fnr));

        oppgaveRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));
        oppgaveRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));

        List<Oppgavehistorikk> oppgavehistorikkDTOS = oppgavehistorikkController.getOppgavehistorikk(fnr);
        assertEquals(oppgavehistorikkDTOS.size(), 2);
    }

    private OppgavehistorikkDTO getOppgaveHitorikk(AktorId aktorId) {
        return new OppgavehistorikkDTO("tema",
                "type",
                new Timestamp(0),
                "X000000",
                "GSAKID",
                aktorId.get());
    }

}