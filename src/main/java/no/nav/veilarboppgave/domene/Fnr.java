package no.nav.veilarboppgave.domene;

import lombok.Value;

@Value(staticConstructor = "of")
public class Fnr {
    String fnr;
}
