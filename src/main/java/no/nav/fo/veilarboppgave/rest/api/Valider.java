package no.nav.fo.veilarboppgave.rest.api;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.util.StringUtils;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.OppgaveType;
import no.nav.fo.veilarboppgave.domene.Prioritet;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static java.util.Optional.ofNullable;
import static no.bekk.bekkopen.person.FodselsnummerValidator.isValid;

public class Valider {

    public static Fnr fnr(String fnr) {
        if (isValid(fnr)) {
            return Fnr.of(fnr);
        }
        throw new UgyldigRequest();
    }

    public static Tema tema(String tema) {
        return ofNullable(tema)
                .map(Valider::atFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(Tema::contains)
                .map(Tema::valueOf)
                .orElseThrow(UgyldigRequest::new);
    }

    public static Prioritet prioritet(String prioritet) {
        return ofNullable(prioritet)
                .map(Valider::atFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(Prioritet::contains)
                .map(Prioritet::valueOf)
                .orElseThrow(UgyldigRequest::new);
    }

    public static OppgaveType oppgavetype(String oppgaveType) {
        return ofNullable(oppgaveType)
                .map(Valider::atFeltErUtfylt)
                .map(String::toUpperCase)
                .filter(OppgaveType::contains)
                .map(OppgaveType::valueOf)
                .orElseThrow(UgyldigRequest::new);
    }

    public static OppgaveDTO fraDatoErFoerTilDato(OppgaveDTO oppgaveDTO) {
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

    public static String atFeltErUtfylt(String enhet) {
        return StringUtils.of(enhet).orElseThrow(UgyldigRequest::new);
    }

    public static String beskrivelse(String beskrivelse) {
        return ofNullable(beskrivelse)
                .map(Valider::atFeltErUtfylt)
                .map(Valider::erIkkeOver250Tegn)
                .orElseThrow(UgyldigRequest::new);
    }

    private static String erIkkeOver250Tegn(String beskrivelse) {
        if (beskrivelse.length() > 250) {
            throw new UgyldigRequest();
        }
        return beskrivelse;
    }
}
