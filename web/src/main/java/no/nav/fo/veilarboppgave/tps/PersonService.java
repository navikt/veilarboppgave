package no.nav.fo.veilarboppgave.tps;


import no.nav.tjeneste.virksomhet.person.v3.PersonV3;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;

public class PersonService {

    @Inject
    PersonV3 personSoapService;

    public List<Person> hentGeografiskTilknytting() {
        return emptyList();
    }
}
