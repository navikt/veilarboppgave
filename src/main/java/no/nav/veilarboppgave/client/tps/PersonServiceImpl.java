package no.nav.veilarboppgave.client.tps;


import lombok.extern.slf4j.Slf4j;
import no.nav.veilarboppgave.domain.Fnr;
import no.nav.veilarboppgave.domain.GeografiskTilknytning;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentGeografiskTilknytningPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentGeografiskTilknytningSikkerhetsbegrensing;
import no.nav.tjeneste.virksomhet.person.v3.binding.PersonV3;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.NorskIdent;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.PersonIdent;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentGeografiskTilknytningRequest;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static no.nav.apiapp.util.UrlUtils.clusterUrlForApplication;
import static no.nav.apiapp.util.UrlUtils.joinPaths;
import static no.nav.veilarboppgave.config.ApplicationConfig.VEILARBPERSON_API_URL_PROPERTY;
import static no.nav.sbl.util.EnvironmentUtils.getOptionalProperty;

@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonV3 personSoapService;
    private final Client restClient;
    private final String host;

    public PersonServiceImpl(PersonV3 personSoapService, Client restClient) {
        this.personSoapService = personSoapService;
        this.restClient = restClient;
        host = getOptionalProperty(VEILARBPERSON_API_URL_PROPERTY)
                .orElseGet(() ->
                        joinPaths(clusterUrlForApplication("veilarbperson"), "/veilarbperson/api"));
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

    @Override
    public boolean hentEgenAnsatt(Fnr fnr) {
        PersonResponse response = restClient
                .target(joinPaths(host, "person", fnr.getFnr()))
                .request()
                .get(PersonResponse.class);

        return response.egenAnsatt;
    }
}

class PersonResponse {
    boolean egenAnsatt;
}
