package no.nav.fo.veilarboppgave;

import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;

public class TestData {
    public static final Fnr GYLDIG_FNR = Fnr.of("XXXXXXXXXXX");
    public static final Fnr IKKE_GYLDIG_FNR = Fnr.of("00000000000");
    public static final Fnr IKKE_AUTORISERT_FNR = Fnr.of("XXXXXXXXXXX");

    public static OppgaveDTO testData(Fnr fnr) {
        return new OppgaveDTO(
                fnr.getFnr(),
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
