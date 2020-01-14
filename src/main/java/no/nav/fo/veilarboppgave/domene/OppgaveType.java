package no.nav.fo.veilarboppgave.domene;

import java.util.Arrays;

public enum OppgaveType {
    VURDER_KONSEKVENS_FOR_YTELSE("VUR_KONS_YTE"),
    VURDER_HENVENDELSE("VURD_HENV");

    private String typeKode;

    OppgaveType(String typeKode) {
        this.typeKode = typeKode;
    }

    public String getKode() {
        return typeKode;
    }

    public static boolean contains(String value) {
        return Arrays
                .stream(OppgaveType.values())
                .anyMatch(v -> v.name().equals(value));
    }

    public static String utledOppgaveTypeKode(TemaDTO temaDTO, OppgaveType oppgaveType) {
        return oppgaveType.getKode() + "_" + temaDTO.getFagomradeKode();
    }
}
