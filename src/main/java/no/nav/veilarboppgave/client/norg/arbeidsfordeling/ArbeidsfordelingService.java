package no.nav.veilarboppgave.client.norg.arbeidsfordeling;

import no.nav.veilarboppgave.domain.GeografiskTilknytning;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.domain.TemaDTO;

import java.util.List;

public interface ArbeidsfordelingService {
    List<OppfolgingEnhet> hentBestMatchEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO tema, boolean egenAnsatt);
}
