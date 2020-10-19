package no.nav.veilarboppgave.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OppgavehistorikkDTO {
    private final String tema;
    private final String type;
    private final Timestamp opprettetDato;
    private final String opprettetAv;
    private final String gsakID;
    private final String aktoerid;
    private Long ID = null;

    public OppgavehistorikkDTO(String tema, String type, Timestamp opprettetDato, String opprettetAv,
                               String gsakID, String aktoerid) {
        this.tema = tema;
        this.type = type;
        this.opprettetDato = opprettetDato;
        this.opprettetAv = opprettetAv;
        this.gsakID = gsakID;
        this.aktoerid = aktoerid;
    }

    public OppgavehistorikkDTO(String tema, String type, Timestamp opprettetDato, String opprettetAv,
                               String gsakID, String aktoerid, Long id) {
        this.tema = tema;
        this.type = type;
        this.opprettetDato = opprettetDato;
        this.opprettetAv = opprettetAv;
        this.gsakID = gsakID;
        this.aktoerid = aktoerid;
        this.ID = id;
    }

    @SneakyThrows
    public static List<OppgavehistorikkDTO> mapper(ResultSet rs) {
        List<OppgavehistorikkDTO> oppgaver = new ArrayList<>();
        oppgaver.add(toOppgavehistorikkDTO(rs));

        while(rs.next()) {
            oppgaver.add(toOppgavehistorikkDTO(rs));
        }

        return oppgaver;
    }

    @SneakyThrows
    public static OppgavehistorikkDTO toOppgavehistorikkDTO(ResultSet rs) {
        return new OppgavehistorikkDTO(
                rs.getString("TEMA"),
                rs.getString("TYPE"),
                rs.getTimestamp("OPPRETTET_TIDSPUNKT"),
                rs.getString("OPPRETTET_AV_IDENT"),
                rs.getString("GSAK_ID"),
                rs.getString("AKTOERID"),
                rs.getLong("ID")
        );
    }
}
