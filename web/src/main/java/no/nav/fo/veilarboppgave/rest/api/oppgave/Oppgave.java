package no.nav.fo.veilarboppgave.rest.api.oppgave;

import lombok.Value;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Prioritet;
import no.nav.fo.veilarboppgave.domene.Tema;

import java.time.LocalDate;

@Value
public class Oppgave {
    Fnr fnr;
    Tema tema;
    String type;
    Prioritet prioritet;
    String beskrivelse;
    LocalDate fraDato;
    LocalDate tilDato;
    String enhet;
    String veileder;
}
