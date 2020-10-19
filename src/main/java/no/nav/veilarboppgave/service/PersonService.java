package no.nav.veilarboppgave.service;

import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.GeografiskTilknytning;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonService {

    public Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr) {
        return Optional.empty();
    }

    public boolean erEgenAnsatt(Fnr fnr) {
        return true;
    }

}
