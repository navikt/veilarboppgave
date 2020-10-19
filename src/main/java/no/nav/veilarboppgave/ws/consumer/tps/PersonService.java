package no.nav.veilarboppgave.ws.consumer.tps;

import no.nav.veilarboppgave.domene.Fnr;
import no.nav.veilarboppgave.domene.GeografiskTilknytning;

import java.util.Optional;

public interface PersonService {
    Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr);
    boolean hentEgenAnsatt(Fnr fnr);
}
