package no.nav.fo.veilarboppgave;

import no.nav.fo.veilarboppgave.rest.api.OppgaveDTO;

public class TestData {
    public static final String GYLDIG_FNR ="XXXXXXXXXXX";
    public static final String IKKE_GYLDIG_FNR ="00000000000";

    public static OppgaveDTO testData(String fnr) {
        return new OppgaveDTO(
                fnr,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        );

    }

}
