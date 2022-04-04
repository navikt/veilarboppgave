package no.nav.veilarboppgave.repositoyry;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import no.nav.common.types.identer.AktorId;
import no.nav.veilarboppgave.domain.OppgaveId;
import no.nav.veilarboppgave.domain.OppgavehistorikkDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Repository
public class OppgavehistorikkRepository {

    private final static String OPPGAVEHISTORIKK = "OPPGAVEHISTORIKK";
    private final static String ID = "ID";
    private final static String AKTOERID = "AKTOERID";
    private final static String GSAK_ID = "GSAK_ID";
    private final static String TEMA = "TEMA";
    private final static String TYPE = "TYPE";
    private final static String OPPRETTET_AV_IDENT = "OPPRETTET_AV_IDENT";
    private final static String OPPRETTET_TIDSPUNKT = "OPPRETTET_TIDSPUNKT";

    private final JdbcTemplate db;

    public void insertOppgaveHistorikk(OppgavehistorikkDTO oppgavehistorikkDTO) {
        String sql = format(
                "INSERT INTO %s(%s,%s,%s,%s,%s,%s) VALUES(?,?,?,?,?,?)",
                OPPGAVEHISTORIKK, GSAK_ID, TEMA, TYPE, OPPRETTET_AV_IDENT, OPPRETTET_TIDSPUNKT, AKTOERID
        );

        db.update(sql,
                oppgavehistorikkDTO.getGsakID(), oppgavehistorikkDTO.getTema(),
                oppgavehistorikkDTO.getType(), oppgavehistorikkDTO.getOpprettetAv(),
                oppgavehistorikkDTO.getOpprettetDato(), oppgavehistorikkDTO.getAktoerid()
        );
    }

    public List<OppgavehistorikkDTO> hentOppgavehistorikkForBruker(AktorId aktorId) {
        String sql = format("SELECT * FROM %s WHERE %s = ?", OPPGAVEHISTORIKK, AKTOERID);

        return db.query(sql, OppgavehistorikkRepository::mapOppgavehistorikkRow, aktorId.get());
    }

    public OppgavehistorikkDTO hentOppgavehistorikkForGsakId(OppgaveId gsakid) {
        String sql = format("SELECT * FROM %s WHERE %s = ? LIMIT 1", OPPGAVEHISTORIKK, GSAK_ID);

        List<OppgavehistorikkDTO> result = db.query(sql, OppgavehistorikkRepository::mapOppgavehistorikkRow, gsakid.getOppgaveId());

        return result.isEmpty() ? null : result.get(0);
    }

    @SneakyThrows
    public static OppgavehistorikkDTO mapOppgavehistorikkRow(ResultSet rs, int row) {
        return new OppgavehistorikkDTO(
                rs.getString(TEMA),
                rs.getString(TYPE),
                rs.getTimestamp(OPPRETTET_TIDSPUNKT),
                rs.getString(OPPRETTET_AV_IDENT),
                rs.getString(GSAK_ID),
                rs.getString(AKTOERID),
                rs.getLong(ID)
        );
    }

}
