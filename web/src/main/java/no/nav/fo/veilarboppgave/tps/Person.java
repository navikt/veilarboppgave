package no.nav.fo.veilarboppgave.tps;

import lombok.Value;
import lombok.experimental.Wither;

@Value(staticConstructor = "of")
@Wither
public class Person {
    String fnr;
}
