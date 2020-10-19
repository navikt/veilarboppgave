package no.nav.veilarboppgave.ws.consumer.gsak;

import no.nav.veilarboppgave.domene.OppgaveId;
import no.nav.veilarboppgave.rest.api.oppgave.Oppgave;

import java.util.Optional;

public interface BehandleOppgaveService {

    Optional<OppgaveId> opprettOppgave(Oppgave oppgave);

}
