package no.nav.veilarboppgave.repositoyry;

import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.utils.LocalH2Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class OppgaveRepositoryTest {

    private OppgaveRepository oppgaveRepository;

    @BeforeEach
    public void setUp() {
        oppgaveRepository = new OppgaveRepository(LocalH2Database.getDb());
    }

    @Test
    public void skalInsertOppgavehistorikk() {
        AktorId aktorId = AktorId.of("aktoerid");
        Timestamp opprettet = new Timestamp(0);
        OppgavehistorikkDTO oppgave = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktorId.get(), 1L);
        oppgaveRepository.insertOppgaveHistorikk(oppgave);

        OppgavehistorikkDTO hentetOppgave = oppgaveRepository.hentOppgavehistorikkForBruker(aktorId).get(0);
        assertThat(hentetOppgave.getTema()).isEqualTo("tema");
    }

    @Test
    public void skalHenteListeMedOppgavehistorikk() {
        AktorId aktoerid = AktorId.of("aktoerid");
        Timestamp opprettet = new Timestamp(0);

        OppgavehistorikkDTO oppgave1 = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.get());

        OppgavehistorikkDTO oppgave2 = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.get());

        oppgaveRepository.insertOppgaveHistorikk(oppgave1);
        oppgaveRepository.insertOppgaveHistorikk(oppgave2);

        List<OppgavehistorikkDTO> hentetOppgaver = oppgaveRepository.hentOppgavehistorikkForBruker(aktoerid);
        assertThat(hentetOppgaver.size()).isEqualTo(2);

    }

}