package no.nav.veilarboppgave.utils;

import no.bekk.bekkopen.person.Fodselsnummer;
import no.bekk.bekkopen.person.KJONN;
import no.nav.common.types.identer.Fnr;
import no.nav.veilarboppgave.domain.OppgaveDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static no.bekk.bekkopen.person.FodselsnummerCalculator.getFodselsnummerForDateAndGender;
import static no.bekk.bekkopen.person.KJONN.KVINNE;
import static no.bekk.bekkopen.person.KJONN.MANN;

public class TestData {

    public static OppgaveDTO oppgaveDTO(Fnr fnr) {
        return new OppgaveDTO(
                fnr.get(),
                "oppfolging",
                "",
                "vurder_konsekvens_for_ytelse",
                "norm",
                "beskrivelse",
                "2017-09-18",
                "2017-10-18",
                "1234",
                "Z1234",
                "0104"
        );

    }

    public static Fnr genererTilfeldigFnrMedTilgang() {
        return genererFnr(KVINNE);
    }

    public static Fnr genererTilfeldigFnrUtenTilgang() {
        return genererFnr(MANN);
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
}
