package no.nav.veilarboppgave.ws.consumer.norg.organisasjonenhet;

import no.nav.veilarboppgave.domene.OppfolgingEnhet;

import java.util.List;

public interface OrganisasjonEnhetService {
    public List<OppfolgingEnhet> hentAlleEnheter();
}
