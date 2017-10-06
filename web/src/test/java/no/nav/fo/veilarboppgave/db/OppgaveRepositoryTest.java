package no.nav.fo.veilarboppgave.db;

import no.nav.fo.veilarboppgave.config.LocalJndiContextConfig;
import no.nav.fo.veilarboppgave.domene.Aktoerid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class OppgaveRepositoryTest {

    OppgaveRepository oppgaveRepository;

    @BeforeEach
    public void setUp() {
        oppgaveRepository = new OppgaveRepository(new JdbcTemplate(LocalJndiContextConfig.setupInMemoryDatabase()));
    }

    @Test
    public void skalInsertOppgavehistorikk() {
        Aktoerid aktoerid = Aktoerid.of("aktoerid");
        Timestamp opprettet = new Timestamp(0);
        OppgavehistorikkDTO oppgave = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.getAktoerid(), 1L);
        oppgaveRepository.insertOppgaveHistorikk(oppgave);

        OppgavehistorikkDTO hentetOppgave = oppgaveRepository.hentOppgavehistorikkForBruker(aktoerid).get(0);
        assertThat(hentetOppgave.getTema()).isEqualTo("tema");
    }

    @Test
    public void skalHenteListeMedOppgavehistorikk() {
        Aktoerid aktoerid = Aktoerid.of("aktoerid");
        Timestamp opprettet = new Timestamp(0);
        OppgavehistorikkDTO oppgave1 = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.getAktoerid());
        OppgavehistorikkDTO oppgave2 = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.getAktoerid());
        oppgaveRepository.insertOppgaveHistorikk(oppgave1);
        oppgaveRepository.insertOppgaveHistorikk(oppgave2);
        List<OppgavehistorikkDTO> hentetOppgaver = oppgaveRepository.hentOppgavehistorikkForBruker(aktoerid);
        assertThat(hentetOppgaver.size()).isEqualTo(2);

    }
}