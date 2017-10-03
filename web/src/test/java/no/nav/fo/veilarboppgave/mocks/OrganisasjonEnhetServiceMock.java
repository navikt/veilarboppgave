package no.nav.fo.veilarboppgave.mocks;

import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetService;

import java.util.List;

public class OrganisasjonEnhetServiceMock implements OrganisasjonEnhetService {
    @Override
    public List<OppfolgingEnhet> hentAlleEnheter() {
        return null;
    }
}
