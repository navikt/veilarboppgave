package no.nav.veilarboppgave.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OppgavehistorikkDTO {
    private Long ID = null;
    private final String tema;
    private final String type;
    private final Timestamp opprettetDato;
    private final String opprettetAv;
    private final String gsakID;
    private final String aktoerid;

    public OppgavehistorikkDTO(
            String tema, String type, Timestamp opprettetDato, String opprettetAv,
            String gsakID, String aktoerid
    ) {
        this.tema = tema;
        this.type = type;
        this.opprettetDato = opprettetDato;
        this.opprettetAv = opprettetAv;
        this.gsakID = gsakID;
        this.aktoerid = aktoerid;
    }

    public OppgavehistorikkDTO(
            String tema, String type, Timestamp opprettetDato,
            String opprettetAv, String gsakID, String aktoerid, Long id
    ) {
        this.tema = tema;
        this.type = type;
        this.opprettetDato = opprettetDato;
        this.opprettetAv = opprettetAv;
        this.gsakID = gsakID;
        this.aktoerid = aktoerid;
        this.ID = id;
    }

}
