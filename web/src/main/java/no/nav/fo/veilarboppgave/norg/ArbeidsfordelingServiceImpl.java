package no.nav.fo.veilarboppgave.norg;

import lombok.extern.slf4j.Slf4j;
import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OrganisasjonsEnhet;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.FinnBehandlendeEnhetListeUgyldigInput;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSArbeidsfordelingKriterier;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSGeografi;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.informasjon.WSOrganisasjonsenhet;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.WSFinnBehandlendeEnhetListeRequest;
import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.meldinger.WSFinnBehandlendeEnhetListeResponse;

import javax.inject.Inject;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ArbeidsfordelingServiceImpl implements ArbeidsfordelingService {

    @Inject
    ArbeidsfordelingV1 arbeidsfordelingSoapTjeneste;

    @Override
    public List<OrganisasjonsEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning) {
        List<OrganisasjonsEnhet> enheter = emptyList();
        try {
            WSGeografi wsGeografi = new WSGeografi().withValue(geografiskTilknytning.getGeofrafiskTilknytning());
            WSArbeidsfordelingKriterier arbeidsfordelingKriterier = new WSArbeidsfordelingKriterier().withGeografiskTilknytning(wsGeografi);
            WSFinnBehandlendeEnhetListeRequest request = new WSFinnBehandlendeEnhetListeRequest().withArbeidsfordelingKriterier(arbeidsfordelingKriterier);
            WSFinnBehandlendeEnhetListeResponse response = arbeidsfordelingSoapTjeneste.finnBehandlendeEnhetListe(request);
            List<WSOrganisasjonsenhet> behandlendeEnhetListe = response.getBehandlendeEnhetListe();
             enheter = behandlendeEnhetListe.stream().map(OrganisasjonsEnhet::of).collect(toList());
        } catch (FinnBehandlendeEnhetListeUgyldigInput finnBehandlendeEnhetListeUgyldigInput) {
            log.warn(format("Kunne ikke finne behandlende enheter for geografisk tilknytning %s", geografiskTilknytning));
        }
        return enheter;
    }
}
