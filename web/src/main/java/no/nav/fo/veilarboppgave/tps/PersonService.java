package no.nav.fo.veilarboppgave.tps;

import no.nav.fo.veilarboppgave.domene.Fnr;

import java.util.Optional;

public interface PersonService {
    Optional<String> hentGeografiskTilknytning(Fnr fnr);
}
