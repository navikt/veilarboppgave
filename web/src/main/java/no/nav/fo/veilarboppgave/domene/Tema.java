package no.nav.fo.veilarboppgave.domene;

import java.util.Arrays;

public enum Tema {
    OPPFOLGING("OPP"),
    DAGPENGER("DAG"),
    ARBEIDSAVKLARING("AAP"),
    INDIVIDSTONAD("IND"),
    ENSLIGFORSORGER("ENF"),
    TILLEGSSTONAD("TSO");

    private String fagomradeKode;

    Tema(String fagomradeKode) {
        this.fagomradeKode = fagomradeKode;
    }

    public String getFagomradeKode() {
        return fagomradeKode;
    }

    public static boolean contains(String value) {
        return Arrays
                .stream(Tema.values())
                .anyMatch(v -> v.name().equals(value));
    }
}
