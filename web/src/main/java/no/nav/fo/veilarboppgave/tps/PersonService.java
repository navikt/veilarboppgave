package no.nav.fo.veilarboppgave.tps;


import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.tjeneste.virksomhet.person.v3.HentGeografiskTilknytningPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.HentGeografiskTilknytningSikkerhetsbegrensing;
import no.nav.tjeneste.virksomhet.person.v3.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.WSNorskIdent;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.WSPersonIdent;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.WSHentGeografiskTilknytningRequest;

import javax.inject.Inject;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
public class PersonService {

    @Inject
    PersonV3 personSoapService;

    public Optional<String> hentGeografiskTilknytning(Fnr fnr) {

        WSNorskIdent norskIdent = new WSNorskIdent();
        norskIdent.setIdent(fnr.getFnr());
        WSPersonIdent personIdent = new WSPersonIdent();
        personIdent.setIdent(norskIdent);

        WSHentGeografiskTilknytningRequest request = new WSHentGeografiskTilknytningRequest();
        request.setAktoer(personIdent);

        Optional<String> maybeResponse = Optional.empty();
        try {
            maybeResponse = ofNullable(personSoapService.hentGeografiskTilknytning(request).getGeografiskTilknytning().getGeografiskTilknytning());
        } catch (HentGeografiskTilknytningSikkerhetsbegrensing | HentGeografiskTilknytningPersonIkkeFunnet e) {
            log.warn(String.format("Kunne ikke hente geografisk tilknytning for fnr %s : %s", fnr, e.getMessage()));
        }

        return maybeResponse;
    }
}
