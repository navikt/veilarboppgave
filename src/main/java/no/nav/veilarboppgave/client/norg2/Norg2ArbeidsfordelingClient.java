package no.nav.veilarboppgave.client.norg2;

import no.nav.common.health.HealthCheck;
import no.nav.veilarboppgave.domain.OppfolgingEnhet;
import no.nav.veilarboppgave.domain.TemaDTO;

import java.util.List;

public interface Norg2ArbeidsfordelingClient extends HealthCheck {
    List<OppfolgingEnhet> hentBestMatchEnheter(String geografiskTilknytning, TemaDTO tema, boolean egenAnsatt);
}
