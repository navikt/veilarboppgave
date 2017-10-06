package no.nav.fo.veilarboppgave.db;

import no.nav.fo.veilarboppgave.config.LocalJndiContextConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class OppgaveRepositoryTest {

    OppgaveRepository oppgaveRepository;

    @BeforeEach
    public void setUp() {
        oppgaveRepository = new OppgaveRepository(new JdbcTemplate(LocalJndiContextConfig.setupInMemoryDatabase()));
    }

    @Test
    public void skalInsertOppgavehistorikk() {
        String aktoerid = "aktoerid";
        Timestamp opprettet = new Timestamp(0);
        OppgavehistorikkDTO oppgave = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid, 1L);
        oppgaveRepository.insertOppgaveHistorikk(oppgave);

        OppgavehistorikkDTO hentetOppgave = oppgaveRepository.hentOppgavehistorikkForBruker(aktoerid).get(0);
        assertThat(hentetOppgave.getTema()).isEqualTo("tema");
    }
}