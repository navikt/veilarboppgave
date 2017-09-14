package no.nav.fo.veilarboppgave.rest.api;

import no.nav.apiapp.feil.UgyldigRequest;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static no.bekk.bekkopen.person.FodselsnummerValidator.isValid;

public class Validering {

    public static Optional<String> of(String data) {
        return ofNullable(data);
    }

    public static String erGyldigFnr(String fnr) {
        if (isValid(fnr)) {
            return fnr;
        }
        throw new UgyldigRequest();
    }
}
