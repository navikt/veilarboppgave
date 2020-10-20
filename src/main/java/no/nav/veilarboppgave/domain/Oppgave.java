package no.nav.veilarboppgave.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import no.nav.common.types.identer.AktorId;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class Oppgave {
    AktorId aktorId;
    TemaDTO temaDTO;
    OppgaveType type;
    Prioritet prioritet;
    String beskrivelse;
    LocalDate fraDato;
    LocalDate tilDato;
    String enhetId;
    String veilederId;
    String avsenderenhetId;
}
