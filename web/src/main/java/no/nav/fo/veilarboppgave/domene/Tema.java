package no.nav.fo.veilarboppgave.domene;

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
}
