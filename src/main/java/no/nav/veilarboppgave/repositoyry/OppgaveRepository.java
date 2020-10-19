package no.nav.veilarboppgave.repositoyry;

import lombok.RequiredArgsConstructor;
import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.domain.OppgaveId;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import no.nav.veilarboppgave.util.sql.SqlUtils;
import no.nav.veilarboppgave.util.sql.where.WhereClause;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class OppgaveRepository {

    private final static String OPPGAVEHISTORIKK = "OPPGAVEHISTORIKK";

    private final JdbcTemplate db;

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

    public List<OppgavehistorikkDTO> hentOppgavehistorikkForBruker(AktorId aktorId) {
        return Optional.ofNullable(SqlUtils.select(db.getDataSource(), OPPGAVEHISTORIKK, OppgavehistorikkDTO::mapper)
                .column("ID")
                .column("AKTOERID")
                .column("GSAK_ID")
                .column("TEMA")
                .column("TYPE")
                .column("OPPRETTET_AV_IDENT")
                .column("OPPRETTET_TIDSPUNKT")
                .where(WhereClause.equals("AKTOERID", aktorId.get()))
                .execute())
                .orElse(Collections.emptyList());
    }

    public OppgavehistorikkDTO hentOppgavehistorikkForGSAKID(OppgaveId gsakid) {
        return SqlUtils.select(db.getDataSource(), OPPGAVEHISTORIKK, OppgavehistorikkDTO::mapper)
                .column("ID")
                .column("AKTOERID")
                .column("GSAK_ID")
                .column("TEMA")
                .column("TYPE")
                .column("OPPRETTET_AV_IDENT")
                .column("OPPRETTET_TIDSPUNKT")
                .where(WhereClause.equals("GSAK_ID",gsakid.getOppgaveId()))
                .execute().get(0);
    }

}
