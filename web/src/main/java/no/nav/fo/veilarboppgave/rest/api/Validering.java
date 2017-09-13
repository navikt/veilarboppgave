package no.nav.fo.veilarboppgave.rest.api;

import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.apiapp.security.PepClient;

import javax.inject.Inject;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static no.bekk.bekkopen.person.FodselsnummerValidator.isValid;

public class Validering {

    @Inject
    private static PepClient pepClient;

    public static Optional<String> of(String data) {
        return ofNullable(data);
    }

    public static String sjekkTilgangTilBruker(String fnr) {
        pepClient.sjekkTilgangTilFnr(fnr);
        return fnr;
    }

    public static String erGyldigFnr(String fnr) {
        if (isValid(fnr)) {
            return fnr;
        }
        throw new UgyldigRequest();
    }
}
