package no.nav.fo.veilarboppgave.domene;

import java.util.Arrays;

public enum Tema {
    OPPFOLGING("OPP"),
    DAGPENGER("DAG"),
    ARBEIDSAVKLARING("AAP"),
    INDIVIDSTONAD("IND"),
    ENSLIGFORSORGER("ENF"),
    TILLEGSSTONAD("TSO");

    private String temaKode;

    Tema(String temaKode) {
        this.temaKode = temaKode;
    }

    public String getTemaKode() {
        return temaKode;
    }

    public static boolean contains(String value) {
        return Arrays
                .stream(Tema.values())
                .anyMatch(v -> v.name().equals(value));
    }
}
