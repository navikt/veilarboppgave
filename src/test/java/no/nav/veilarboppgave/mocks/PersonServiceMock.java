package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.GeografiskTilknytning;
import no.nav.veilarboppgave.client.tps.PersonService;

import java.util.Optional;

public class PersonServiceMock implements PersonService {
    @Override
    public Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr) {
        return Optional.of(GeografiskTilknytning.of("Trøgstad"));
    }

    @Override
    public boolean hentEgenAnsatt(Fnr fnr) {
        return false;
    }
}
