package no.nav.fo.veilarboppgave.domene;

import java.util.Arrays;

public enum Prioritet {
    LAV,
    HOY;

    public static boolean contains(String value) {
        return Arrays
                .stream(Prioritet.values())
                .anyMatch(v -> v.name().equals(value));
    }
}
