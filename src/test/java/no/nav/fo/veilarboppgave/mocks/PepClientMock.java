package no.nav.fo.veilarboppgave.mocks;

import no.nav.apiapp.feil.IngenTilgang;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.security.abac.PepClient;

import static no.bekk.bekkopen.person.FodselsnummerValidator.getFodselsnummer;

public class PepClientMock implements PepClient {
    @Override
    public Fnr sjekkTilgangTilFnr(Fnr fnr) {
        boolean youShallNotPass = getFodselsnummer(fnr.getFnr()).isMale();
        if (youShallNotPass) {
            throw new IngenTilgang();
        }
        return fnr;
    }
}

