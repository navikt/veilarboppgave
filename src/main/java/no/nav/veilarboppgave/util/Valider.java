package no.nav.veilarboppgave.util;

import no.nav.common.utils.StringUtils;
import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.OppgaveType;
import no.nav.veilarboppgave.domain.Prioritet;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.domain.OppgaveDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.util.Optional.ofNullable;

public class Valider {

    public static TemaDTO tema(String tema) {
        return ofNullable(tema)
                .map(Valider::atFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(TemaDTO::contains)
                .map(TemaDTO::valueOf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema er ugyldig"));
    }

    public static Prioritet prioritet(String prioritet) {
        return ofNullable(prioritet)
                .map(Valider::atFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(Prioritet::contains)
                .map(Prioritet::valueOf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prioritet er ugyldig"));
    }

    public static OppgaveType oppgavetype(String oppgaveType) {
        return ofNullable(oppgaveType)
                .map(Valider::atFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(OppgaveType::contains)
                .map(OppgaveType::valueOf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgavetype er ugyldig"));
    }

    public static OppgaveDTO fraDatoErFoerTilDato(OppgaveDTO oppgaveDTO) {
        LocalDate fra = Valider.dato(oppgaveDTO.getFraDato());
        LocalDate til = Valider.dato(oppgaveDTO.getTilDato());

        if (fra.isBefore(til.plusDays(1))) {
            return oppgaveDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgave dato er ugyldig");
        }
    }

    public static LocalDate dato(String fraDato) {
        try {
            return LocalDate.parse(fraDato);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dato er ugyldig");
        }
    }

    public static String atFeltErUtfylt(String enhet) {
        return StringUtils.of(enhet).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enhet mangler"));
    }

    public static String beskrivelse(String beskrivelse) {
        return ofNullable(beskrivelse)
                .map(Valider::atFeltErUtfylt)
                .map(Valider::erIkkeOver250Tegn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beskrivelse er ugyldig"));
    }

    private static String erIkkeOver250Tegn(String beskrivelse) {
        if (beskrivelse.length() > 250) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beskrivelse må være 250 tegn eller mindre");
        }
        return beskrivelse;
    }
}
