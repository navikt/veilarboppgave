package no.nav.veilarboppgave.domain;

import lombok.Value;
import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.TemaDTO;

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
