package no.nav.fo.veilarboppgave.norg;

import java.util.List;

public interface ArbeidsfordelingService {
    List<OrganisasjonsEnhet> hentBehandlendeEnheter(GeografiskTilknytning geografiskTilknytning);
}
