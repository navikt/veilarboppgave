package no.nav.fo.veilarboppgave.ws.consumer.tps;


import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.Fnr;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentGeografiskTilknytningPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentGeografiskTilknytningSikkerhetsbegrensing;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.NorskIdent;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.PersonIdent;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentGeografiskTilknytningRequest;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonV3 personSoapService;

    public PersonServiceImpl(PersonV3 personSoapService) {
        this.personSoapService = personSoapService;
    }

    @Override
    public Optional<GeografiskTilknytning> hentGeografiskTilknytning(Fnr fnr) {

        NorskIdent norskIdent = new NorskIdent();
        norskIdent.setIdent(fnr.getFnr());
        PersonIdent personIdent = new PersonIdent();
        personIdent.setIdent(norskIdent);

        HentGeografiskTilknytningRequest request = new HentGeografiskTilknytningRequest();
        request.setAktoer(personIdent);

        Optional<GeografiskTilknytning> maybeResponse = Optional.empty();
        try {
            String valueFromResponse = personSoapService
                    .hentGeografiskTilknytning(request)
                    .getGeografiskTilknytning()
                    .getGeografiskTilknytning();

            if (valueFromResponse != null) {
                return ofNullable(GeografiskTilknytning.of(valueFromResponse));
            }

        } catch (HentGeografiskTilknytningSikkerhetsbegrensing e) {
            log.warn("Kunne ikke hente geografisk tilknytning for fnr pga sikkerhetsbegrensning mot baktjeneste");
            throw new RuntimeException(e);
        } catch (HentGeografiskTilknytningPersonIkkeFunnet e) {
            log.info("Fant ikke geografisk tilknytning for fnr);");
            throw new NotFoundException(e);
        }

        return maybeResponse;
    }
}
