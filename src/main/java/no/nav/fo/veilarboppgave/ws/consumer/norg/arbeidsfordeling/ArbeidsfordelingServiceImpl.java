package no.nav.fo.veilarboppgave.ws.consumer.norg.arbeidsfordeling;

import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;
import no.nav.fo.veilarboppgave.domene.TemaDTO;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.binding.ArbeidsfordelingV1;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.binding.FinnBehandlendeEnhetListeUgyldigInput;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.ArbeidsfordelingKriterier;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.Geografi;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.Organisasjonsenhet;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.Tema;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.FinnBehandlendeEnhetListeRequest;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.FinnBehandlendeEnhetListeResponse;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ArbeidsfordelingServiceImpl implements ArbeidsfordelingService {

    private final ArbeidsfordelingV1 arbeidsfordelingSoapTjeneste;

    public ArbeidsfordelingServiceImpl(ArbeidsfordelingV1 arbeidsfordelingV1) {
        this.arbeidsfordelingSoapTjeneste = arbeidsfordelingV1;
    }

    @Override
    public List<OppfolgingEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning, TemaDTO temaDTO) {
        try {
            Geografi geografi = new Geografi();
            geografi.setKodeverksRef(geografiskTilknytning.getGeofrafiskTilknytning());

            ArbeidsfordelingKriterier arbeidsfordelingKriterier = new ArbeidsfordelingKriterier();
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
}
