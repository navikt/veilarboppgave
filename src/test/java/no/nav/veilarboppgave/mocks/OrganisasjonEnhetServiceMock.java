package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.veilarboppgave.ws.consumer.norg.organisasjonenhet.OrganisasjonEnhetService;

import java.util.Collections;
import java.util.List;

public class OrganisasjonEnhetServiceMock implements OrganisasjonEnhetService {
    @Override
    public List<OppfolgingEnhet> hentAlleEnheter() {
        return Collections.emptyList();
    }
}
