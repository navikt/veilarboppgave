package no.nav.fo.veilarboppgave.mocks;

import no.bekk.bekkopen.person.FodselsnummerValidator;
import no.nav.apiapp.feil.IngenTilgang;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.security.abac.PepClient;

public class PepClientMock implements PepClient {
    @Override
    public Fnr sjekkTilgangTilFnr(Fnr fnr) {
        boolean isMale = FodselsnummerValidator.getFodselsnummer(fnr.getFnr()).isMale();
        if (isMale) {
            throw new IngenTilgang();
        }
        return fnr;
    }
}
