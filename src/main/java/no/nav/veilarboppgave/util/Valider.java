package no.nav.veilarboppgave.util;

import no.nav.common.utils.StringUtils;
import no.nav.veilarboppgave.domain.OppgaveType;
import no.nav.veilarboppgave.domain.Prioritet;
import no.nav.veilarboppgave.domain.TemaDTO;
import no.nav.veilarboppgave.domain.OppgaveDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static java.util.Optional.ofNullable;

public class Valider {

    public static void validerOppgaveDto(OppgaveDTO oppgaveDto) {
        validerAtFeltErUtfylt(oppgaveDto.getAvsenderenhetId());
        validerAtFeltErUtfylt(oppgaveDto.getEnhetId());

        validerTema(oppgaveDto.getTema());
        validerPrioritet(oppgaveDto.getPrioritet());

        validerFraDatoErForTilDato(oppgaveDto.getFraDato(), oppgaveDto.getTilDato());

        validerOppgaveType(oppgaveDto.getType());
        validerBeskrivelse(oppgaveDto.getBeskrivelse());
    }

    public static void validerTema(String tema) {
        ofNullable(tema)
                .map(Valider::validerAtFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(TemaDTO::contains)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema er ugyldig"));
    }

    private static void validerPrioritet(String prioritet) {
        ofNullable(prioritet)
                .map(Valider::validerAtFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(Prioritet::contains)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prioritet er ugyldig"));
    }

    public static void validerOppgaveType(String oppgaveType) {
         ofNullable(oppgaveType)
                .map(Valider::validerAtFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(OppgaveType::contains)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgavetype er ugyldig"));
    }

    public static void validerFraDatoErForTilDato(String fraDato, String tilDato) {
        LocalDate til = DateUtils.tilDato(tilDato);
        LocalDate fra = DateUtils.tilDato(fraDato);

        if (til.isBefore(fra)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oppgave dato er ugyldig");
        }
    }

    public static String validerAtFeltErUtfylt(String felt) {
        return StringUtils.of(felt).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Felt er ikke utfylt"));
    }

    public static void validerBeskrivelse(String beskrivelse) {
        ofNullable(beskrivelse)
                .map(Valider::validerAtFeltErUtfylt)
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
