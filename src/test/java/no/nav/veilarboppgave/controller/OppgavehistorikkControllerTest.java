package no.nav.veilarboppgave.controller;


import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.repositoyry.OppgavehistorikkRepository;
import no.nav.veilarboppgave.service.AuthService;
import no.nav.veilarboppgave.service.OppgavehistorikkService;
import no.nav.veilarboppgave.utils.SingletonPostgresContainer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class OppgavehistorikkControllerTest {

    private AuthService authService = mock(AuthService.class);

    private OppgavehistorikkRepository oppgavehistorikkRepository;

    private OppgavehistorikkController oppgavehistorikkController;

    @Before
    public void resetMocks() {
        JdbcTemplate db = SingletonPostgresContainer.init().createJdbcTemplate();
        oppgavehistorikkRepository = new OppgavehistorikkRepository(db);
        OppgavehistorikkService oppgavehistorikkService = new OppgavehistorikkService(oppgavehistorikkRepository);
        oppgavehistorikkController = new OppgavehistorikkController(authService, oppgavehistorikkService);

        reset(authService);
    }

    @Test
    public void skalHenteOppgavehistorikk() {
        AktorId aktoerid = AktorId.of("1111");
        Fnr fnr = Fnr.of("2222");

        when(authService.getAktorIdOrThrow(any())).thenReturn(aktoerid);
//        when(authService.getFnr(aktoerid)).thenReturn(Optional.of(fnr));

        oppgavehistorikkRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));
        oppgavehistorikkRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));

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