package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.client.norg.organisasjonenhet.OrganisasjonEnhetService;

import java.util.Collections;
import java.util.List;

public class OrganisasjonEnhetServiceMock implements OrganisasjonEnhetService {
    @Override
    public List<OppfolgingEnhet> hentAlleEnheter() {
        return Collections.emptyList();
    }
}
