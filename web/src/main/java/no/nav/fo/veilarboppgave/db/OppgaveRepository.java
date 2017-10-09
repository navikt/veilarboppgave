package no.nav.fo.veilarboppgave.db;

import no.nav.fo.veilarboppgave.domene.Aktoerid;
import no.nav.fo.veilarboppgave.util.sql.SqlUtils;
import no.nav.fo.veilarboppgave.util.sql.where.WhereClause;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OppgaveRepository {

    private JdbcTemplate db;
    private final static String OPPGAVEHISTORIKK = "OPPGAVEHISTORIKK";

    @Inject
    public OppgaveRepository(JdbcTemplate db) {
        this.db = db;
    }

    public void insertOppgaveHistorikk(OppgavehistorikkDTO oppgavehistorikkDTO) {
        SqlUtils.insert(db, "OPPGAVEHISTORIKK")
                .value("GSAK_ID", oppgavehistorikkDTO.getGsakID())
                .value("TEMA", oppgavehistorikkDTO.getTema())
                .value("TYPE", oppgavehistorikkDTO.getType())
                .value("OPPRETTET_AV_IDENT", oppgavehistorikkDTO.getOpprettetAv())
                .value("OPPRETTET_TIDSPUNKT", oppgavehistorikkDTO.getOpprettetDato())
                .value("AKTOERID", oppgavehistorikkDTO.getAktoerid())
                .execute();
    }

    public List<OppgavehistorikkDTO> hentOppgavehistorikkForBruker(Aktoerid aktoerid) {
        return Optional.ofNullable(SqlUtils.select(db.getDataSource(), OPPGAVEHISTORIKK, OppgavehistorikkDTO::mapper)
                .column("ID")
                .column("AKTOERID")
                .column("GSAK_ID")
                .column("TEMA")
                .column("TYPE")
                .column("OPPRETTET_AV_IDENT")
                .column("OPPRETTET_TIDSPUNKT")
                .where(WhereClause.equals("AKTOERID",aktoerid.getAktoerid()))
                .execute())
                .orElse(Collections.emptyList());
    }
}
