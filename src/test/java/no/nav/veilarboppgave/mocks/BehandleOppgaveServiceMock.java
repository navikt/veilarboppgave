package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domene.OppgaveId;
import no.nav.veilarboppgave.rest.api.oppgave.Oppgave;
import no.nav.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;

import java.util.Optional;

public class BehandleOppgaveServiceMock implements BehandleOppgaveService {

    @Override
    public Optional<OppgaveId> opprettOppgave(Oppgave oppgave) {
        return OppgaveId.of( "1");
    }
}
