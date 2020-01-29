package no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling;

import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.TemaDTO;

import java.util.List;

public interface ArbeidsfordelingService {
    List<OppfolgingEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO gyldigTemaDTO);
    List<OppfolgingEnhet> hentBestMatchEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO tema, boolean egenAnsatt);
}
