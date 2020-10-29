package no.nav.veilarboppgave.util;

import no.nav.veilarboppgave.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public class Valider {

    public static void validerOppgaveDto(OppgaveDTO oppgaveDto) {
        // Avsenderenhet er ikke påkrevd, men må være gyldig hvis det blir sendt med
        if (oppgaveDto.getAvsenderenhetId() != null) {
            validerEnhetId(oppgaveDto.getAvsenderenhetId());
        }

        validerEnhetId(oppgaveDto.getEnhetId());

        validerTema(oppgaveDto.getTema());
        validerPrioritet(oppgaveDto.getPrioritet());

        validerFraDatoErForTilDato(oppgaveDto.getFraDato(), oppgaveDto.getTilDato());

        validerOppgaveType(oppgaveDto.getType());
        validerBeskrivelse(oppgaveDto.getBeskrivelse());
    }

    public static void validerTema(String tema) {
        if (tema == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema mangler");
        }

        if (!TemaDTO.contains(tema.toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema er ugyldig: " + tema);
        }
    }

    private static void validerPrioritet(String prioritet) {
        if (prioritet == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prioritet mangler");
        }

        if (!Prioritet.contains(prioritet.toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prioritet er ugyldig: " + prioritet);
        }
    }

    public static void validerOppgaveType(String oppgaveType) {
        if (oppgaveType == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgavetype mangler");
        }

        if (!OppgaveType.contains(oppgaveType.toUpperCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgavetype er ugyldig: " + oppgaveType);
        }
    }

    public static void validerFraDatoErForTilDato(String fraDato, String tilDato) {
        LocalDate fra = DateUtils.tilDato(fraDato);
        LocalDate til = DateUtils.tilDato(tilDato);

        // "til"-dato må være lik eller større "fra"-dato
        if (til.isBefore(fra)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgave dato er ugyldig");
        }
    }

    public static void validerEnhetId(String enhetId) {
        if (enhetId == null || enhetId.length() != 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enhet ID er ugyldig: " + enhetId);
        }
    }

    public static void validerBeskrivelse(String beskrivelse) {
        if (beskrivelse == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beskrivelse mangler");
        }

        if (beskrivelse.length() > 250) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Beskrivelse må være 250 tegn eller mindre");
        }
    }

}
