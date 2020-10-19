package no.nav.veilarboppgave.rest.api.oppgave;

import lombok.Value;
import no.nav.veilarboppgave.domene.Fnr;
import no.nav.veilarboppgave.domene.TemaDTO;

import java.time.LocalDate;

@Value
public class Oppgave {
    Fnr fnr;
    TemaDTO temaDTO;
    String type;
    String prioritet;
    String beskrivelse;
    LocalDate fraDato;
    LocalDate tilDato;
    String enhetId;
    String veilederId;
    String avsenderenhetId;
}
