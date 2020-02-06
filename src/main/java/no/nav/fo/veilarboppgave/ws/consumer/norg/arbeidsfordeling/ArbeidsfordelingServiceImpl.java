package no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling;

import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.TemaDTO;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.client.Entity.json;
import static no.nav.apiapp.util.UrlUtils.joinPaths;
import static no.nav.fo.veilarboppgave.config.ApplicationConfig.NORG2_API_URL_PROPERTY;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

@Slf4j
public class ArbeidsfordelingServiceImpl implements ArbeidsfordelingService {

    private final Client restClient;
    private final String host;

    public ArbeidsfordelingServiceImpl(Client restClient) {
        this.restClient = restClient;
        this.host = getRequiredProperty(NORG2_API_URL_PROPERTY);
    }

    @Override
    public List<OppfolgingEnhet> hentBestMatchEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO tema, boolean egenAnsatt) {
        ArbeidsfordelingKriterier kriterier = new ArbeidsfordelingKriterier();
        kriterier.skjermet = egenAnsatt;
        kriterier.tema = tema.getFagomradeKode();
        kriterier.geografiskOmraade = geografiskTilknytning.getGeofrafiskTilknytning();

        Response response = restClient
                .target(joinPaths(host, "v1/arbeidsfordeling/enheter/bestmatch"))
                .request()
                .post(json(kriterier));

        if (response.getStatus() != 200) {
            log.error("Kunne ikke hente enheter fra arbeidsfordeling. Response status = " + response.getStatus());
            return emptyList();
        } else {
            List<ArbeidsfordelingEnhet> arbeidsfordelingEnheter = response.readEntity(new GenericType<List<ArbeidsfordelingEnhet>>() {
            });
            return arbeidsfordelingEnheter.stream().map(OppfolgingEnhet::of).collect(toList());
        }

    }
}
