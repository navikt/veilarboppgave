package no.nav.fo.veilarboppgave.mocks;

import no.nav.fo.veilarboppgave.domene.OppgaveId;
import no.nav.fo.veilarboppgave.rest.api.oppgave.OppgaveDTO;
import no.nav.fo.veilarboppgave.ws.consumer.gsak.BehandleOppgaveService;

import java.util.Optional;

public class BehandleOppgaveServiceMock implements BehandleOppgaveService {

    @Override
    public Optional<OppgaveId> opprettOppgave(OppgaveDTO oppgave) {

        return Optional.of(OppgaveId.of(1));
    }
}
