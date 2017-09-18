package no.nav.fo.veilarboppgave;

import no.bekk.bekkopen.person.Fodselsnummer;
import no.bekk.bekkopen.person.KJONN;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static no.bekk.bekkopen.person.FodselsnummerCalculator.getFodselsnummerForDate;
import static no.bekk.bekkopen.person.FodselsnummerCalculator.getFodselsnummerForDateAndGender;
import static no.bekk.bekkopen.person.KJONN.KVINNE;
import static no.bekk.bekkopen.person.KJONN.MANN;

public class TestData {

    public static final Fnr IKKE_GYLDIG_FNR = Fnr.of("00000000000");

    public static Fnr genererTilfeldigFnr() {
        Fodselsnummer random = getFodselsnummerForDate(randomDate());
        return Fnr.of(random.toString());
    }

    public static Fnr genererTilfeldigFnrMedTilgang() {
        return genererFnr(KVINNE);
    }

    public static Fnr genererTilfeldigFnrUtenTilgang() {
        return genererFnr(MANN);
    }

    public static OppgaveDTO oppgaveDTO(Fnr fnr) {
        return new OppgaveDTO(
                fnr.getFnr(),
                "",
                "",
                "",
                "",
                "2017-09-18",
                "2017-10-18",
                "",
                ""
        );

    }

    public static OppgaveDTO oppgaveDTO(String fraDato, String tilDato) {
        return new OppgaveDTO(
                genererTilfeldigFnrMedTilgang().getFnr(),
                "",
                "",
                "",
                "",
                fraDato,
                tilDato,
                "",
                ""
        );

    }

    private static Fnr genererFnr(KJONN kjonn) {
        Date randomDate = randomDate();
        return getFodselsnummerForDateAndGender(randomDate, kjonn).stream()
                .findFirst()
                .map(Fodselsnummer::toString)
                .map(Fnr::of)
                .orElseThrow(RuntimeException::new);
    }

    private static Date randomDate() {
        int minDay = (int) LocalDate.of(1900, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2017, 1, 1).toEpochDay();
        long randomDay = minDay + new Random().nextInt(maxDay - minDay);
        return new Date(randomDay);
    }

    public static void main(String[] args) {
        System.out.println(genererFnr(KVINNE));
    }
}
