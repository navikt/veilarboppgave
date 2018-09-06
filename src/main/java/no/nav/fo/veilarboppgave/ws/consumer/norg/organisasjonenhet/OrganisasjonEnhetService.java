package no.nav.fo.veilarboppgave.ws.consumer.norg.organisasjonenhet;

import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;

import java.util.List;

public interface OrganisasjonEnhetService {
    public List<OppfolgingEnhet> hentAlleEnheter();
}
