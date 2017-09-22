package no.nav.fo.veilarboppgave.domene;

import java.util.Arrays;

public enum  Prioritet {
    LAV,
    NORM,
    HOY;

    public static boolean contains(String value) {
        return Arrays
                .stream(Prioritet.values())
                .anyMatch(v -> v.name().equals(value));
    }

    public static String utledPrioritetKode(Tema tema, Prioritet prioritet) {
        return prioritet.name() + "_" + tema.getTemaKode();
    }

}
