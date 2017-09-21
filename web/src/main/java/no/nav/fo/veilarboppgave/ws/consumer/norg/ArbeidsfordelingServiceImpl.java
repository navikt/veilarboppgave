package no.nav.fo.veilarboppgave.ws.consumer.norg;

import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.Enhet;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.Tema;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.FinnBehandlendeEnhetListeUgyldigInput;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSArbeidsfordelingKriterier;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSGeografi;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSOrganisasjonsenhet;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSTema;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.WSFinnBehandlendeEnhetListeRequest;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.WSFinnBehandlendeEnhetListeResponse;

import javax.ws.rs.NotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public class ArbeidsfordelingServiceImpl implements ArbeidsfordelingService {

    private final ArbeidsfordelingV1 arbeidsfordelingSoapTjeneste;

    public ArbeidsfordelingServiceImpl(ArbeidsfordelingV1 arbeidsfordelingV1) {
        this.arbeidsfordelingSoapTjeneste = arbeidsfordelingV1;
    }

    @Override
    public List<Enhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning, Tema tema) {
        try {
            WSGeografi wsGeografi = new WSGeografi().withValue(geografiskTilknytning.getGeofrafiskTilknytning());
            WSArbeidsfordelingKriterier arbeidsfordelingKriterier = new WSArbeidsfordelingKriterier().withGeografiskTilknytning(wsGeografi);

            WSTema wsTema = new WSTema();
            wsTema.setValue(tema.getTemaKode());
            arbeidsfordelingKriterier.setTema(wsTema);

            WSFinnBehandlendeEnhetListeRequest request = new WSFinnBehandlendeEnhetListeRequest().withArbeidsfordelingKriterier(arbeidsfordelingKriterier);
            WSFinnBehandlendeEnhetListeResponse response = arbeidsfordelingSoapTjeneste.finnBehandlendeEnhetListe(request);

            List<WSOrganisasjonsenhet> behandlendeEnhetListe = response.getBehandlendeEnhetListe();
            return behandlendeEnhetListe.stream().map(Enhet::of).collect(toList());
        } catch (FinnBehandlendeEnhetListeUgyldigInput e) {
            log.info("Kunne ikke finne behandlende enheter for geografisk tilknytning og tema ");
            throw new NotFoundException(e);
        }
    }
}
