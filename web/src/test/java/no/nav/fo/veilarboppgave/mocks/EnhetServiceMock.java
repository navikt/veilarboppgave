package no.nav.fo.veilarboppgave.mocks;

import no.nav.fo.veilarboppgave.ws.consumer.norg.enhet.EnhetService;

public class EnhetServiceMock implements EnhetService {

    @Override
    public boolean harTilgangTilEnhet(String enhetId) {
        return true;
    }
}
