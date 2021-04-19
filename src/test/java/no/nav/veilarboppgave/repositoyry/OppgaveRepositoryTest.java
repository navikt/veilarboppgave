package no.nav.veilarboppgave.repositoyry;

import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.utils.LocalPostgresDatabase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Timestamp;
import java.util.List;

import static no.nav.veilarboppgave.utils.LocalPostgresDatabase.createPostgresJdbcTemplate;
import static org.junit.Assert.assertEquals;


public class OppgaveRepositoryTest {

    @Rule
    public PostgreSQLContainer<?> postgresContainer = LocalPostgresDatabase.createPostgresContainer();

    private OppgavehistorikkRepository oppgavehistorikkRepository;

    @Before
    public void setUp() {
        JdbcTemplate jdbcTemplate = createPostgresJdbcTemplate(postgresContainer);
        LocalPostgresDatabase.cleanAndMigrate(jdbcTemplate.getDataSource());
        oppgavehistorikkRepository = new OppgavehistorikkRepository(jdbcTemplate);
    }

    @Test
    public void skalInsertOppgavehistorikk() {
        AktorId aktorId = AktorId.of("aktoerid");
        Timestamp opprettet = new Timestamp(0);
        OppgavehistorikkDTO oppgave = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktorId.get(), 1L);
        oppgavehistorikkRepository.insertOppgaveHistorikk(oppgave);

        OppgavehistorikkDTO hentetOppgave = oppgavehistorikkRepository.hentOppgavehistorikkForBruker(aktorId).get(0);
        assertEquals("tema", hentetOppgave.getTema());
    }

    @Test
    public void skalHenteListeMedOppgavehistorikk() {
        AktorId aktoerid = AktorId.of("aktoerid");
        Timestamp opprettet = new Timestamp(0);

        OppgavehistorikkDTO oppgave1 = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.get());

        OppgavehistorikkDTO oppgave2 = new OppgavehistorikkDTO("tema", "type", opprettet,"Z000000",
                "gsakid",aktoerid.get());

        oppgavehistorikkRepository.insertOppgaveHistorikk(oppgave1);
        oppgavehistorikkRepository.insertOppgaveHistorikk(oppgave2);

        List<OppgavehistorikkDTO> hentetOppgaver = oppgavehistorikkRepository.hentOppgavehistorikkForBruker(aktoerid);
        assertEquals(2, hentetOppgaver.size());
    }

}