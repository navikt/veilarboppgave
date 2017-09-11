package no.nav.fo.veilarboppgave.norg;

import no.nav.fo.veilarboppgave.domene.GeografiskTilknytning;
import no.nav.fo.veilarboppgave.domene.OrganisasjonsEnhet;

import java.util.List;

public interface ArbeidsfordelingService {
    List<OrganisasjonsEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning);
}
