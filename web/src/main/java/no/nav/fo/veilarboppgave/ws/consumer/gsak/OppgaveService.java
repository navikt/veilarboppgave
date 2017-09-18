package no.nav.fo.veilarboppgave.ws.consumer.gsak;

import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;

import java.util.Optional;

public interface OppgaveService {

    Optional<OppgaveId> opprettOppgave(OppgaveDTO oppgave);

}
