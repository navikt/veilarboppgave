package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domene.Fnr;
import no.nav.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.veilarboppgave.ws.consumer.tps.PersonService;

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
