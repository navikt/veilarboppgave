package no.nav.veilarboppgave.client.oppgave;

import no.nav.common.health.HealthCheck;
import no.nav.veilarboppgave.domain.Oppgave;
import no.nav.veilarboppgave.domain.OppgaveId;

import java.util.Optional;

public interface OppgaveClient extends HealthCheck {

    Optional<OppgaveId> opprettOppgave(Oppgave oppgave);

}
