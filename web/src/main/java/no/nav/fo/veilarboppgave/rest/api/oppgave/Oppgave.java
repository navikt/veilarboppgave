package no.nav.fo.veilarboppgave.rest.api.oppgave;

import lombok.Value;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.OppgaveType;
import no.nav.fo.veilarboppgave.domene.Tema;

import java.time.LocalDate;

@Value
public class Oppgave {
    Fnr fnr;
    Tema tema;
    OppgaveType type;
    String prioritet;
    String beskrivelse;
    LocalDate fraDato;
    LocalDate tilDato;
    String enhetId;
    String veilederId;
    String avsenderenhetId;
}
