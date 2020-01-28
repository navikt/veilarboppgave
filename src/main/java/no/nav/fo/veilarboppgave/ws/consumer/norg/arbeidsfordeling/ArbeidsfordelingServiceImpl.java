package no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling;

import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.TemaDTO;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.binding.ArbeidsfordelingV1;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.binding.FinnBehandlendeEnhetListeUgyldigInput;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.Geografi;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.Organisasjonsenhet;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.Tema;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.FinnBehandlendeEnhetListeRequest;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.FinnBehandlendeEnhetListeResponse;

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

    private final ArbeidsfordelingV1 arbeidsfordelingSoapTjeneste;
    private final Client restClient;
    private final String host;

    public ArbeidsfordelingServiceImpl(ArbeidsfordelingV1 arbeidsfordelingV1, Client restClient) {
        this.arbeidsfordelingSoapTjeneste = arbeidsfordelingV1;
        this.restClient = restClient;
        this.host = getRequiredProperty(NORG2_API_URL_PROPERTY);
    }

    @Override
    public List<OppfolgingEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO temaDTO) {
        try {
            Geografi geografi = new Geografi();
            geografi.setKodeverksRef(geografiskTilknytning.getGeofrafiskTilknytning());

            no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.ArbeidsfordelingKriterier arbeidsfordelingKriterier =
                    new no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.ArbeidsfordelingKriterier();
            arbeidsfordelingKriterier.setGeografiskTilknytning(geografi);

            Tema tema = new Tema();
            tema.setKodeverksRef(temaDTO.getFagomradeKode());
            arbeidsfordelingKriterier.setTema(tema);

            FinnBehandlendeEnhetListeRequest request = new FinnBehandlendeEnhetListeRequest();
            request.setArbeidsfordelingKriterier(arbeidsfordelingKriterier);

            FinnBehandlendeEnhetListeResponse response = arbeidsfordelingSoapTjeneste.finnBehandlendeEnhetListe(request);

            List<Organisasjonsenhet> behandlendeEnhetListe = response.getBehandlendeEnhetListe();

            return behandlendeEnhetListe.stream().map(OppfolgingEnhet::of).collect(toList());

        } catch (FinnBehandlendeEnhetListeUgyldigInput e) {
            log.warn("Kunne ikke finne behandlende enheter for geografisk tilknytning og tema ", e);
            return emptyList();
        }
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

        List<ArbeidsfordelingEnhet> arbeidsfordelingEnheter = response.readEntity(new GenericType<List<ArbeidsfordelingEnhet>>() {
        });

        return arbeidsfordelingEnheter.stream().map(OppfolgingEnhet::of).collect(toList());
    }
}
