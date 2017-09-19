package no.nav.fo.veilarboppgave.rest.api;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import static no.bekk.bekkopen.person.FodselsnummerValidator.isValid;

public class Valider {

    public static Fnr fnr(String fnr) {
        if (isValid(fnr)) {
            return Fnr.of(fnr);
        }
        throw new UgyldigRequest();
    }

    public static Tema tema(String tema) {
        return Arrays.stream(Tema.values())
                .filter(value -> value.name().equals(tema.toUpperCase()))
                .findFirst()
                .orElseThrow(UgyldigRequest::new);
    }

    public static OppgaveDTO fraTilDato(OppgaveDTO oppgaveDTO) {
        LocalDate fra = Valider.dato(oppgaveDTO.getAktivFra());
        LocalDate til = Valider.dato(oppgaveDTO.getAktivTil());

        if (fra.isBefore(til.plusDays(1))) {
            return oppgaveDTO;
        } else {
            throw new UgyldigRequest();
        }
    }

    public static LocalDate dato(String aktivFra) {
        try {
            return LocalDate.parse(aktivFra);
        } catch (DateTimeParseException e) {
            throw new UgyldigRequest();
        }
    }
}
