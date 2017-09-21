package no.nav.fo.veilarboppgave.domene;

public enum Type {
    VURDER_KONSEKVENS_FOR_YTELSE("VUR_KONS_YTE"),
    VURDER_HENVENDELSE("VURD_HENV ");

    private String typeKode;

    Type(String typeKode) {
        this.typeKode = typeKode;
    }

    public String getKode() {
        return typeKode;
    }

    public static boolean contains(String value) {
        try {
            Type.valueOf(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
