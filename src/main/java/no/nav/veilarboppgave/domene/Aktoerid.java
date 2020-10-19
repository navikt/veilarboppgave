package no.nav.veilarboppgave.domene;

import lombok.Value;

@Value(staticConstructor = "of")
public class Aktoerid {
    String aktoerid;
}
