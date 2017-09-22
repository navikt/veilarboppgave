package no.nav.fo.veilarboppgave.ws.consumer.norg.enhet;

import no.nav.fo.veilarboppgave.domene.OppfolgingEnhet;

import java.util.List;

public interface EnhetService {
    List<OppfolgingEnhet> harTilgangTilEnhet(String veilederId);
}
