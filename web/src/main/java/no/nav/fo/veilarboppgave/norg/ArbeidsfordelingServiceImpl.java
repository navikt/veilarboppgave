package no.nav.fo.veilarboppgave.norg;

import no.nav.tjeneste.virksomhet.arbeidsfordeling.v1.ArbeidsfordelingV1;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;

public class ArbeidsfordelingServiceImpl implements ArbeidsfordelingService {

    @Inject
    ArbeidsfordelingV1 arbeidsfordelingSoapTjeneste;

    @Override
    public List<Enhet> hentBehandlendeEnheter() {
        return emptyList();
    }
}
