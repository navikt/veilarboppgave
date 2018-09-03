package no.nav.fo.veilarboppgave.rest.api.oppgave;


import no.nav.dialogarena.aktor.AktorService;
import no.nav.fo.veilarboppgave.db.OppgaveRepository;
import no.nav.fo.veilarboppgave.db.OppgavehistorikkDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static no.nav.fo.veilarboppgave.config.LocalJndiContextConfig.setupInMemoryDatabase;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

class OppgavehistorikkRessursTest {

    AktorService aktorService = mock(AktorService.class);

    OppgaveRepository oppgaveRepository;
    OppgavehistorikkRessurs oppgavehistorikkRessurs;


    @BeforeEach
    public void resetMocks() {
        reset(aktorService);
        oppgaveRepository = new OppgaveRepository(new JdbcTemplate(setupInMemoryDatabase()));
        oppgavehistorikkRessurs = new OppgavehistorikkRessurs(aktorService, oppgaveRepository);
    }

    @Test
    public void skalHenteOppgavehistorikk() {
        String aktoerid = "1111";
        String fnr = "2222";

        when(aktorService.getAktorId(fnr)).thenReturn(Optional.of(aktoerid));
        when(aktorService.getFnr(aktoerid)).thenReturn(Optional.of(fnr));

        oppgaveRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));
        oppgaveRepository.insertOppgaveHistorikk(getOppgaveHitorikk(aktoerid));

        List<Oppgavehistorikk> oppgavehistorikkDTOS = oppgavehistorikkRessurs.getOppgavehistorikk(fnr);
        assertThat(oppgavehistorikkDTOS.size()).isEqualTo(2);
    }

    private OppgavehistorikkDTO getOppgaveHitorikk(String aktoerid) {
        return new OppgavehistorikkDTO("tema",
                "type",
                new Timestamp(0),
                "X000000",
                "GSAKID",
                aktoerid);
    }

}