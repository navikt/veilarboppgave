package no.nav.fo.veilarboppgave.rest.api.oppgave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OppgaveDTO {
    String fnr;
    String fagomradeKode;
    String oppgavetypeKode;
    String prioritetKode;
    String beskrivelse;
    String aktivFra;
    String aktivTil;
    String ansvarligEnhetId;
    String ansvarligId;
}
