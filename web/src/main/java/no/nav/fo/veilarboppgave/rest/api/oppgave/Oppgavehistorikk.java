package no.nav.fo.veilarboppgave.rest.api.oppgave;

import lombok.Value;
import lombok.experimental.Wither;
import no.nav.fo.veilarboppgave.db.OppgavehistorikkDTO;

import java.sql.Timestamp;

@Value(staticConstructor = "of")
@Wither
public class Oppgavehistorikk {
    private final String type;
    private final String oppgaveTema;
    private final String oppgaveType;
    private final String opprettetAv;
    private final String opprettetAvBrukerId;
    private final Timestamp dato;

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
