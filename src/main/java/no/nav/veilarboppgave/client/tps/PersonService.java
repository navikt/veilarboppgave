package no.nav.veilarboppgave.client.tps;

import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.GeografiskTilknytning;

import java.util.Optional;

public interface PersonService {
    Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr);
    boolean hentEgenAnsatt(Fnr fnr);
}
