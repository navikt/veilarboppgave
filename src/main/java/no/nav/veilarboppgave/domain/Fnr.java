package no.nav.veilarboppgave.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Fnr {
    String fnr;
}
