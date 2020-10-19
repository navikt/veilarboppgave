package no.nav.veilarboppgave.rest.api.oppgave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OppgaveDTO {
    String fnr;
    String tema;
    String type;
    String prioritet;
    String beskrivelse;
    String fraDato;
    String tilDato;
    String enhetId;
    String veilederId;
    String avsenderenhetId;
}
