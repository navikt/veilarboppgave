package no.nav.fo.veilarboppgave.ws.consumer.gsak;

import no.nav.fo.veilarboppgave.domene.OppgaveId;

import java.util.Optional;

public interface OppgaveService {

    Optional<OppgaveId> opprettOppgave();

}
