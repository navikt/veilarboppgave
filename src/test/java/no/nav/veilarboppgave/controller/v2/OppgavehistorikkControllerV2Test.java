package no.nav.veilarboppgave.controller.v2;


import no.nav.common.types.identer.AktorId;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.domain.OppgavehistorikkRequest;
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
import static org.mockito.Mockito.*;

public class OppgavehistorikkControllerV2Test {

    private AuthService authService = mock(AuthService.class);

    private OppgavehistorikkRepository oppgavehistorikkRepository;

    private OppgavehistorikkControllerV2 oppgavehistorikkControllerV2;

    @Before
    public void resetMocks() {
        JdbcTemplate db = SingletonPostgresContainer.init().createJdbcTemplate();
        oppgavehistorikkRepository = new OppgavehistorikkRepository(db);
        OppgavehistorikkService oppgavehistorikkService = new OppgavehistorikkService(oppgavehistorikkRepository);
        oppgavehistorikkControllerV2 = new OppgavehistorikkControllerV2(authService, oppgavehistorikkService);

        reset(authService);
    }

    @Test
    public void skalHenteOppgavehistorikk() {
        AktorId aktoerid = AktorId.of("4444");
        OppgavehistorikkRequest oppgavehistorikkRequest = new OppgavehistorikkRequest(Fnr.of("3333"));

        when(authService.getAktorIdOrThrow(any())).thenReturn(aktoerid);

        oppgavehistorikkRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));
        oppgavehistorikkRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));

        List<Oppgavehistorikk> oppgavehistorikkDTOS = oppgavehistorikkControllerV2.getOppgavehistorikk(oppgavehistorikkRequest);
        assertEquals(2, oppgavehistorikkDTOS.size());
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