package no.nav.fo.veilarboppgave.rest.api;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.apiapp.feil.UgyldigRequest;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.Tema;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static no.bekk.bekkopen.person.FodselsnummerValidator.isValid;

public class Validering {

    public static Optional<String> of(String data) {
        return ofNullable(data);
    }

    public static Fnr erGyldigFnr(String fnr) {
        if (isValid(fnr)) {
            return Fnr.of(fnr);
        }
        throw new UgyldigRequest();
    }

    public static Tema erGyldigTema(String tema) {
        return Arrays.stream(Tema.values())
                .filter(value -> value.name().equals(tema.toUpperCase()))
                .findFirst()
                .orElseThrow(IngenTilgang::new);
    }
}
