package no.nav.veilarboppgave.client.veilarbperson;

import no.nav.common.health.HealthCheck;
import no.nav.common.types.identer.Fnr;

import java.util.Optional;

public interface VeilarbpersonClient extends HealthCheck {

    boolean erEgenAnsatt(Fnr fnr);

    Optional<String> hentGeografiskTilknytning(Fnr fnr);

}
