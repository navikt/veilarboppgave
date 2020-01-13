package no.nav.fo.veilarboppgave.domene;

import java.util.Arrays;

public enum TemaDTO {
    OPPFOLGING("OPP"),
    DAGPENGER("DAG"),
    ARBEIDSAVKLARING("AAP"),
    INDIVIDSTONAD("IND"),
    ENSLIG_FORSORGER("ENF"),
    TILLEGGSTONAD("TSO");

    private String fagomradeKode;

    TemaDTO(String fagomradeKode) {
        this.fagomradeKode = fagomradeKode;
    }

    public String getFagomradeKode() {
        return fagomradeKode;
    }

    public static boolean contains(String value) {
        return Arrays
                .stream(TemaDTO.values())
                .anyMatch(v -> v.name().equals(value));
    }
}
