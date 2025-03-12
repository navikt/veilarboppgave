package no.nav.veilarboppgave.domain;

import lombok.Value;
import lombok.With;

import java.sql.Timestamp;

@With
@Value(staticConstructor = "of")
public class Oppgavehistorikk {
    String type;
    String oppgaveTema;
    String oppgaveType;
    String opprettetAv;
    String opprettetAvBrukerId;
    Timestamp dato;

    public static Oppgavehistorikk of(OppgavehistorikkDTO dto) {
        return Oppgavehistorikk.of(
                "OPPRETTET_OPPGAVE",
                dto.getTema(),
                dto.getType(),
                "NAV",
                dto.getOpprettetAv(),
                dto.getOpprettetDato()
        );
    }
}
