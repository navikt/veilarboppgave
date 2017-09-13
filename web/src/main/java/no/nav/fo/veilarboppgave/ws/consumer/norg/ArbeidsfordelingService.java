package no.nav.fo.veilarboppgave.ws.consumer.norg;

import no.nav.fo.veilarboppgave.domene.Enhet;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;

import java.util.List;

public interface ArbeidsfordelingService {
    List<Enhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning);
}
