package no.nav.veilarboppgave.mocks;

import no.nav.veilarboppgave.domain.OppgaveId;
import no.nav.veilarboppgave.domain.Oppgave;
import no.nav.veilarboppgave.client.gsak.BehandleOppgaveService;

import java.util.Optional;

public class BehandleOppgaveServiceMock implements BehandleOppgaveService {

    @Override
    public Optional<OppgaveId> opprettOppgave(Oppgave oppgave) {
        return OppgaveId.of( "1");
    }
}
