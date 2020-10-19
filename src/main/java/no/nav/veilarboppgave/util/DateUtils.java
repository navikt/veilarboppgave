package no.nav.veilarboppgave.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;

public class DateUtils {

    public static LocalDate tilDato(String datoStr) {
        try {
            return LocalDate.parse(datoStr);
        } catch (DateTimeParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, format("Dato %s er ugyldig", datoStr));
        }
    }

}
