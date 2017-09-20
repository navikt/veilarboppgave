package no.nav.fo.veilarboppgave;

import no.bekk.bekkopen.person.Fodselsnummer;
import no.bekk.bekkopen.person.KJONN;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import static no.bekk.bekkopen.person.FodselsnummerCalculator.getFodselsnummerForDate;
import static no.bekk.bekkopen.person.FodselsnummerCalculator.getFodselsnummerForDateAndGender;
import static no.bekk.bekkopen.person.KJONN.KVINNE;
import static no.bekk.bekkopen.person.KJONN.MANN;
import static no.nav.fo.veilarboppgave.TestData.FeltNavn.*;

public class TestData {

    public static final Fnr IKKE_GYLDIG_FNR = Fnr.of("00000000000");

    public class FeltNavn {
        public static final String ANSVARLIG_ID = "ansvarligId";
        public static final String PRIORITETKODE = "prioritetKode";
        public static final String ANSVARLIGENHETID = "ansvarligEnhetId";
        public static final String FNR = "fnr";
        public static final String FAGOMRADEKODE = "fagomradeKode";
        public static final String OPPGAVETYPEKODE = "oppgavetypeKode";
        public static final String BESKRIVELSE = "beskrivelse";
        public static final String AKTIVFRA = "aktivFra";
        public static final String AKTIVTIL = "aktivTil";
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

    public static JSONObject json() {
        return new JSONObject()
                .put(FNR, genererTilfeldigFnrMedTilgang().getFnr())
                .put(FAGOMRADEKODE, Tema.OPPFOLGING.name().toLowerCase())
                .put(OPPGAVETYPEKODE, "konsekvens_for_ytelse")
                .put(PRIORITETKODE, "lav")
                .put(BESKRIVELSE, "Dette er en testbeskrivelse")
                .put(AKTIVFRA, "2017-09-19")
                .put(AKTIVTIL, "2018-09-19")
                .put(ANSVARLIGENHETID, "0000")
                .put(ANSVARLIG_ID, "X000000");
    }

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
