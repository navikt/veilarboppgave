package no.nav.veilarboppgave.controller;


import no.nav.veilarboppgave.controller.OppgavehistorikkController;
import no.nav.veilarboppgave.domain.Oppgavehistorikk;
import no.nav.veilarboppgave.repositoyry.OppgaveRepository;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

class OppgavehistorikkControllerTest {

//    private AktorService aktorService = mock(AktorService.class);
//
//    private OppgaveRepository oppgaveRepository;
//    private OppgavehistorikkController oppgavehistorikkController;
//
//
//    @BeforeEach
//    public void resetMocks() {
//        reset(aktorService);
//        oppgaveRepository = new OppgaveRepository(new JdbcTemplate(InMemDatabaseConfig.setupInMemoryDatabase()));
//        oppgavehistorikkController = new OppgavehistorikkController(aktorService, oppgaveRepository);
//    }
//
//    @Test
//    public void skalHenteOppgavehistorikk() {
//        String aktoerid = "1111";
//        String fnr = "2222";
//
//        when(aktorService.getAktorId(fnr)).thenReturn(Optional.of(aktoerid));
//        when(aktorService.getFnr(aktoerid)).thenReturn(Optional.of(fnr));
//
//        oppgaveRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));
//        oppgaveRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));
//
//        List<Oppgavehistorikk> oppgavehistorikkDTOS = oppgavehistorikkController.getOppgavehistorikk(fnr);
//        assertThat(oppgavehistorikkDTOS.size()).isEqualTo(2);
//    }
//
//    private OppgavehistorikkDTO getOppgaveHitorikk(String aktoerid) {
//        return new OppgavehistorikkDTO("tema",
//                "type",
//                new Timestamp(0),
//                "X000000",
//                "GSAKID",
//                aktoerid);
//    }

}