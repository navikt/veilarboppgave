package no.nav.fo.veilarboppgave.ws.consumer.tps;

import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;

import java.util.Optional;

public interface PersonService {
    Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr);
    boolean hentEgenAnsatt(Fnr fnr);
}
