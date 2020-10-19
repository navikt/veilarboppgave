package no.nav.veilarboppgave.ws.consumer.norg.arbeidsfordeling;

import no.nav.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.veilarboppgave.domene.TemaDTO;

import java.util.List;

public interface ArbeidsfordelingService {
    List<OppfolgingEnhet> hentBestMatchEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO tema, boolean egenAnsatt);
}
