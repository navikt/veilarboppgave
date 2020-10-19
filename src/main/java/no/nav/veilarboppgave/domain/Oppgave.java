package no.nav.veilarboppgave.domain;

import lombok.Value;
import no.nav.common.types.identer.Fnr;

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