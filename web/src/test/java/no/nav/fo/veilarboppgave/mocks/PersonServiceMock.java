package no.nav.fo.veilarboppgave.mocks;

import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.ws.consumer.tps.PersonService;

import java.util.Optional;

public class PersonServiceMock implements PersonService {
    @Override
    public Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr) {
        return Optional.of(GeografiskTilknytning.of("Trøgstad"));
    }
}
