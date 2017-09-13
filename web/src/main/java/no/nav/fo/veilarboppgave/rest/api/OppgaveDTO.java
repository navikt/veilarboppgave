package no.nav.fo.veilarboppgave.rest.api;

import lombok.Value;

@Value
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
