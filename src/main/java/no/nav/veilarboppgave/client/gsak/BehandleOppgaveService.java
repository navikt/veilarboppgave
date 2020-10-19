package no.nav.veilarboppgave.client.gsak;

import no.nav.veilarboppgave.domain.OppgaveId;
import no.nav.veilarboppgave.domain.Oppgave;

import java.util.Optional;

public interface BehandleOppgaveService {

    Optional<OppgaveId> opprettOppgave(Oppgave oppgave);

}
