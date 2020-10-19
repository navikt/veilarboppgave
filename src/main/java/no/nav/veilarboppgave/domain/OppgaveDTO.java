package no.nav.veilarboppgave.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OppgaveDTO {
    String fnr;
    String tema;
    String type;
    String prioritet;
    String beskrivelse;
    String  fraDato;
    String tilDato;
    String enhetId;
    String veilederId;
    String avsenderenhetId;
}
