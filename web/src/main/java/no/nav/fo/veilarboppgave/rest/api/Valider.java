package no.nav.fo.veilarboppgave.rest.api;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.util.StringUtils;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Prioritet;
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

    public static Prioritet prioritet(String prioritet) {
        return Arrays.stream(Prioritet.values())
                .filter(value -> value.name().equals(prioritet.toUpperCase()))
                .findFirst()
                .orElseThrow(UgyldigRequest::new);
    }

    public static OppgaveDTO fraTilDato(OppgaveDTO oppgaveDTO) {
        LocalDate fra = Valider.dato(oppgaveDTO.getFraDato());
        LocalDate til = Valider.dato(oppgaveDTO.getTilDato());

        if (fra.isBefore(til.plusDays(1))) {
            return oppgaveDTO;
        } else {
            throw new UgyldigRequest();
        }
    }

    public static LocalDate dato(String fraDato) {
        try {
            return LocalDate.parse(fraDato);
        } catch (DateTimeParseException e) {
            throw new UgyldigRequest();
        }
    }

    public static void obligatoriskeFelter(OppgaveDTO dto) {
        StringUtils.of(dto.getTilDato()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getFraDato()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getBeskrivelse()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getEnhet()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getTema()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getType()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getPrioritet()).orElseThrow(UgyldigRequest::new);
        StringUtils.of(dto.getFnr()).orElseThrow(UgyldigRequest::new);
    }
}
